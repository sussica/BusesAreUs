package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test Arrivals
 */
public class ArrivalsTest1 {
    private Route sampleRoute;
    private Arrival testArrival;

    @BeforeEach
    public void setup() {
        this.sampleRoute = RouteManager.getInstance().getRouteWithNumber("43");
        this.testArrival = new Arrival(23, "Home", this.sampleRoute);
    }

    @Test
    public void testConstructor() {
        assertEquals(23, this.testArrival.getTimeToStopInMins());
        assertEquals(this.sampleRoute, this.testArrival.getRoute());
        assertTrue(this.testArrival.getDestination().equals("Home"));
    }

    @Test
    public void testSetStatus(){
        String[] status = new String[]{" ","*","-","+"};
        for(String s:status){
            this.testArrival.setStatus(s);
            assertTrue(this.testArrival.getStatus().equals(s));
        }

       /* boolean work = false;
        try {
            this.testArrival.setStatus(AutoTestConf.RandomStringGen.generate(1024));
        } catch (IllegalArgumentException e){
            work = true;
        }
        if(!work)
            fail("Missing expected exception!");
           */
        try {
            this.testArrival.setStatus(AutoTestConf.RandomStringGen.generate(1024));
        } catch (Exception e){
            fail("Unexpected exceptions!");
        }
    }

    @Test
    public void testCompareTo(){
        for(int k=0;k<AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST);k++){
            int time = 0==k?23:AutoTestConf.RND.nextInt();
            String dest = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(1024));
            assertEquals(23<time,0>this.testArrival.compareTo(new Arrival(time,dest,this.sampleRoute)));
            assertEquals(23>time,0>(new Arrival(time,dest,this.sampleRoute)).compareTo(this.testArrival));
        }
    }
}
