package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * This class represents a plane in three-dimensional space by point on the plane and vertical vector.
 */
public class Plane implements Geometry {
    private final Point q0;
    private final Vector normal;

    /**
     * Constructor with point on the plane and normal vector.
     * @param q0 point on the plane
     * @param normal normal vector
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructor with 3 points on the plane (select one point for the field q0 and find a normal vector to those points).
     * @param p1 first point.
     * @param p2 second point.
     * @param p3 third point.
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        try{
            this.normal = (p1.subtract(p2)). crossProduct (p1.subtract(p3)).normalize();
        }
        catch(IllegalArgumentException ex) //change the message to be more specific.
        {
            throw new IllegalArgumentException("cant build a plane from 3 points on the same ray");
        }
    }

    /**
     * getter for field q0
     * @return q0- the point on the plane.
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * getter for field normal
     * @return the vector that is normal to the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
