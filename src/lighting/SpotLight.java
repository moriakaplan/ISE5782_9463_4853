package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Double.max;

/**
 *
 */
public class SpotLight extends PointLight{
    Vector direction;

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0, direction.dotProduct(getL(p))));
    }
}
