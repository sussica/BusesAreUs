package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test the RouteManager
 */
public class RouteManagerTest {

    @BeforeEach
    public void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testBasic() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
    }

    @Test
    public void testGetRouteWithNumberOneParameter(){
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
        Route r1 =RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43,r1);
        assertEquals(1,RouteManager.getInstance().getNumRoutes());

    }



    @Test
    public void testGetRouteWithNumberTwoParameter(){
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43","lala");
        assertEquals(r43, r);
        Route r1 =RouteManager.getInstance().getRouteWithNumber("43","lili");
        assertEquals(r43,r1);
        assertEquals(1,RouteManager.getInstance().getNumRoutes());
    }


    @Test
    public void testIterator(){
       List routes = new LinkedList<Route>();
        RouteManager.getInstance().getRouteWithNumber("43","lili");
        for(Route r:RouteManager.getInstance()){
            routes.add(r);
        }
        assertEquals(1,routes.size());


    }


}
