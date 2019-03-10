package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StopTest1 {
    private Stop testStop;
    private int sampleNum;
    private String sampleName;
    private LatLon sampleLocn;

    @BeforeEach
    public void runBefore(){
        StopManager.getInstance().clearStops();
        RouteManager.getInstance().clearRoutes();
        this.sampleNum = AutoTestConf.RND.nextInt();
        this.sampleName = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN));
        this.sampleLocn = new LatLon(AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble());
        this.testStop = StopManager.getInstance().getStopWithNumber(this.sampleNum,this.sampleName,this.sampleLocn);
        //this.testStop = new Stop(this.sampleNum,this.sampleName,this.sampleLocn);
    }

    @Test
    public void testConstructor(){
        assertEquals(this.sampleNum,this.testStop.getNumber());
        assertTrue(this.testStop.getName().equals(this.sampleName));
        assertEquals(this.testStop.getLocn().getLatitude(),this.sampleLocn.getLatitude(),AutoTestConf.DELTA);
        assertEquals(this.testStop.getLocn().getLongitude(),this.sampleLocn.getLongitude(),AutoTestConf.DELTA);
        assertEquals(0,this.testStop.getRoutes().size());
        boolean work = false;
        try{
            this.testStop.getRoutes().add(RouteManager.getInstance().getRouteWithNumber(AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN))));
        } catch (UnsupportedOperationException e){
            work = true;
            //Expected
        }
        assertTrue(work);
    }

    @Test
    public void testSetName(){
        this.sampleName = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN));
        this.testStop.setName(this.sampleName);
        assertTrue(this.testStop.getName().equals(this.sampleName));
    }

    @Test
    public void testSetLocn(){
        this.sampleLocn = new LatLon(AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble());
        this.testStop.setLocn(this.sampleLocn);
        assertEquals(this.sampleLocn,this.testStop.getLocn());
    }

    @Test
    public void testRemoveRouteNotExists(){
        /*
        try{
            this.testStop.removeRoute(RouteManager.getInstance().getRouteWithNumber(AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN))));
            fail("Missing expected exception!");
        } catch (IllegalArgumentException e){
            // Expected
        }
        */
        try {
            this.testStop.removeRoute(RouteManager.getInstance().getRouteWithNumber(AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN))));
        } catch (Exception e){
            fail("Unexpected exceptions!");
        }
    }

    @Test
    public void testAddBusNotInRoute(){
        Bus sampleBus = new Bus(RouteManager.getInstance().getRouteWithNumber(
                AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN))),
                AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble(),
                AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN)),
                AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN)));
        try{
            this.testStop.addBus(sampleBus);
            fail("Missing exceptions!");
        } catch (RouteException e){
            // Expected
        }
    }

    @Test
    public void testIteratorRemove(){
        List<Arrival> sampleList= new LinkedList<Arrival>();
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            Arrival arr = new Arrival(AutoTestConf.RND.nextInt(131072),null,null);
            sampleList.add(arr);
            this.testStop.addArrival(arr);
        }
        Collections.sort(sampleList);
        Set<Arrival> toRemove = new HashSet<Arrival>();
        Iterator i = this.testStop.iterator();
        for(int k=0;k<sampleList.size();k++){
            assertEquals(k<sampleList.size(),i.hasNext());
            assertEquals(sampleList.get(k).getTimeToStopInMins(),((Arrival) i.next()).getTimeToStopInMins());
            if(AutoTestConf.RND.nextInt(100)>=80){
                try {
                    i.remove();
                    toRemove.add(sampleList.get(k));
                } catch (Exception e){
                    fail("Unexpected exceptions!");
                }
            }
        }
        assertFalse(i.hasNext());
        sampleList.removeAll(toRemove);

        i = this.testStop.iterator();
        for(int k=0;k<sampleList.size();k++){
            assertEquals(k<sampleList.size(),i.hasNext());
            assertEquals(sampleList.get(k).getTimeToStopInMins(),((Arrival) i.next()).getTimeToStopInMins());
        }
        assertFalse(i.hasNext());
    }

    @Test
    public void testIteratorRemoveWithoutNext(){
        try{
            this.testStop.iterator().remove();
            fail("Missing expections!");
        } catch (UnsupportedOperationException e){
            //Expected
        }

        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            Arrival arr = new Arrival(AutoTestConf.RND.nextInt(131072),null,null);
            this.testStop.addArrival(arr);
        }

        try{
            this.testStop.iterator().remove();
            fail("Missing expections!");
        } catch (UnsupportedOperationException e){
            //Expected
        }
    }
}
