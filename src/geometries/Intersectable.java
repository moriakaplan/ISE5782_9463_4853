package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/**
 * abstract class for object that we can find intersections on them.
 */
public abstract class Intersectable {
    boolean bvh = false;
    public BoundingBox box;

    /**
     * class for boundary box
     */
    public class BoundingBox {
        /**
         * extreme node of box, containing minimal values for each axis
         */
        public Point min;
        /**
         * extreme node of box, containing maximal values for each axis
         */
        public Point max;

        /**
         * constructor for bounding box
         *
         * @param minimums minimal bounding values
         * @param maximums maximal bounding values
         */
        public BoundingBox(Point minimums, Point maximums) {
            min = minimums;
            max = maximums;
        }
    }

    public Intersectable bvhOn() {
        if (!bvh) {
            createBoundingBox();
        }
        bvh = true;
        return this;
    }

    public Intersectable bvhOff() {
        bvh = false;
        return this;
    }


    /**
     * return true if ray intersects object
     *
     * @param ray ray to check
     * @return whether ray intersects box
     * code taken from scratchapixel.com
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-acceleration-structure/bounding-volume-hierarchy-BVH-part1
     */
    public boolean intersectingBox(Ray ray) {
        if (!bvh || box == null)
            return true;
        Vector dir = ray.getDir();
        Point p0 = ray.getP0();
        double tmin = (box.min.getX() - p0.getX()) / dir.getX();
        double tmax = (box.max.getX() - p0.getX()) / dir.getX();

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }


        double tymin = (box.min.getY() - p0.getY()) / dir.getY();
        double tymax = (box.max.getY() - p0.getY()) / dir.getY();

        if (tymin > tymax) {
            double temp = tymin;
            tymin = tymax;
            tymax = temp;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (box.min.getZ() - p0.getZ()) / dir.getZ();
        double tzmax = (box.max.getZ() - p0.getZ()) / dir.getZ();

        if (tzmin > tzmax) {
            double temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
        }

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }



    /**
     * point on specific geometry.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * constructor
         * @param geometry the geometry of the geoPoint (that the point is belongs to)
         * @param point the point's coordinates
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry==geoPoint.geometry && Objects.equals(point, geoPoint.point); //***
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "geometry=" + geometry + ", point=" + point;
        }
    }

    public abstract void createBoundingBox();

    /**
     * find intersections between the geometry and ray.
     * @param ray
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find all the intersections between intersectable object and a ray.
     * @param ray a ray that night intersect this object.
     * @return the list of intersections in GeoPoint points.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find intersections between intersectable object and a ray- til a maximum specific distance from the beginning of the ray..
     * @param ray a ray that night intersect this object.
     * @param maxDistance maximum distance for the points that we return.
     * @return the list of intersections in GeoPoint points.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance){
        if (bvh && !intersectingBox(ray))
            return null;
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * helper abstract function for find intersection between intersectable object and a ray (with distance that smaller than the maximum).
     * do the real job.
     * @param ray a ray that night intersect this object.
     * @param maxDistance maximum distance for the points that we return.
     * @return the list of intersection in GeoPoint points.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);
}
