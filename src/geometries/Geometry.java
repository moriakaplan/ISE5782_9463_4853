package geometries;

import primitives.*;

/**
 * An interface for geometric objects that can give an normal vector for any point on the object.
 */
public interface Geometry extends Intersectable{
    /**
     * find an normal vector that is perpendicular to the plane of the geometric object.
     * @param p point on thecasing of the geometry.
     * @return normal vector.
     */
    Vector getNormal(Point p);
}
