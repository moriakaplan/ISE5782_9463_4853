package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class for directional light- light with specific direction.
 */
public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    /**
     * constructor- set the values in the fields.
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
