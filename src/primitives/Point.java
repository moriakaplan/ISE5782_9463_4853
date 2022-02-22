package primitives;

public class Point {
    final Double3 coordinates;

    public Point(double x, double y, double z) {
        this.coordinates = new Double3(x, y, z);
    }
    public Vector subtract(Point other)
    {
        return new Vector(this.coordinates.subtract(other.coordinates)); /*look at the ctor of vector*/
    }
    public Point add(Vector vec)
    {
        return new Vector(this.coordinates.add(other.coordinates));
    }
    public double distanceSquared(Point other)
    {
        Double3 d = this.coordinates.subtract(other.coordinates);
        return d.d1+d.d2+d.d3;
    }
    public double distance(Point other)
    {
        return Math.sqrt(this.distanceSquared(other));
    }
}
