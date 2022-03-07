package geometries;

import primitives.Ray;

/**
 * a cylinder object in the space, extend of class Tube
 */
public class Cylinder extends Tube{
    private double height;

    /**
     * constructor for initialize data by radius, height and center axis.
     * @param axisRay the center ray of the cylinder.
     * @param radius the radius of the cylinder.
     * @param height the height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if (height <= 0)
        {
            throw new IllegalArgumentException("radius must be a positive number");
        }
        this.height = height;
    }

    /**
     * getter for field height.
     * @return the height of the cylinder.
     */
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
