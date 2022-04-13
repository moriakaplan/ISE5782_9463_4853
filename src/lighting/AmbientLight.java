package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    /**
     * constructor- find the value for the field intensity according to the intensity of the ambient light of the scene and the attenuation factor.
     *
     * @param iA intensity of the ambient light.
     * @param kA attenuation factor.
     */
    public AmbientLight(Color iA, Double3 kA) {
        this.intensity = iA.scale(kA);
    }

    /**
     * getter for field intensity
     *
     * @return the intensity of the ambient light of the scene.
     */
    public Color getIntensity() {
        return intensity;
    }
}
