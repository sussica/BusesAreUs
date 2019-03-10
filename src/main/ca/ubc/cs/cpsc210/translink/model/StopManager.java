package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import ca.ubc.cs.cpsc210.translink.util.SphericalGeometry;

import java.util.*;

/**
 * Manages all bus stops.
 * <p>
 * Singleton pattern applied to ensure only a single instance of this class that
 * is globally accessible throughout application.
 */
// TODO: Task 2: Complete all the methods of this class

public class StopManager implements Iterable<Stop> {
    public static final int RADIUS = 10000;
    private static StopManager instance;
    // Use this field to hold all of the stops.
    // Do not change this field or its type, as the iterator method depends on it
    private Map<Integer, Stop> stopMap;
    private Stop selected;

    /**
     * Constructs stop manager with empty collection of stops and null as the selected stop
     */
    private StopManager() {
        stopMap = new HashMap<Integer, Stop>();
        this.selected = null;
    }

    /**
     * Gets one and only instance of this class
     *
     * @return instance of class
     */
    public static StopManager getInstance() {
        // Do not modify the implementation of this method!
        if (instance == null) {
            instance = new StopManager();
        }

        return instance;
    }

    public Stop getSelected() {
        return selected;
    }

    /**
     * Get stop with given number, creating it and adding it to the collection of all stops if necessary.
     * If it is necessary to create a new stop, then provide it with an empty string as its name,
     * and a default location somewhere in the lower mainland as its location.
     * <p>
     * In this case, the correct name and location of the stop will be provided later
     *
     * @param number the number of this stop
     * @return stop with given number
     */
    public Stop getStopWithNumber(int number) {
        LatLon defaultLocn = new LatLon(1, 1);

        if (stopMap.containsKey(number)) {
            return stopMap.get(number);
        } else {
            Stop st = new Stop(number, "", defaultLocn);
            stopMap.put(number, st);
            return st;
        }
    }


    /**
     * Get stop with given number, creating it and adding it to the collection of all stops if necessary,
     * using the given name and location
     *
     * @param number the number of this stop
     * @param name   the name of this stop
     * @param locn   the location of this stop
     * @return stop with given number
     */
    public Stop getStopWithNumber(int number, String name, LatLon locn) {
        if (stopMap.containsKey(number)) {
            stopMap.get(number).setName(name);
            stopMap.get(number).setLocn(locn);
            return stopMap.get(number);
        } else {
            Stop st = new Stop(number, name, locn);
            stopMap.put(number, st);
            return st;
        }
    }

    /**
     * Set the stop selected by user
     *
     * @param selected stop selected by user
     * @throws StopException when stop manager doesn't contain selected stop
     */
    public void setSelected(Stop selected) throws StopException {
        if (stopMap.containsValue(selected)) {
            this.selected = selected;
        } else
            throw new StopException("No such stop: " + selected.getNumber() + " " + selected.getName());
    }

    /**
     * Clear selected stop (selected stop is null)
     */
    public void clearSelectedStop() {
        this.selected = null;
    }

    /**
     * Get number of stops managed
     *
     * @return number of stops added to manager
     */
    public int getNumStops() {
        return stopMap.size();
    }

    /**
     * Remove all stops from stop manager
     */
    public void clearStops() {
        stopMap.clear();

    }

    /**
     * Find nearest stop to given point.  Returns null if no stop is closer than RADIUS metres.
     *
     * @param pt point to which nearest stop is sought
     * @return stop closest to pt but less than RADIUS away; null if no stop is within RADIUS metres of pt
     */
    public Stop findNearestTo(LatLon pt) {
        Stop rsf = null;
        double dis;
        for(Stop s:stopMap.values()){
            if ((dis = SphericalGeometry.distanceBetween(s.getLocn(),pt))<RADIUS){
                if (rsf==null||dis <(SphericalGeometry.distanceBetween(rsf.getLocn(),pt )))
                {
                rsf = s;
                }
            }
        }
        return rsf;
    }

    @Override
    public Iterator<Stop> iterator() {
        // Do not modify the implementation of this method!
        return stopMap.values().iterator();
    }
}
