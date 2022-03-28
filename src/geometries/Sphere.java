package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;

/**
 * This class represents a sphere in the space by center point and radius.
 */
public class Sphere implements Geometry {
    private final Point center;
    private final double radius;

    /**
     * constructor for initialize the sphere data.
     *
     * @param center the center point of the sphere.
     * @param radius the length of the radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.center = center;
        this.radius = radius;
    }

    /**
     * getter for center point.
     *
     * @return the point that is the center of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getter for field radius.
     *
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        Point p0 = ray.getP0();
        if (center.equals(p0))
            return List.of(ray.getPoint(radius));
        Vector u = center.subtract(p0);
        double tM = u.dotProduct(ray.getDir());
        double d = sqrt(u.lengthSquared() - tM * tM);
        if (alignZero(d - radius) >= 0) return null;
        double tH = sqrt(radius * radius - d * d);
        double t1 = alignZero(tM + tH);
        if (t1 <= 0)
            return null;
        double t2 = alignZero(tM - tH);
        if (t2 <= 0)
            return List.of(ray.getPoint(t1));
        return List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}
