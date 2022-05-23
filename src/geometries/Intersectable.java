package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * abstract class for object that we can find intersections on them.
 */
public abstract class Intersectable {

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
