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
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * constructor- Initializes the reference scene.
     *
     * @param scene the scene.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * find the closest intersection between the geometries of the scene and a ray.
     * @param ray ray in the space that might intersect objects.
     * @return the closest intersection.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * find the closest intersection until maximum distance.
     * @param ray ray in the space.
     * @param maxDistance maximum distance between the beginning of the ray and the point.
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray, double maxDistance) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray, maxDistance));
    }

    /**
     * check if the point is not shaded.
     *
     * @param gP point on geometry.
     * @param l  the vector from the light source to the point.
     * @param n  the normal vector from the point.
     * @param nv the dot product of n and v (v- the vector from the camera to the point).
     * @return true if its unshaded and false if its shaded.
     */
    private Double3 transparency(GeoPoint gP, LightSource light, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector directedNormal = nv < 0 ? n : n.scale(-1);
        Ray lightRay = new Ray(gP.point, lightDirection, directedNormal);
        //way 1 - not bonus:
        //List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        //if( intersections==null) return true;
        //double distanceToLight = light.getDistance(point);
        //for (GeoPoint p:
        //     intersections) {
        //    if(point.distance(p.point)<distanceToLight)
        //        return false;
        //}
        //return true;

        //way 2 - bonus:
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gP.point));

        Double3 ktr = new Double3(1);
        if (intersections == null) return ktr; //return ktr if there are no intersections in the way.
        for (GeoPoint p :
                intersections) {
            ktr = ktr.product(p.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * calculate the reflected ray according to the formula
     * r = v-2*(vn)*n
     *
     * @param point the point in the space
     * @param n     the normal vector
     * @param v     the vector from the camera to the point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point point, Vector n, Vector v) {
        double nv = n.dotProduct(v);
        Vector directedNormal = nv < 0 ? n : n.scale(-1);
        return new Ray(point, v.subtract(n.scale(nv * 2)), directedNormal);
    }

    /**
     * find the refracted ray from point on a geometry.
     * @param point a point in the scene.
     * @param n normal vector of the point from the geometry.
     * @param v vector to the point (to refract).
     * @return the refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector n, Vector v) {
        double nv = n.dotProduct(v);
        Vector directedNormal = nv < 0 ? n.scale(-1) : n;
        return new Ray(point, v, directedNormal);
    }

    /**
     * find the color of specific point in the scene.
     *
     * @param gp  point in space.
     * @param ray a ray from the camera of the scene to the point.
     * @return the color of this point in the scene.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * find the color of specific point in the scene.
     *
     * @param gp point in space.
     * @param ray ray to the point
     * @param level level of the recursion (for stop the recursion).
     * @param k
     * @return the color of this point in the scene.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color
                : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calculate the global effects of the point- reflections and refractions.
     * @param gp GeoPoint point
     * @param ray ray to the point.
     * @param level level of the recursion (for stop the recursion).
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Ray reflectedRay = constructReflectedRay(gp.point, gp.geometry.getNormal(gp.point), ray.getDir());
        GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
        Ray refractedRay = constructRefractedRay(gp.point, gp.geometry.getNormal(gp.point), ray.getDir());
        GeoPoint refractedPoint = findClosestIntersection(refractedRay);
        Double3 kr = gp.geometry.getMaterial().kR, kkr = k.product(kr);
        Double3 kt = gp.geometry.getMaterial().kT, kkt = k.product(kt);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K) && reflectedPoint != null) {
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        if (!kkt.lowerThan(MIN_CALC_COLOR_K) && refractedPoint != null) {
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    /**
     * help function. calculate the local effects of the point (the basic color of it)
     *
     * @param gP  point on geometry.
     * @param ray ray from the camera to the point.
     * @return the local color.
     */
    private Color calcLocalEffects(GeoPoint gP, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(gP, light, l, n, nv);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = light.getIntensity(gP.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl))
                            , (iL.scale(calcSpecular(material, n, l, nl, v))));
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
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) return scene.background;
        return calcColor(closestPoint, ray);
    }
}
