package lighting;

import primitives.Color;

/**
 * base class lights with intensity field.
 */
abstract class Light {
    private Color intensity;

    /**
     * constructor
     * @param intensity the intensity of this light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
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
