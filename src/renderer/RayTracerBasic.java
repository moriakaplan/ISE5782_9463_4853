package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * basic ray tracer- can trace rays and find their appropriate colors in the scene.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * constructor- Initializes the reference scene.
     *
     * @param scene the scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * check if the point is not shaded.
     *
     * @param gP point on geometry.
     * @param l the vector from the light source to the point.
     * @param n the normal vector from the point.
     * @param nv the dot product of n and v (v- the vector from the camera to the point).
     * @return true if its unshaded and false if its shaded.
     */
    private boolean unshaded(GeoPoint gP, LightSource light, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = gP.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if( intersections==null) return true;
        double distanceToLight = light.getDistance(point);
        for (GeoPoint p:
             intersections) {
            if(point.distance(p.point)<distanceToLight)
                return false;
        }
        return true;
    }

    /**
     * find the color of specific point in the scene.
     *
     * @param intersection point in space.
     * @return the color of this point in the scene.
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * help function. calculate the local effects of the point (the basic color of it)
     *
     * @param gP  point on geometry.
     * @param ray ray from the camera to the point.
     * @return the local color.
     */
    private Color calcLocalEffects(GeoPoint gP, Ray ray) {
        Color color = gP.geometry.getEmission();
        Vector n = gP.geometry.getNormal(gP.point);
        Vector v = ray.getDir();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gP.geometry.getMaterial();
        for (LightSource light :
                scene.lights) {
            Vector l = light.getL(gP.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if(unshaded(gP, light, l, n, nv)) {
                    Color iL = light.getIntensity(gP.point);
                    color = color.add(iL.scale(calcDiffusive(material, nl))
                                    ,(iL.scale(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * calculate the diffuse factor- kD*|n*l|
     *
     * @param material the material of the geometry of the geoPoint.
     * @param nl       product of the normal vector and the vector l.
     * @return the diffuse factor.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * calculate the specular factor- kS*(max(0,-v*r)^nSh)
     *
     * @param material the material of the geometry of the geoPoint.
     * @param n        the normal vector for the geoPoint on the geometry
     * @param l        the vector from the light source place to the point.
     * @param nl       product of the normal vector and the vector l.
     * @param v        the vector from the camera to the point.
     * @return the diffuse factor.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(nl).scale(2)); // r = l-2*(n*l)*n
        return material.kS.scale(pow(max(0, r.dotProduct(v.scale(-1))), material.shininess));
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return scene.background;
        GeoPoint point = ray.findClosestGeoPoint(intersections);
        return calcColor(point, ray);
    }
}
