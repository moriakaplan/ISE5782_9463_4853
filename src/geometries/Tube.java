package geometries;
import primitives.*;

public class Tube implements Geometry{
    Ray axisRay;
    double radius;

    public Tube(Ray axisRay, double radius) {
        if (radius <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

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
