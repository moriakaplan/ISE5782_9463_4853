package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract base class for ray tracers- classes that can trace rays and find their appropriate colors in the scene.
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor- Initializes the reference scene.
     * @param scene the scene.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * find in the scene the appropriate color to specific ray.
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
