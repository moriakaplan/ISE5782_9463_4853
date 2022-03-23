package geometries;
import primitives.*;

import java.util.List;

import static primitives.Util.isZero;


/**
 * This class represents an infinity Tube in three-dimensional space by the center axis of the tube (ray) and its radius.
 */
public class Tube implements Geometry{
    protected Ray axisRay;
    protected double radius;

    /**
     * Constructor, using the values for the fields.
     * @param axisRay the center axis of the tube.
     * @param radius the length of the radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        if (radius <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * getter for field AxisRay.
     * @return the center axis of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Getter for field radius.
     * @return the length of the radius of the tube.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        // the formula:
        // t = v(p-p0)
        // o = p0 + t*v
        // normal = normalize(p-o)
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();
        double t = v.dotProduct(p.subtract(p0));
        Point center;
        if(isZero(t)) center = p0;
        else center = p0.add(v.scale(t));
        return p.subtract(center).normalize();
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
