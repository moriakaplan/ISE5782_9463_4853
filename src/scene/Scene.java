package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;
import renderer.Camera;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * class scene- include the name, background, ambient light and geometries of the scene
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights= new LinkedList<>();
    public double softShadowTargetSize = 0;
    public int softShadowNumRays = 1;


    /**
     * constructor- set the name of the scene and create empty list of geometries.
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        ambientLight = new AmbientLight();
        geometries = new Geometries();
        background = Color.BLACK;
    }

    /**
     * setter for field name in builder template.
     * @param name the name of the scene.
     * @return the scene object.
     */
    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * setter for field background in builder template.
     * @param background the background of the scene.
     * @return the scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * setter for field ambientLight in builder template.
     * @param ambientLight the ambient light of the scene.
     * @return the scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter for field geometries in builder template.
     * @param geometries the geometries of the scene.
     * @return the scene object.
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * setter for field lights in builder template.
     * @param lights the light sources of the scene.
     * @return the scene object.
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * start the soft shadow improvement.
     * set the softShadowNumRays field
     * @param numRays numRays^2 is the number of shadow rays
     * @param areaSize areaSize^2 is the size of the target area
     * @return the scene object
     */
    public Scene softShadowOn(int numRays, double areaSize) {
        if (numRays < 1) {
            throw new IllegalArgumentException("number of shadow rays must be positive");
        }
        if(isZero(areaSize)){
            throw new IllegalArgumentException("the size of the target area of soft shadow must be positive");
        }
        this.softShadowNumRays = numRays;
        this.softShadowTargetSize = areaSize;
        return this;
    }

    /**
     * stop the soft shadow improvement.
     * @return the scene object
     */
    public Scene softShadowOff() {
        this.softShadowNumRays = 1;
        return this;
    }

    /* builder- class scene
    private final String name;
    private final Color background;
    private final AmbientLight ambientLight;
    private final Geometries geometries;

    private Scene(SceneBuilder builder){
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public Geometries getGeometries() {
        return geometries;
    }
    */

    /* builder- sceneBuilder
    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        public SceneBuilder(String name){
            this.name = name;
        }
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }
        public Scene build(){
            Scene scene = new Scene(this);
            //... what should we do here?
            return scene;
        }
    }*/
}
