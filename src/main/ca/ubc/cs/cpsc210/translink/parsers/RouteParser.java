package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the file and add all route to the route manager.
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }

    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException             when:
     *                                   <ul>
     *                                   <li>JSON data does not have expected format (JSON syntax problem)
     *                                   <li>JSON data is not an array
     *                                   </ul>
     *                                   If a JSONException is thrown, no routes should be added to the route manager
     * @throws RouteDataMissingException when
     *                                   <ul>
     *                                   <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *                                   <li>The value of the Patterns element is not an array for any route</li>
     *                                   <li>JSON data is missing PatternNo, Destination, or Direction element for any route pattern</li>
     *                                   </ul>
     *                                   If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */

    public void parseRoutes(String jsonResponse) throws JSONException, RouteDataMissingException {
        Route r;
        RoutePattern b;
        Boolean boo = false;

        JSONArray routes = new JSONArray(jsonResponse);

        for (int i = 0; i < routes.length(); i++) {
            String routeNumber = null;
            String name = null;
            JSONArray routePatternsAsArray = null;
            JSONObject route = routes.getJSONObject(i);


            try {
                routeNumber = route.getString("RouteNo");
                name = route.getString("Name");
                routePatternsAsArray = route.getJSONArray("Patterns");
            } catch (JSONException je) {
                boo = true;
            }

            if (name != null && routeNumber != null && routePatternsAsArray != null) {
                try {
                    r = RouteManager.getInstance().getRouteWithNumber(routeNumber, name);
                    b = parseRoutePatterns(routePatternsAsArray, r);
                } catch (RouteDataMissingException re) {
                    boo = true;
                }
            }
        }


        if (boo == true) {
            throw new RouteDataMissingException("route info is missing");
        }
    }


    public static RoutePattern parseRoutePatterns(JSONArray pattern, Route r) throws RouteDataMissingException {


        String patternNumber = null;
        String destination = null;
        String direction = null;
        RoutePattern rp = null;
        boolean boo = false;

        for (int i = 0; i < pattern.length(); i++) {
            try {
                JSONObject pat = pattern.getJSONObject(i);
                patternNumber = pat.getString("PatternNo");
                destination = pat.getString("Destination");
                direction = pat.getString("Direction");
            } catch (JSONException e) {
                throw new RouteDataMissingException("pattern info is missing");
            }
            rp = r.getPattern(patternNumber, destination, direction);

        }
        return rp;
    }
}








