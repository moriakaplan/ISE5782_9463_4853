package primitives;

import java.util.Objects;

/**
 * This class represents a point in three-dimensional space by using class Double3.
 */
public class Point {
    final Double3 xyz;

    /**
     * Constructor to initialize point based object with its three number values
     *
     * @param x first number value
     * @param y second number value
     * @param z third number value
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructor to initialize point based object with Double3 coordinate
     *
     * @param d Double3 value
     */
    Point(Double3 d) {
        this.xyz = new Double3(d.d1, d.d2, d.d3);
    }

    /**
     * Subtract two floating point triads into a new triad where each couple of
     * @param other right handle side operand for addition
     * @return result of add
     */
    public Vector subtract(Point other){
        return new Vector(this.xyz.subtract(other.xyz));
    }

    /**
     * Sum two floating point triads into a new triad where each couple of numbers
     * is summarized
     *
     * @param vec right handle side operand for addition
     * @return result of add
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }

    /**
     * Calculate the distance squared between two floating points
     *
     * @param other point for calculate the distance to
     * @return the distance to another point
     */
    public double distanceSquared(Point other) {
        Double3 d = this.xyz.subtract(other.xyz);
        return d.d1 * d.d1 + d.d2 * d.d2 + d.d3 * d.d3;
    }

    /**
     * Calculate the distance between two floating points
     *
     * @param other point for calculate the distance to
     * @return the distance to another point
     */
    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point " + xyz;
    }


}
