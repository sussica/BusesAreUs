package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.BusParser;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BusParserTest1 {

    @BeforeEach
    public void setup(){
        RouteManager.getInstance().clearRoutes();
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        List<String> routes = new ArrayList<>(Arrays.asList("014","004"));
        for(String rm: routes)
            s.addRoute(RouteManager.getInstance().getRouteWithNumber(rm));
    }

    @Test
    public void testBusLocationsParserNormal() throws JSONException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
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
    public void testBusNotOnTheRoute() throws JSONException{
        Stop s = StopManager.getInstance().getStopWithNumber(51480);
        s.addRoute(RouteManager.getInstance().getRouteWithNumber("014"));
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("buslocations.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        BusParser.parseBuses(s, data);

        assertEquals(2, s.getBuses().size());
    }

    @Test
    public void testErrorSyntax(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("illegal/errorSyntax1-bus.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        try {
            BusParser.parseBuses(s, data);
            fail("Missing expection!");
        } catch (JSONException e){
            //Expected
        }

        try {
            data = new FileDataProvider("illegal/errorSyntax2-bus.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        try {
            BusParser.parseBuses(s, data);
            fail("Missing expection!");
        } catch (JSONException e){
            //Expected
        }

    }

    @Test
    public void testBusLossField() {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearBuses();
        String data = "";

        try {
            data = new FileDataProvider("illegal/lostField-bus.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        try {
            BusParser.parseBuses(s, data);
        } catch (Exception e){
            fail("Unexpected exception!");
        }

        assertEquals(3, s.getBuses().size());
    }

}
