package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * basic ray tracer- can trace rays and find their appropriate colors in the scene.
 */
public class RayTracerBasic extends RayTracerBase{
    /**
     * constructor- Initializes the reference scene.
     * @param scene the scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * find the color of specific point in the scene.
     * @param point point in space.
     * @return the color of this point in the scene.
     */
    private Color calcColor(GeoPoint point){
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections= scene.geometries.findGeoIntersections(ray);
        if(intersections==null) return scene.background;
        GeoPoint point= ray.findClosestGeoPoint(intersections);
        return calcColor(point);
    }
}
