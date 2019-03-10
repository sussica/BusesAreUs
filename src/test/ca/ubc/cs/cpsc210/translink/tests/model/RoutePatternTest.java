package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoutePatternTest {
    private List<LatLon> latLons;
    private Route r;
    private RoutePattern rp;
    private RoutePattern rp1;
    private Route r1;


    @BeforeEach
    public void runBefore() {
        r = RouteManager.getInstance().getRouteWithNumber("43");
        rp = new RoutePattern("name", "destination", "direction", r);

        latLons = new LinkedList<LatLon>();
    }

    @Test
    public void testConstructor() {
        assertEquals("name", rp.getName());
        assertEquals("destination", rp.getDestination());
        assertEquals("direction", rp.getDirection());
        assertEquals(0,rp.getPath().size());

    }

    @Test
    public void testSetPath() {
        LatLon latlon = new LatLon(1.1, 2.2);
        latLons.add(latlon);
        rp.setPath(latLons);
        assertEquals(1, rp.getPath().size());
    }

    @Test
    public void testSetDirection(){
        rp.setDirection("thisDirection");
        assertEquals("thisDirection",rp.getDirection());
    }

    @Test
    public void testSetDestination(){
        rp.setDestination("thisDestination");
        assertEquals("thisDestination",rp.getDestination());
    }

    @Test
    public void testEqual(){

        assertTrue(rp.equals(rp));
        rp1 = null;
        assertFalse(rp.equals(rp1));
        r1 = RouteManager.getInstance().getRouteWithNumber("22");
        assertFalse(rp.equals(r1));
        assertFalse(rp.equals(latLons));
        rp1 = new  RoutePattern("name", "desti", "direct", r);
        assertTrue(rp.equals(rp1));
        rp1 = new  RoutePattern("name", "destination", "direction", r);
        assertTrue(rp.equals(rp1));

        rp1 = new RoutePattern(null, "d","d",r);
        RoutePattern rp2 = new RoutePattern(null,"d","d",r);
        assertTrue(rp1.equals(rp2));


    }

    @Test
    public void testHashCode(){
        assertEquals("name".hashCode(),rp.hashCode());
    }



    @Test
    public void testGetPath(){
        LatLon latlon = new LatLon(1.1, 2.2);
        LatLon latlon1 = new LatLon(1.2, 2.2);
        latLons.add(latlon);
        latLons.add(latlon1);
        rp.setPath(latLons);
        boolean b =false;
        try{rp.getPath().add(latlon);}
        catch(UnsupportedOperationException e){
            b=true;
        }
        assertTrue(b);



    }
}
