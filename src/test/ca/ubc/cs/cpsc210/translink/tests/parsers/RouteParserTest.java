package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the RouteParser
 */
// TODO: Write more tests

public class RouteParserTest {
    @BeforeEach
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testRouteParserNormal() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("allroutes.json");
        p.parse();
        assertEquals(229, RouteManager.getInstance().getNumRoutes());
    }


    @Test
    public void testRouteException() throws RouteDataMissingException, JSONException, IOException {
        RouteParser p = new RouteParser("testRouteException.json");
        try {
            p.parse();
        }
        catch (RouteDataMissingException re){
            //expected
        }
        assertEquals(1, RouteManager.getInstance().getNumRoutes());

    }
}
