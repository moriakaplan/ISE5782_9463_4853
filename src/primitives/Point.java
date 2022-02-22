package primitives;

import java.util.Objects;

public class Point {
    final Double3 coordinates;

    public Point(double x, double y, double z) {
        this.coordinates = new Double3(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return coordinates.equals(point.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "Point{" +
                "coordinates=" + coordinates +
                '}';
    }

    public Vector subtract(Point other) {
        return new Vector(this.coordinates.subtract(other.coordinates)); /*look at the ctor of vector*/
    }

    public Point add(Vector vec) {
        return new Vector(this.coordinates.add(other.coordinates));
    }

    public double distanceSquared(Point other) {
        Double3 d = this.coordinates.subtract(other.coordinates);
        return d.d1 * d.d1 + d.d2 * d.d2 + d.d3 * d.d3;
    }

    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }
}
