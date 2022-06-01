package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

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
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Ray> shadowRays(Point p, Vector directedN, double targetSize, int nRays) {
        return List.of(new Ray(p, getL(p).scale(-1), directedN));
    }
}
