package lighting;

import primitives.Color;
import primitives.*;

import java.util.List;

/**
 * interface for light sources.
 */
public interface LightSource {
    /**
     * find the intensity of this light on specific point.
     * @param p point in the scene.
     * @return the intensity of this light on the point.
     */
    public Color getIntensity(Point p);

    /**
     * find the vector from the light source to the point.
     * @param p point in the space.
     * @return the vector from the light source to the point.
     */
    public Vector getL(Point p);

    /**
     * find the distance between the light source and point in space.
     * @param point point in the space.
     * @return the distance between the light source and the point.
     */
    public double getDistance(Point point);

    /**
     * create a list of rays from a point to the place of the light source- super sampling with grid.
     * @param p point in the scene.
     * @return list of rays from the point to the light source.
     */
    List<Ray> shadowRays(Point p, Vector directedN, double targetSize, int nRays);
}
