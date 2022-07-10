package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Geometries extends Intersectable {
    private List<Intersectable> shapes;

    /**
     * default constructor- create empty list.
     */
    public Geometries() {
        shapes = new LinkedList<Intersectable>();

    }

    /**
     * constructor- create list with geometries.
     *
     * @param geometries collection of geometries in the scene.
     */
    public Geometries(Intersectable... geometries) {
        shapes = new LinkedList<Intersectable>();
        add(geometries);
        if (bvh)
            createBoundingBox();
    }

    /**
     * add geometries to the list.
     *
     * @param geometries collection of geometries to add.
     */
    public void add(Intersectable... geometries) {
        for (Intersectable g :
                geometries) {
            shapes.add(g);
        }
        if (bvh)
            createBoundingBox();
    }

    /**
     * creates a bounding box for the geometries
     */
    @Override
    public void createBoundingBox() {
        if (shapes == null)
            return;
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;
        for (Intersectable geo : shapes) {
            if (geo.box != null) {
                minX = Math.min(minX, geo.box.min.getX());
                minY = Math.min(minY, geo.box.min.getY());
                minZ = Math.min(minZ, geo.box.min.getZ());
                maxX = Math.max(maxX, geo.box.max.getX());
                maxY = Math.max(maxY, geo.box.max.getY());
                maxZ = Math.max(maxZ, geo.box.max.getZ());
            }
        }
        box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for (Intersectable g :
                shapes) {
            List<Point> intersections= g.findIntersections(ray);
            if (intersections != null) {
                if (result == null) result = new LinkedList<>();
                result.addAll(intersections);
            }
        }
        return result;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable g :
                shapes) {
            List<GeoPoint> intersections= g.findGeoIntersectionsHelper(ray, maxDistance);
            if (intersections != null) {
                if (result == null) result = new LinkedList<>();
                result.addAll(intersections);
            }
        }
        return result;
    }
}
