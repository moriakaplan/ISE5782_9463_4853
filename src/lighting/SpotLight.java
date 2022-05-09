package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Double.max;
import static java.lang.Math.pow;

/**
 * a light source of spot light- light with position and direction.
 */
public class SpotLight extends PointLight{
    private Vector direction;
    private double nB; // parameter for narrowing the beam- 0 as default for regular.

    /**
     * setter for field nB
     * @param nB parameter for narrowing the beam- 0 as default for regular.
     * @return the light object.
     */
    public SpotLight setNarrowBeam(double nB) {
        this.nB = nB;
        return this;
    }

    /**
     * constructor with parameters.
     * @param intensity the intensity of the light.
     * @param position the position of the light source.
     * @param direction the direction of the light source.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
        this.nB=0;
    }

    @Override
    public Color getIntensity(Point p) {
        double cos = direction.dotProduct(getL(p));
        if(cos<=0)
        {
             return Color.BLACK;
        }
        return super.getIntensity(p).scale(cos).scale(pow(cos, nB));
    }
}
