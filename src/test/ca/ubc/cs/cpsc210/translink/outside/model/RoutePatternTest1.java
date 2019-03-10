package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.tests.AutoTestConf;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutePatternTest1 {
    private Route sampleRoute;
    private RoutePattern testRoutePattern;

    private static int MAX_STR_LEN = 1024;
    private String sampleDest;
    private String sampleDirc;
    private String sampleName;

    @BeforeEach
    public void runBefore() {
        this.sampleRoute = RouteManager.getInstance()
                .getRouteWithNumber(AutoTestConf.RandomStringGen.generate(1024));
        this.sampleDest = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(RoutePatternTest1.MAX_STR_LEN));
        this.sampleDirc = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(RoutePatternTest1.MAX_STR_LEN));
        this.sampleName = AutoTestConf.RandomStringGen.generate(AutoTestConf.RND.nextInt(RoutePatternTest1.MAX_STR_LEN));

        this.testRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
    }

    public void testConstructor() {
        assertTrue(this.sampleName.equals(this.testRoutePattern.getName()));
        assertTrue(this.sampleDest.equals(this.testRoutePattern.getDestination()));
        assertTrue(this.sampleDirc.equals(this.testRoutePattern.getDirection()));
        Route r;
        try {
            Field field = RoutePattern.class.getDeclaredField("route");
            field.setAccessible(true);
            r = (Route) field.get(this.testRoutePattern);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("No such filed called 'route' in class RoutePattern. Please change 'route' to your own filed name and try again.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("No access to filed 'route' in class RoutePattern. Please change 'route' to your own filed name and try again.");
        }
        assertEquals(this.sampleRoute, r);

        List<LatLon> p;
        try {
            Field field = RoutePattern.class.getDeclaredField("paths");
            field.setAccessible(true);
            p = (List<LatLon>) field.get(this.testRoutePattern);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("No such filed called 'paths' in class RoutePattern. Please change 'paths' to your own filed name and try again.");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("No access to filed 'paths' in class RoutePattern. Please change 'paths' to your own filed name and try again.");
        }
        assertTrue(p.isEmpty());
        boolean work = false;
        try {
            p.add(new LatLon(0, 0));
        } catch (UnsupportedOperationException e) {
            work = true;

            assertTrue(work);
        }
    }

        @Test
        public void testSetPaths () {
            List<LatLon> samplePaths = new LinkedList<LatLon>();
            for (int k = 0; k < AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST); k++)
                samplePaths.add(new LatLon(AutoTestConf.RND.nextDouble(), AutoTestConf.RND.nextDouble()));
            this.testRoutePattern.setPath(samplePaths);
            List<LatLon> cachedPaths = this.testRoutePattern.getPath();
            boolean work = false;
            try {
                cachedPaths.add(samplePaths.get(AutoTestConf.RND.nextInt(samplePaths.size())));
            } catch (UnsupportedOperationException e) {
                work = true;
                // Excepted
            }
            //if(!work) fail("Missing expected exceptions!");
            assertTrue(work);
            for (int k = 0; k < this.testRoutePattern.getPath().size(); k++) {
                assertEquals(this.testRoutePattern.getPath().get(k), samplePaths.get(k));
            }
/*
            try {
                Field field = RoutePattern.class.getDeclaredField("paths");
                field.setAccessible(true);
                cachedPaths = (List<LatLon>) field.get(this.testRoutePattern);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException("No such filed called 'paths' in class RoutePattern. Please change 'paths' to your own filed name and try again.");
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("No access to filed 'paths' in class RoutePattern. Please change 'paths' to your own filed name and try again.");
            }
            work = false;
            try {
                cachedPaths.add(samplePaths.get(AutoTestConf.RND.nextInt(samplePaths.size())));
            } catch (UnsupportedOperationException e) {
                work = true;
                // Excepted
            }
            if (!work) fail("Missing expected exceptions!");
            */
        }

        @Test
        public void testSetDest () {
            String oldDest = new String(this.sampleDest);
            do {
                this.sampleDest = AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN);
            } while (oldDest.equals(this.sampleDest));

            assertFalse(this.sampleDest.equals(this.testRoutePattern.getDestination()));
            this.testRoutePattern.setDestination(this.sampleDest);
            assertTrue(this.sampleDest.equals(this.testRoutePattern.getDestination()));
        }

        @Test
        public void testSetDirc () {
            String oldDirc = new String(this.sampleDirc);
            do {
                this.sampleDirc = AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN);
            } while (oldDirc.equals(this.sampleDirc));

            assertFalse(this.sampleDirc.equals(this.testRoutePattern.getDirection()));
            this.testRoutePattern.setDirection(this.sampleDirc);
            assertTrue(this.sampleDirc.equals(this.testRoutePattern.getDirection()));
        }

        @Test
        public void testEqualDifferentClass () {
            assertFalse(this.testRoutePattern.equals(this.sampleRoute));
            assertFalse(this.testRoutePattern.equals(null));
        }

        @Test
        public void testEqualDifferentDest () {
            for (int k = 0; k < AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST); k++) {
                RoutePattern oldRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                String oldDest = new String(this.sampleDest);
                do {
                    this.sampleDest = AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN);
                } while (oldDest.equals(this.sampleDest));
                this.testRoutePattern.setDestination(this.sampleDest);
                assertTrue(oldRoutePattern.equals(this.testRoutePattern));
                assertEquals(oldRoutePattern.hashCode(), this.testRoutePattern.hashCode());
            }
        }

        @Test
        public void testEqualDifferentDirc () {
            for (int k = 0; k < AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST); k++) {
                RoutePattern oldRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                String oldDirc = new String(this.sampleDirc);
                do {
                    this.sampleDirc = AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN);
                } while (oldDirc.equals(this.sampleDirc));
                this.testRoutePattern.setDirection(this.sampleDirc);
                assertTrue(oldRoutePattern.equals(this.testRoutePattern));
                assertEquals(oldRoutePattern.hashCode(), this.testRoutePattern.hashCode());
            }
        }

        @Test
        public void testEqualDifferentName () {
            for (int k = 0; k < AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST); k++) {
                RoutePattern oldRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                String oldName = new String(this.sampleName);
                do {
                    this.sampleName = AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN);
                } while (oldName.equals(this.sampleName));
                this.testRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                assertFalse(oldRoutePattern.equals(this.testRoutePattern));
                assertFalse(oldRoutePattern.hashCode() == this.testRoutePattern.hashCode());
            }
        }

        @Test
        public void testEqualDifferentRoute () {
            for (int k = 0; k < AutoTestConf.RND.nextInt(AutoTestConf.MAX_TEST); k++) {
                RoutePattern oldRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                String oldRouteNum = new String(this.sampleRoute.getNumber());
                do {
                    this.sampleRoute = RouteManager.getInstance().getRouteWithNumber(
                            AutoTestConf.RandomStringGen.generate(RoutePatternTest1.MAX_STR_LEN));
                } while (this.sampleRoute.getNumber().equals(oldRouteNum));
                this.testRoutePattern = new RoutePattern(this.sampleName, this.sampleDest, this.sampleDirc, this.sampleRoute);
                assertTrue(oldRoutePattern.equals(this.testRoutePattern));
                assertEquals(oldRoutePattern.hashCode(), this.testRoutePattern.hashCode());
            }
        }

        @Test
        public void testUnmodifiableGetPath () {
            //System.out.println(this.testRoutePattern.getPath().getClass().getSimpleName());
            assertTrue(this.testRoutePattern.getPath().getClass().getSimpleName().contains("Unmodifiable"));
        }
}
