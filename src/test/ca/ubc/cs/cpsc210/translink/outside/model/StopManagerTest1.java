package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import ca.ubc.cs.cpsc210.translink.util.SphericalGeometry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test the StopManager
 */
public class StopManagerTest1 {

    @BeforeEach
    public void setup() {
        StopManager.getInstance().clearStops();
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    public void testBasic() {
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);

        Stop s777 = new Stop(777,null,null);
        r = StopManager.getInstance().getStopWithNumber(777,"Evil point",new LatLon(-20.5,80.3));
        assertEquals(s777,r);
    }

    @Test
    public void testSelected(){
        Set<Integer> usedStopNumber = new HashSet<>();
        assertEquals(null,StopManager.getInstance().getSelected());

        //TestSelectWithEmptyStops
        try{
            int stopNum;
            do{
                stopNum = AutoTestConf.RND.nextInt();
            } while(usedStopNumber.contains(stopNum));
            StopManager.getInstance().setSelected(new Stop(stopNum,null,null));
            fail("Missing exception!");
        } catch (StopException e){
            //Expected
        }

        //TestSelectWithNewStop
        try{
            int stopNum;
            do{
                stopNum = AutoTestConf.RND.nextInt();
            } while(usedStopNumber.contains(stopNum));
            StopManager.getInstance().setSelected(StopManager.getInstance().getStopWithNumber(stopNum));
            assertEquals(StopManager.getInstance().getStopWithNumber(stopNum),StopManager.getInstance().getSelected());
        } catch (Exception e){
            fail("Unexpected exception!");
        }

        //TestClearSelected
        StopManager.getInstance().clearSelectedStop();
        assertEquals(StopManager.getInstance().getSelected(),null);

        //TestSelectExistStops
        for(int stopNum:usedStopNumber){
            try {
                StopManager.getInstance().setSelected(StopManager.getInstance().getStopWithNumber(stopNum));
            } catch (Exception e){
                fail("Unexpected exception!");
            }
        }
    }

    @Test
    public void testIterator(){
        Set<Stop> stops = new HashSet<>();
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            stops.add(StopManager.getInstance().getStopWithNumber(AutoTestConf.RND.nextInt()));
        }
        int count =0;
        for(Stop s:StopManager.getInstance()){
            assertTrue(stops.contains(s));
            count++;
        }
        assertEquals(stops.size(),count);
    }

    @Test
    public void testFindNearestStop(){
       for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
           double lat = AutoTestConf.RND.nextDouble() * 180 - 90;
           double lon = AutoTestConf.RND.nextDouble() * 360 -180;
           StopManager.getInstance().getStopWithNumber(AutoTestConf.RND.nextInt()).setLocn(new LatLon(lat,lon));
       }

       for(int lat=-90; lat<=90;lat++)
           for(int lon=-180;lon<=180;lon++)
               for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST/64800);k++) {
                   LatLon pt = new LatLon(lat+AutoTestConf.RND.nextDouble(), lon+AutoTestConf.RND.nextDouble());
                   assertEquals(StopManagerTest1.nearest(pt), StopManager.getInstance().findNearestTo(pt));
               }
    }

    public static Stop nearest(LatLon pt){
        Stop stop = null;
        for(Stop s:StopManager.getInstance()){
            if(SphericalGeometry.distanceBetween(pt,s.getLocn())>StopManager.RADIUS)
                continue;
            if((null==stop)||
                    (SphericalGeometry.distanceBetween(pt,s.getLocn())<
                            SphericalGeometry.distanceBetween(pt,stop.getLocn())))
                stop = s;
        }
        return stop;
    }

}
