package ca.ubc.cs.cpsc210.translink.outside.parsers.ExceptionTest;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RouteParserExpectionTest {
    @BeforeEach
    public void setup() {
        RouteManager.getInstance().clearRoutes();
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testJsonSyntaxError1(){
        RouteParser p = new RouteParser("illegal/errorSyntax1-route.json");
        try {
            p.parse();
        } catch (JSONException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testJsonSyntaxError2(){
        RouteParser p = new RouteParser("illegal/errorSyntax2-route.json");
        try {
            p.parse();
        } catch (JSONException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testJsonNotArray1(){
        RouteParser p = new RouteParser("illegal/notArray1-routes.json");
        try {
            p.parse();
        } catch (JSONException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testJsonNotArray2(){
        RouteParser p = new RouteParser("illegal/notArray2-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoRouteNo(){
        RouteParser p = new RouteParser("illegal/noRouteNo-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoName(){
        RouteParser p = new RouteParser("illegal/noName-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoPatterns(){
        RouteParser p = new RouteParser("illegal/noPatterns-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoPatternNo(){
        RouteParser p = new RouteParser("illegal/noPatternNo-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoDestination(){
        RouteParser p = new RouteParser("illegal/noDestination-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNoDirection(){
        RouteParser p = new RouteParser("illegal/noDirections-routes.json");
        try {
            p.parse();
        } catch (RouteDataMissingException e){
            // Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }
}
