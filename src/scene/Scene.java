package scene;

import geometries.Geometries;
import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * class scene- include the name, background, ambient light and geometries of the scene
 */
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;
    public List<LightSource> lights= new LinkedList<>();

    /**
     * constructor- set the name of the scene and create empty list of geometries.
     * @param name the name of the scene
     */
    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
        background=Color.BLACK;
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