package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class StopTest {
    private List<Bus> buses;
    private List<Arrival> testArrivals;
    private Set<Route> routes;
    private LatLon latlon;
    private Stop stop;
    private Route r1;
    private Arrival a1;
    private Stop testStop;
    private Arrival a10;
    private Arrival a20;
    private Arrival a30;

    @BeforeEach
    public void RunBefore(){
        latlon = new LatLon(1.1,2.2);
        stop = new Stop(33,"ubc",latlon);
        this.buses = new LinkedList<Bus>();
        this.routes = new HashSet<Route>();
        r1 = new Route("22");
        a1 = new Arrival(3,"bb",r1);
        testArrivals = new LinkedList<Arrival>();
    }


    @Test
    public void testConstructor(){
     assertEquals("ubc",stop.getName());
     assertEquals(latlon,stop.getLocn());
     assertEquals(33,stop.getNumber());
     assertEquals(0,stop.getRoutes().size());
    }


    @Test
    public void testAddAndRemoveAndOnRoute(){

        stop.addRoute(r1);
        assertEquals(1,stop.getRoutes().size());
        assertTrue(stop.onRoute(r1));
        stop.removeRoute(r1);
        assertEquals(0,stop.getRoutes().size());
        assertFalse(stop.onRoute(r1));
    }

    @Test
    public void testClearArrivals(){
        stop.addArrival(a1);
        for(Arrival a: stop){
            testArrivals.add(a);
        }
        assertEquals(1,testArrivals.size());
        stop.clearArrivals();
        testArrivals.clear();
        for(Arrival a: stop){
            testArrivals.add(a);
        }
        assertEquals(0,testArrivals.size());
    }


    @Test
    public void testAddBusAndClearBus(){
        Boolean b = false;
        try{ stop.addBus(new Bus(r1,3,5,"a","b"));}
        catch (RouteException e){ b = true;}
        assertTrue(b);
        stop.addRoute(r1);
        try {stop.addBus(new Bus(r1,3,5,"a","b"));}
        catch (RouteException e){fail ("Should not be throw");}
        assertEquals(1,stop.getBuses().size());
        stop.clearBuses();
        assertEquals(0,stop.getBuses().size());


    }


    @Test
    public void testEqual(){
        assertTrue(stop.equals(stop));
        testStop = null;
        assertFalse(stop.equals(testStop));
        assertFalse(stop.equals(a1));
        assertTrue(stop.equals(new Stop(33,"ccc",latlon)));
        assertFalse(stop.equals(new Stop(34,"ccc",latlon)));
    }

    @Test
    public void testHashCode(){
        assertEquals(33,stop.hashCode());
    }

    @Test
    public void testSetName(){
        stop.setName("bb");
        assertEquals("bb",stop.getName());
    }

    @Test
    public void testSetLocn(){
        LatLon la = new LatLon(1.1,333);
        stop.setLocn(la);
        assertEquals(la,stop.getLocn());

    }

    @Test
    public void testAddArrivals(){
        stop.addArrival(a10 = new Arrival(10,"lala",r1));
        stop.addArrival(a20 = new Arrival(20,"oo",r1));
        stop.addArrival(a30 = new Arrival(15,"o",r1));
        for(Arrival a : stop){
            testArrivals.add(a);
        }
        assertEquals(a10 ,testArrivals.get(0));
        assertEquals(a30, testArrivals.get(1));
        assertEquals(a20, testArrivals.get(2));

    }


}
