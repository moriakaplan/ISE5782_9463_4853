package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

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
    public List<Ray> shadowRays(Point p, Vector directedN, double targetSize, int nRays) {
        List<Ray> result = new LinkedList<Ray>();
        Vector l=getL(p);
        //help vector for calculating the vectors in the target plane (just must be different from l and (0,0,0)
        Vector vec = l.add(new Vector(0, 0, l.getZ()));
        Vector v1 = l.crossProduct(vec).normalize();  //vector in the target plane (orthogonal to l).
        Vector v2 = l.crossProduct(v1).normalize();   //vector in the target plane (orthogonal to l and v1).
        Point corner = position.subtract(v1.scale(targetSize / 2))  //the corner of the target area.
                               .subtract(v2.scale(targetSize / 2));
        Point Pij;
        for (int i = 0; i < nRays; i++) {
            for (int j = 0; j < nRays; j++) {
                //calculate one point in the grid on the target area.
                Pij = corner;
                if (i != 0) Pij = Pij.add(v1.scale((targetSize / nRays) * i));
                if (j != 0) Pij = Pij.add(v2.scale((targetSize / nRays) * j));
                result.add(new Ray(p, Pij.subtract(p), directedN));
            }
        }
        return result;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
