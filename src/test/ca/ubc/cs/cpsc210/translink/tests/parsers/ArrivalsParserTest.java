package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test the ArrivalsParser
 */
public class ArrivalsParserTest {

    @Test
    public void testArrivalsParserNormal() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals43.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
        int count = 0;
        for (Arrival a : s) {
            assertTrue(a.getTimeToStopInMins() <= 120);
            count++;
        }
        assertEquals(6, count);
    }

    @Test
    public void testFirstArrivalex() throws JSONException, ArrivalsDataMissingException {
        int count =0;
            Stop s = StopManager.getInstance().getStopWithNumber(51479);
            s.clearArrivals();
            String data = "";
            try {
                data = new FileDataProvider("arrivals43.json").dataSourceToString();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Can't read the arrivals data");
            }
            ArrivalsParser.parseArrivals(s, data);
        for (Arrival a : s) {
            if(a.getTimeToStopInMins()==48)
            count++;
        }
        assertEquals(1, count);
    }



    @Test
    public void testFirstArrivaldes() throws JSONException, ArrivalsDataMissingException {
        int count =0;
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals43.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
        for (Arrival a : s) {
            if(a.getDestination().equals("JOYCE STN"))
                count++;
        }
        assertEquals(6, count);
    }

    @Test
    public void testException() throws JSONException, ArrivalsDataMissingException {
        int count =0;
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("testParseArrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        try{
        ArrivalsParser.parseArrivals(s, data);
        }
        catch (ArrivalsDataMissingException ae){
            //expected
        }
        catch (JSONException je){
            fail("should not throw JSONException");
        }
        for (Arrival a : s) {
            if(a.getDestination().equals("JOYCE STN"))
                count++;
        }
        assertEquals(2, count);
    }



    @Test
    public void testArrivalIsEmpty()  {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("testArrivalIsEmpty.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("should throw");}
            catch (ArrivalsDataMissingException ae){
            //expected
            }
            catch (JSONException je){
                fail("not this one");
            }

        }
}


