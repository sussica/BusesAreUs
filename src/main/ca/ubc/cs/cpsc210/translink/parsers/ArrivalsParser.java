package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop         stop to which parsed arrivals are to be added
     * @param jsonResponse the JSON response produced by Translink
     * @throws JSONException                when:
     *                                      <ul>
     *                                      <li>JSON response does not have expected format (JSON syntax problem)</li>
     *                                      <li>JSON response is not an array</li>
     *                                      </ul>
     * @throws ArrivalsDataMissingException when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {


        //add arrival to stop
        Integer countDown = null;
        String destination = null;
        String scheduleStatus = null;
        boolean boo = false;
        String routeNumber = null;
        JSONArray arrivals = null;

        JSONArray busRoutes = new JSONArray(jsonResponse);


        for (int i = 0; i < busRoutes.length(); i++) {
            JSONObject busRoute = busRoutes.getJSONObject(i);
            try { routeNumber = busRoute.getString("RouteNo");
            } catch (JSONException je) {
                boo = true;
                continue;
            }

            try { arrivals = busRoute.getJSONArray("Schedules");
            } catch (JSONException je) {
                boo = true;
                continue;
            }

            if (arrivals.length() == 0) {
                boo = true;
            }

            for (int in = 0; in < arrivals.length(); in++) {
                JSONObject arrival = arrivals.getJSONObject(in);

                try {
                    countDown = arrival.getInt("ExpectedCountdown");
                    destination = arrival.getString("Destination");
                    scheduleStatus = arrival.getString("ScheduleStatus");
                } catch (JSONException je) {
                    boo = true;
                }


                if (countDown != null && destination != null && scheduleStatus != null) {

                    Arrival arr = new Arrival(countDown, destination, RouteManager.getInstance().getRouteWithNumber(routeNumber));
                    stop.addArrival(arr);
                    countDown = null;
                    destination = null;
                    scheduleStatus = null;
                }


            }

        }
        if (boo == true) {
            throw new ArrivalsDataMissingException("some data is missing");
        }


    }


}
