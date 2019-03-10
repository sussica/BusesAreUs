package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTest {
    private List<Stop> stops;
    private List<RoutePattern> routePatterns;
    private Route route;
    private RoutePattern rp;
    private Stop st;
    private Route r;
    private List<Stop> stops1;

    @BeforeEach
    public void runBefore() {
        route = new Route("333");
        stops = new LinkedList<Stop>();
        routePatterns = new LinkedList<RoutePattern>();
        rp = new RoutePattern("a", "b", "x", route);
        st = new Stop(3, "a", new LatLon(2.3, 4.4));
        stops1 = new LinkedList<Stop>();
    }


    @Test
    public void testConstructor() {
        assertEquals("", route.getName());
        assertEquals(0, route.getStops().size());
        assertEquals(0, route.getPatterns().size());
        assertEquals("333", route.getNumber());


    }

    @Test
    public void testSetAndGetName() {
        route.setName("lalala");
        assertEquals("lalala", route.getName());

    }

    @Test
    public void testAddPattern() {
        route.addPattern(rp);
        assertEquals(1, route.getPatterns().size());
        route.addPattern(rp);
        assertEquals(1, route.getPatterns().size());

    }

    @Test
    public void testAddStopAndRemove() {
        route.addStop(st);
        assertEquals(1, route.getStops().size());
        route.removeStop(st);
        assertEquals(0, route.getStops().size());
    }


    @Test
    public void testHasStop() {
        assertFalse(route.hasStop(st));
        route.addStop(st);
        assertTrue(route.hasStop(st));
    }


    @Test
    public void testEqual() {
        assertTrue(route.equals(route));
        r = null;
        assertFalse(route.equals(r));
        assertFalse(route.equals(st));
        r = new Route("33");
        assertFalse(route.equals(r));
        r = new Route("333");
        assertTrue(route.equals(r));
    }


    @Test
    public void testHashCode() {
        assertEquals("333".hashCode(), route.hashCode());
    }


    @Test
    public void testIterator() {
        for (Stop s : route) {
            stops1.add(s);
        }
        assertEquals(0, stops1.size());
    }

    @Test
    public void testToString() {
        assertEquals("Route 333", route.toString());
    }

    @Test
    public void testGetPatternWithPatternName() {
        assertEquals(new RoutePattern("dennis", "", "", route), route.getPattern("dennis"));
        route.addPattern(rp);
        assertEquals(rp, route.getPattern("a"));
    }


    @Test
    public void testGetPatternWithAllParameter() {
        assertEquals(new RoutePattern("dennis", "ab", "cc", route),
                route.getPattern("dennis", "ab", "cc"));
        route.addPattern(rp);
        assertEquals(rp, route.getPattern("a", "b", "x"));
    }


}








