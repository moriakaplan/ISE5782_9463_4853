package primitives;

import java.util.Objects;

public class Point {
    final Double3 xyz;

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
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
        return "Point{" +
                "coordinates=" + xyz +
                '}';
    }

    public Vector subtract(Point other) {
        return new Vector(this.xyz.subtract(other.xyz)); /*look at the ctor of vector*/
    }

    public Point add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }

    public double distanceSquared(Point other) {
        Double3 d = this.xyz.subtract(other.xyz);
        return d.d1 * d.d1 + d.d2 * d.d2 + d.d3 * d.d3;
    }

    public double distance(Point other) {
        return Math.sqrt(this.distanceSquared(other));
    }
}
