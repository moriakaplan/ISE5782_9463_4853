package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 *
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

    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
