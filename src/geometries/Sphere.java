package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere implements Geometry{
    private Point center;
    private double radius;

    public Sphere(Point center, double radius) {
        if (radius <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
