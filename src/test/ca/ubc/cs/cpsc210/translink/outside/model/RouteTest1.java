package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest1 {
    private Route testRoute;
    private String sampleNum;

    @BeforeEach
    public void runBefore(){
        RouteManager.getInstance().clearRoutes();
        StopManager.getInstance().clearStops();
        this.sampleNum = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        this.testRoute = new Route(this.sampleNum);
    }

    @Test
    public void testConstructor(){
        assertTrue(this.sampleNum.equals(this.testRoute.getNumber()));
        assertTrue(this.testRoute.toString().equals("Route "+this.sampleNum));
        assertTrue(this.testRoute.getName().equals(new String()));
    }

    @Test
    public void testSetName(){
        String sampleName = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        this.testRoute.setName(sampleName);
        assertTrue(sampleName.equals(this.testRoute.getName()));
    }

    @Test
    public void testAddPattern(){
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++) {
            RoutePattern samplePattern = new RoutePattern(
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    this.testRoute);
            this.testRoute.addPattern(samplePattern);
            assertTrue(samplePattern.equals(this.testRoute.getPattern(samplePattern.getName())));
        }
    }

    @Test
    public void testAddRemoveStop(){
        int stopNum = 0;
        Stop sampleStop = null;
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++) {
            stopNum = 0==(k&1)?AutoTestConf.RND.nextInt():stopNum;
            sampleStop = StopManager.getInstance().getStopWithNumber(stopNum,
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    new LatLon(AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble()));
            if(!this.testRoute.hasStop(new Stop(stopNum,null,null))){
                this.testRoute.addStop(sampleStop);
                assertTrue(this.testRoute.hasStop(new Stop(stopNum,null,null)));
            } else {
                this.testRoute.removeStop(sampleStop);
                assertFalse(this.testRoute.hasStop(sampleStop));
                /*
                boolean work = false;
                try {
                    this.testRoute.removeStop(new Stop(stopNum, null, null));
                } catch (IllegalArgumentException e){
                    work = true;
                    //Expected
                }
                if(!work) fail("Missing expected exceptions!");
                */
                try{
                    this.testRoute.removeStop(StopManager.getInstance().getStopWithNumber(stopNum));
                } catch (Exception e){
                    fail("Unexpected exceptions!");
                }
                this.testRoute.addStop(StopManager.getInstance().getStopWithNumber(stopNum));
                this.testRoute.addStop(new Stop(stopNum,null,null));

            }
        }
    }

    @Test
    public void testGetStops(){
        List<Stop> sampleStops = new LinkedList<Stop>();
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++) {
            int stopNum = AutoTestConf.RND.nextInt();
            Stop sampleStop = new Stop(stopNum,
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    new LatLon(AutoTestConf.RND.nextDouble(), AutoTestConf.RND.nextDouble()));
            if (!this.testRoute.hasStop(new Stop(stopNum, null, null))) {
                this.testRoute.addStop(sampleStop);
                sampleStops.add(sampleStop);
            }
            assertEquals(sampleStops.size(),this.testRoute.getStops().size());
            for(int i=0;i<sampleStops.size();i++)
                assertTrue(this.testRoute.getStops().get(i).equals(sampleStops.get(i)));
            boolean work = false;
            try{
                this.testRoute.getStops().remove(0);
            } catch (UnsupportedOperationException e){
                work = true;
                // Excepted
            }
            if(!work) fail("Missing excepted exceptions!");
        }
    }

    @Test
    public void testGetPatternName(){
        assertEquals(0,this.testRoute.getPatterns().size());
        String samplePatternName = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        assertTrue(this.testRoute.getPattern(samplePatternName).equals(new RoutePattern(samplePatternName,null,null,this.testRoute)));
        assertTrue(this.testRoute.getPattern(samplePatternName).equals(new RoutePattern(samplePatternName,null,null,this.testRoute)));
        String sampleDest = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        String sampleDirc = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        assertTrue(this.testRoute.getPattern(samplePatternName).equals(this.testRoute.getPattern(samplePatternName,sampleDest,sampleDirc)));
        assertTrue(this.testRoute.getPattern(samplePatternName).getDestination().equals(sampleDest));
        assertTrue(this.testRoute.getPattern(samplePatternName).getDirection().equals(sampleDirc));
        samplePatternName = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
        assertTrue(this.testRoute.getPattern(samplePatternName,null,null).equals(new RoutePattern(samplePatternName,null,null,this.testRoute)));
    }

    @Test
    public void testIterator(){
        List<Stop> stops = new LinkedList<Stop>();
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            Stop sampleStop = new Stop(AutoTestConf.RND.nextInt(),
                    AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN),
                    new LatLon(AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble()));
            this.testRoute.addStop(sampleStop);
            stops.add(sampleStop);
        }
        int index=0;
        for(Stop s:this.testRoute)
            assertTrue(s.equals(stops.get(index++)));
    }

    @Test
    public void testEquals(){
        assertFalse(this.testRoute.equals(null));
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            Route oldRoute = this.testRoute;
            this.sampleNum = AutoTestConf.RandomStringGen.generate(AutoTestConf.MAX_STR_LEN);
            this.testRoute = new Route(this.sampleNum);
            assertFalse(this.testRoute.equals(oldRoute));
            assertTrue(this.testRoute.equals(new Route(this.sampleNum)));
        }
    }
}
