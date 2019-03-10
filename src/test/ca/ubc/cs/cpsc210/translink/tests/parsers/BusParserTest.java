package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.BusParser;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BusParserTest {
    private Stop s;


    @BeforeEach
    public void runBefore(){
         s = StopManager.getInstance().getStopWithNumber(51479);
         s.addRoute(new Route("004"));
         s.addRoute(new Route("014"));
    }


    @Test
    public void testBusLocationsParserNormal() throws JSONException {
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("buslocations.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        BusParser.parseBuses(s, data);

        assertEquals(4, s.getBuses().size());
    }


    @Test
    public void testParseBus(){
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("testParseBus.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }
        try {BusParser.parseBuses(s,data);}
        catch (JSONException e){fail ("should not throw here");}
        Bus b =s.getBuses().get(0);
        Route o = new Route("014");
        assertEquals(o,b.getRoute());
        assertEquals("HASTINGS",b.getDestination());
        assertEquals(49.264067,b.getLatLon().getLatitude());
        assertEquals(-123.167150,b.getLatLon().getLongitude());
        assertEquals("12:52:08 pm",b.getTime());
    }
    @Test
    public void testParseBusException(){
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("testBusException.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }
        try {BusParser.parseBuses(s,data);}
        catch (JSONException e){fail ("should not throw here");}
        assertEquals(1,s.getBuses().size());
    }
}
