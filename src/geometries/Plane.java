package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * This class represents a plane in three-dimensional space by point on the plane and vertical vector.
 */
public class Plane extends Geometry {
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
            throw new IllegalArgumentException("cant build a plane from 3 points on the same ray"+p1+p2+p3);
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
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        if(q0.equals(p0) || isZero(v.dotProduct(normal))) return null;
        double t = alignZero(normal.dotProduct(q0.subtract(p0)) / normal.dotProduct(v)); // t = n(q0-p0) / n*v
        if(t <= 0) return null;
        return List.of(ray.getPoint(t)); // p = p0+t*v
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        if(q0.equals(p0) || isZero(v.dotProduct(normal))) return null;
        double t = alignZero(normal.dotProduct(q0.subtract(p0)) / normal.dotProduct(v)); // t = n(q0-p0) / n*v
        if(t <= 0 || alignZero(maxDistance - t) <= 0) return null;
        return List.of(new GeoPoint(this, ray.getPoint(t))); // p = p0+t*v
    }

    /**
     * create boundary box for the plane
     */
    @Override
    public void createBoundingBox() {
        box = null;
    }

}
