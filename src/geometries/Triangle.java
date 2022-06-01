package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * class for triangle, a type of polygon with 3 vertices.
 */
public class Triangle extends Polygon {

    /**
     * constructor for initialize by 3 points
     *
     * @param p1 first point- one fo the vertices of the triangle.
     * @param p2 second point- one fo the vertices of the triangle.
     * @param p3 Third point- one fo the vertices of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> l = plane.findIntersections(ray);
        if (l == null) return null;
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);
        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);
        Double dp1 = alignZero(v.dotProduct(n1));
        Double dp2 = alignZero(v.dotProduct(n2));
        Double dp3 = alignZero(v.dotProduct(n3));
        if ((dp1 > 0 && dp2 > 0 && dp3 > 0) || (dp1 < 0 && dp2 < 0 && dp3 < 0))
            return l;
        else return null;
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> l = plane.findGeoIntersectionsHelper(ray, maxDistance);
        if (l == null) return null;
        Point p0 = ray.getP0();
        Vector v = ray.getDir();
        Vector v1, v2, v3;
        Vector n1, n2, n3;
        try {
            v1 = vertices.get(0).subtract(p0);
            v2 = vertices.get(1).subtract(p0);
            v3 = vertices.get(2).subtract(p0);

            n1 = v1.crossProduct(v2);
            n2 = v2.crossProduct(v3);
            n3 = v3.crossProduct(v1);
        }
        catch(IllegalArgumentException ex){
            return null;
        }
        Double dp1 = alignZero(v.dotProduct(n1));
        Double dp2 = alignZero(v.dotProduct(n2));
        Double dp3 = alignZero(v.dotProduct(n3));
        if ((dp1 > 0 && dp2 > 0 && dp3 > 0) || (dp1 < 0 && dp2 < 0 && dp3 < 0))
            return List.of(new GeoPoint(this, l.get(0).point));
        return null;
    }
}
