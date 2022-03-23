package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;


public interface Intersectable {
    /**
     * find intersections between the geometry and ray.
     * @param ray
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray);
}
