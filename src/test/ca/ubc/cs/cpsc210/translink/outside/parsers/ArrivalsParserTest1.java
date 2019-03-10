package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.outside.parsers.ExceptionTest.RouteParserExpectionTest;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.server.ExportException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test the ArrivalsParser
 */
public class ArrivalsParserTest1 {
    @BeforeEach
    public void runBefore(){
        StopManager.getInstance().clearStops();
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testArrivalsParserNormalLargeSize() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals.json").dataSourceToString();
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
        assertEquals(40, count);
    }

    @Test
    public void testArrivalsParserNormalSmallSize() throws JSONException, ArrivalsDataMissingException {
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
    public void testArrivalSequenceStoredInStop() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        ArrivalsParser.parseArrivals(s, data);
        Arrival last = null;
        for(Arrival a:s){
            if(!(null==last))
                assertTrue(last.compareTo(a)<=0);
            last = a;
        }
    }

    @Test
    public void testRouteNoArrival1(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/routeNoArrival1-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("Missing exception!");
        } catch (ArrivalsDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testRouteNoArrival2(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/routeNoArrival2-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("Missing exception!");
        } catch (ArrivalsDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoRouteNum(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/noRouteNo-arrivals.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }
        try {
            ArrivalsParser.parseArrivals(s, data);
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testBadArrivalSchedule(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/badArrivalSchedule-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        try {
            ArrivalsParser.parseArrivals(s, data);
        } catch (Exception e){
            fail("Unexpected exception!");
        }

        int count = 0;
        for(Arrival a: s)
            count++;
        assertEquals(37,count);

        count = 0;
        for(Route route: RouteManager.getInstance())
            if(s.onRoute(route))
                count++;
        assertEquals(7,count);
    }

    @Test
    public void testErrorSyntaxInArrival(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/errorSyntax2-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("Missing exception!");
        } catch(JSONException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }

        int count = 0;
        for(Arrival a:s)
            count++;
        assertEquals(0,count);
    }
}
