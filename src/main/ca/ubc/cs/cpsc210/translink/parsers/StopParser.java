package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException            when:
     *                                  <ul>
     *                                  <li>JSON data does not have expected format (JSON syntax problem)</li>
     *                                  <li>JSON data is not an array</li>
     *                                  </ul>
     *                                  If a JSONException is thrown, no stops should be added to the stop manager
     * @throws StopDataMissingException when
     *                                  <ul>
     *                                  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     *                                  </ul>
     *                                  If a StopDataMissingException is thrown, all correct stops are first added to the stop manager.
     */

    public void parseStops(String jsonResponse) throws JSONException, StopDataMissingException {

        boolean boo = false;
        LatLon latLon = null;
        Route route = null;

        JSONArray stops = new JSONArray(jsonResponse);
        for (int i = 0; i < stops.length(); i++) {
            String name = null;
            Integer stopNumber = null;
            Double latitude = null;
            Double longtitude = null;
            String stringRoutes = null;

            JSONObject stop = stops.getJSONObject(i);

            try {
                name = stop.getString("Name");
                stopNumber = stop.getInt("StopNo");
                latitude = stop.getDouble("Latitude");
                longtitude = stop.getDouble("Longitude");
                stringRoutes = stop.getString("Routes");}
             catch (JSONException je) {
            boo = true;
            }

            if(name!= null&&stopNumber != null&& latitude != null && longtitude != null && stringRoutes != null) {
                latLon = new LatLon(latitude, longtitude);
                if (stringRoutes.contains(", ")) {
                    String[] everyRoute = stringRoutes.split(", ");
                    for (String s : everyRoute) {
                        route = RouteManager.getInstance().getRouteWithNumber(s);
                        StopManager.getInstance().getStopWithNumber(stopNumber, name, latLon);
                        StopManager.getInstance().getStopWithNumber(stopNumber, name, latLon).addRoute(route);
                    }
                } else {
                    route = RouteManager.getInstance().getRouteWithNumber(stringRoutes);
                    StopManager.getInstance().getStopWithNumber(stopNumber, name, latLon);
                    StopManager.getInstance().getStopWithNumber(stopNumber, name, latLon).addRoute(route);
                }
            }
        }


        if(boo == true){
            throw new StopDataMissingException("some data is missing");
        }
        }
    }

