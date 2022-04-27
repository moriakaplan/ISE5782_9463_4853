package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * a class for an ambient light in place.
 */
public class AmbientLight extends Light{

    /**
     * default constructor- call the constructor of Light with the color black.
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * constructor- find the value for the intensity according to the intensity of the ambient light of the scene and the attenuation factor.
     *
     * @param iA intensity of the ambient light.
     * @param kA attenuation factor.
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }
}
