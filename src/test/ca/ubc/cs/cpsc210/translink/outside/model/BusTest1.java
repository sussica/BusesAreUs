package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BusTest1 {
    private Bus testBus;
    private Route sampleRoute;
    private static int MAX_STR_LEN = 1024;
    private LatLon sampleLatLon;
    private String sampleDest;
    private String sampleTime;

    @BeforeEach
    public void runBefore(){
        this.sampleRoute = new Route(AutoTestConf.RandomStringGen.generate(
                AutoTestConf.RND.nextInt(BusTest1.MAX_STR_LEN)));
        this.sampleTime = AutoTestConf.RandomStringGen.generate(
                AutoTestConf.RND.nextInt(BusTest1.MAX_STR_LEN));
        this.sampleDest = AutoTestConf.RandomStringGen.generate(
                AutoTestConf.RND.nextInt(BusTest1.MAX_STR_LEN));
        this.sampleLatLon = new LatLon(AutoTestConf.RND.nextDouble(),AutoTestConf.RND.nextDouble());
        this.testBus = new Bus(this.sampleRoute,this.sampleLatLon.getLatitude(),
                this.sampleLatLon.getLongitude(), this.sampleDest,this.sampleTime);
    }

    @Test
    public void testConstructor(){
        assertTrue(this.sampleRoute.equals(this.testBus.getRoute()));
        assertTrue(this.sampleLatLon.equals(this.testBus.getLatLon()));
        assertTrue(this.sampleDest.equals(this.testBus.getDestination()));
        assertTrue(this.sampleTime.equals(this.testBus.getTime()));
    }
}
