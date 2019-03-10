package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A description of one pattern of a route
 * Each pattern has a name, destination, direction, list of points (of class LatLon), and Route
 */

// TODO: Task 2: Complete all the methods in this class

public class RoutePattern {
    private List<LatLon> mypath;
    private Route route;
    private String name;
    private String destination;
    private String direction;



    /**
     * Construct a new RoutePattern with the given information
     * @param name          the name of the pattern
     * @param destination   the destination
     * @param direction     the direction
     * @param route         the Route of which this is a pattern
     */
    public RoutePattern(String name, String destination, String direction, Route route) {
        this.name =name;
        this.destination =destination;
        this.direction = direction;
        this.route =route;
        mypath = new LinkedList<LatLon>();

    }

    /**
     * Get the pattern name
     * @return      the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the pattern destination
     * @return      the destination
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Get the pattern direction
     * @return      the direction
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Decide if two RoutePatterns are equal. Two route patterns are equal if their names are equal.
     * @param o         the other route pattern to compare to
     * @return          true if this is equal to o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if (getClass() != o.getClass()) return false;

        RoutePattern name = (RoutePattern) o;
        if(this.name == null)
            return name.getName() == null;
        else
        return this.name.equals(name.getName());
    }

    @Override
    public int hashCode() {

        return name.hashCode();

    }

    /**
     * Set the pattern path: list of coordinates
     * @param path      the path
     */
    public void setPath(List<LatLon> path) {
        mypath =path ;
    }

    /**
     * Return the list of coordinates making up this pattern
     *
     * @return      an unmodifiable list of the coordinates on this route pattern
     */
    public List<LatLon> getPath() {
        return Collections.unmodifiableList(this.mypath);
    }

    /**
     * Set the direction
     * @param direction     the direction
     */
    public void setDirection(String direction) {
        this.direction = direction;

    }

    /**
     * Set the destination
     * @param destination     the destination
     */
    public void setDestination(String destination) {
        this.destination =destination;
    }
}
