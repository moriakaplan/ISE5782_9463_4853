package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This class represents a sphere in the space by center point and radius.
 */
public class Sphere implements Geometry{
    private final Point center;
    private final double radius;

    /**
     * constructor for initialize the sphere data.
     * @param center the center point of the sphere.
     * @param radius the length of the radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        if (radius <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.center = center;
        this.radius = radius;
    }

    /**
     * getter for center point.
     * @return the point that is the center of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getter for field radius.
     * @return the radius of the sphere.
     */
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
        return p.subtract(center).normalize();
    }
}
