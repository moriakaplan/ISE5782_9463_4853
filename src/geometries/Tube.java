package geometries;
import primitives.*;

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
        return null;
    }
}
