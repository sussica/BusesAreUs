package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusTest {
    private Bus b;
    private Route r;
    private String routeNum;
    private double lat;
    private double lon;
    private String dest;
    private String time;


    @BeforeEach
    public void runBefore() {
        this.routeNum = "99";
        r = new Route(routeNum);
        this.lat = 1.1;
        this.lon = 2.2;
        this.dest = "UBC";
        this.time = "30 minutes";
        b = new Bus(r, lat, lon, dest, time);
    }

    @Test
    public void testGetRoute() {
        Route o = new Route("99");
        assertEquals(o,b.getRoute());
    }

    @Test
    public void testGetLatLon(){
        LatLon ll = new LatLon(1.1,2.2);
        assertEquals(ll,b.getLatLon());
    }

    @Test
    public void testGetDestination(){
        assertEquals("UBC",b.getDestination());
    }

    @Test
    public void testGetTime() {
        assertEquals( "30 minutes",b.getTime());
    }}