package lighting;

import primitives.Color;
import primitives.*;

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
}
