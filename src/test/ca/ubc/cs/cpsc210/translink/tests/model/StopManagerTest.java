package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test the StopManager
 */
public class StopManagerTest {
    private StopManager sm;
    private Stop s9999;
    private Stop s222;
    private List<Stop> stops;


    @BeforeEach
    public void setup() {
        sm = StopManager.getInstance();
        StopManager.getInstance().clearStops();
        s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
    }

    @Test
    public void testBasic() {
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
    }

    @Test
    public void testSelectedAndGetStopWithOneVars() {
        Boolean b = false;
        try {
            sm.setSelected(s9999);
        } catch (StopException e) {
            b = true;
        }
        assertTrue(b);
        sm.getStopWithNumber(333);
        s222 = new Stop(333, "llala", new LatLon(1.1, 2.2));
        try {
            sm.setSelected(s222);
        } catch (StopException e) {
            fail("should not be throw");
        }
        assertEquals(s222, sm.getSelected());

        assertTrue(sm.getStopWithNumber(333).equals(s222));
        sm.clearSelectedStop();
        assertEquals(null, sm.getSelected());


    }


    @Test
    public void testGetStopWithNumberThreeVariables() {
        LatLon locnn = new LatLon(1.1, 2.2);
        sm.getStopWithNumber(333, "aa", locnn);
        s222 = new Stop(333, "aa", locnn);
        try {
            sm.setSelected(s222);
        } catch (StopException e) {
            fail("should not be throw");
        }
        assertEquals(s222, sm.getSelected());
        assertTrue(sm.getStopWithNumber(333, "bb", new LatLon(1.1, 3.3)).equals(s222));
        assertTrue(sm.getStopWithNumber(333, "aa", locnn).equals(s222));
        assertEquals(1, sm.getNumStops());
    }


    @Test
    public void testFindNearestTwo() {
        LatLon point = new LatLon(0, 0);
        LatLon muchBigger = new LatLon(777777,8888888);
        LatLon medium = new LatLon(0.000002,0.00000001);
        LatLon small = new LatLon(0.000001,0.00000001);
        LatLon verySmall =new LatLon(0.000002,0.00000002);
        sm.getStopWithNumber(77,"biggest",muchBigger);
        assertEquals(null,sm.findNearestTo(point));
        sm.getStopWithNumber(44,"medium",medium);
        Stop s44 = new Stop(44,"lala",medium);
        assertTrue(sm.findNearestTo(point).equals(s44));
        sm.getStopWithNumber(3,"small",small);
        Stop s3= new Stop(3,"emm",small);
        assertTrue(sm.findNearestTo(point).equals(s3));
        sm.getStopWithNumber(66,"small",verySmall);
        Stop s6= new Stop(66,"emm",small);
        assertTrue(sm.findNearestTo(point).equals(s3));

    }

    @Test
    public void testIterator(){
       stops = new LinkedList<Stop>();
        sm.getStopWithNumber(44,"medium",new LatLon(1.2,3.2));
      for(Stop s:sm){
          stops.add(s);
      }
      assertEquals(1,stops.size());
    }





}
