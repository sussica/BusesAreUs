package ca.ubc.cs.cpsc210.translink.tests.parsers.ExceptionTest;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class ArrivalsParserExceptionTest {
    @BeforeEach
    public void runBefore(){
        RouteManager.getInstance().clearRoutes();
        StopManager.getInstance().clearStops();
    }

    @Test
    public void testErrorSyntax(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/errorSyntax-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("Missing exception!");
        } catch (JSONException e){
            //Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testNotArray(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("illegal/notArray-arrival.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("Missing exception!");
        } catch (JSONException e){
            //Expected
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }

    @Test
    public void testEntireFileEmptyArray(){
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        try {
            ArrivalsParser.parseArrivals(s,"[]");
        } catch (Exception e){
            fail("Unexpected exception!");
        }
    }
}
