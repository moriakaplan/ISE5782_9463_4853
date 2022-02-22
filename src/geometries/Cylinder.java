package geometries;

import primitives.Ray;

public class Cylinder extends Tube{
    private double height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                '}';
    }
}
