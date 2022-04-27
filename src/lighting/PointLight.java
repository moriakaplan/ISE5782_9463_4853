package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class for point light- light with specific position.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double
            kC, //the const attenuation coefficient
            kL, //the linear attenuation coefficient
            kQ; //the squared attenuation coefficient

    /**
     * constructor- determine the attenuation coefficients to be 1,0,0 as default.
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        kC = 1;
        kL = 0;
        kQ = 0;
    }

    /** setter for the field kC- the const attenuation coefficient
     * @param kC the const attenuation coefficient
     * @return the light object.
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /** setter for the field kL- the linear attenuation coefficient
     * @param kL the linear attenuation coefficient
     * @return the light object.
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /** setter for the field kQ- the squared attenuation coefficient
     * @param kQ the squared attenuation coefficient
     * @return the light object.
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position);
    }
}
