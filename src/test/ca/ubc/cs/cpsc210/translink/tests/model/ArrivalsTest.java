package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test Arrivals
 */
public class ArrivalsTest {
    Route r;
    Arrival a;
    Route r1;
    Arrival a1;

    @BeforeEach
    public void setup() {
        r = RouteManager.getInstance().getRouteWithNumber("43");
        a = new Arrival(23, "Home", r);
    }

    @Test
    public void testConstructor() {
        assertEquals(23, a.getTimeToStopInMins());
        assertEquals("Home",a.getDestination());
        assertEquals(r, a.getRoute());
        assertEquals(" ",a.getStatus());
    }


    @Test
    public void testSetStatus(){
        a.setStatus("+");
        assertEquals("+",a.getStatus());
        a.setStatus("-");
        assertEquals("-",a.getStatus());
        a.setStatus("*");
        assertEquals("*",a.getStatus());
        a.setStatus(" ");
        assertEquals(" ",a.getStatus());
    }

    @Test
    public void testCompareto(){
        r1 = RouteManager.getInstance().getRouteWithNumber("43");
        a1 = new Arrival(22, "Home", r1);
        assertEquals(1,a.compareTo(a1));
    }
}
