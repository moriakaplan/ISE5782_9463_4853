package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Override
    public Vector getNormal(Point p) {
        Point p0 = axisRay.getP0();
        Vector v = axisRay.getDir();
        Point secondEdgeP=p0.add(v.scale(height));
        if(p.subtract(p0).dotProduct(v) == 0) //for the case when the point is on the base of the cylinder.
            return v.scale(-1);
        if(p.subtract(secondEdgeP).dotProduct(v) ==0 ) //for the case when the point is on the second base of the cylinder.
            return v;
        return super.getNormal(p);
    }
}
