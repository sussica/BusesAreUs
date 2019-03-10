package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StopRouteAssociation1 {
    private Stop testStop;
    private Route testRoute;
    private int sampleStopNum;
    private String sampleRouteNum;

    @BeforeEach
    public void runBefore(){
        StopManager.getInstance().clearStops();
        RouteManager.getInstance().clearRoutes();
        this.sampleStopNum = AutoTestConf.RND.nextInt();
        this.sampleRouteNum = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(AutoTestConf.MAX_STR_LEN));
        this.testStop = StopManager.getInstance().getStopWithNumber(this.sampleStopNum);
        this.testRoute = RouteManager.getInstance().getRouteWithNumber(this.sampleRouteNum);
    }

    @Test
    public void testAddStop(){
        this.testRoute.addStop(this.testStop);
        assertTrue(this.testRoute.hasStop(this.testStop));
        assertTrue(this.testStop.onRoute(this.testRoute));
    }

    @Test
    public void testAddRoute(){
        this.testStop.addRoute(this.testRoute);
        assertTrue(this.testRoute.hasStop(this.testStop));
        assertTrue(this.testStop.onRoute(this.testRoute));
    }

    @Test
    public void testAddStopSequence(){
        Set<Integer> usedStopNum = new HashSet<Integer>();
        List<Stop> stops = new LinkedList<Stop>();
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            usedStopNum.add(this.sampleStopNum);
            do {
                this.sampleStopNum = AutoTestConf.RND.nextInt();
            } while (usedStopNum.contains(this.sampleStopNum));
            this.testStop = StopManager.getInstance().getStopWithNumber(this.sampleStopNum);
            stops.add(this.testStop);
            this.testRoute.addStop(this.testStop);
        }

        assertEquals(stops.size(),this.testRoute.getStops().size());
        for(int k=0;k<stops.size();k++){
            assertEquals(stops.get(k),this.testRoute.getStops().get(k));
        }
    }
}
