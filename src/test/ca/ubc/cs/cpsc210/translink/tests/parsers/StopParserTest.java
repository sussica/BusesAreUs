package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Tests for the StopParser
 */

// TODO: Write more tests

public class StopParserTest {
    @BeforeEach
    public void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testStopParserNormal() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("stops.json");
        p.parse();
        assertEquals(8524, StopManager.getInstance().getNumStops());
    }

    @Test
    public void testException() throws StopDataMissingException, JSONException, IOException {
        StopParser p = new StopParser("testStopException.json");
        try{
            p.parse();}
        catch (StopDataMissingException se){
            //expected
        }
        catch(JSONException je){
            fail("not this one ");
        }

        assertEquals(1, StopManager.getInstance().getNumStops());
        assertEquals(1, RouteManager.getInstance().getRouteWithNumber("C23").getStops().size());


    }

    @Test
    public void testnoROute() throws StopDataMissingException, JSONException, IOException {
        RouteManager.getInstance().clearRoutes();
        StopParser p = new StopParser("testNoRouteStopParser.json");
        try{
            p.parse();}
        catch (StopDataMissingException se){
            //expected
        }
        catch(JSONException je){
            fail("not this one ");
        }

        assertEquals(0, RouteManager.getInstance().getRouteWithNumber("C23").getStops().size());


    }

}
