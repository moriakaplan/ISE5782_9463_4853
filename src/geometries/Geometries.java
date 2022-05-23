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
        //shapes.addAll(geometries);
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
