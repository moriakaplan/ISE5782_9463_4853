package primitives;

import java.util.Objects;

/**
 * this class represents a ray in three-dimensional space by point and vector from the point.
 */
public class Ray {
    private final Point p0;
    private final Vector dir;

    /**
     * Constructor- initialize the data according a point and normalize vector.
     * @param p0 point- the beginning of the ray.
     * @param dir vector- the direction of the ray (not necessarily normalize).
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * getter for field p0.
     * @return the point that is the beginning of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * getter for field dir
     * @return the direction vector (normalize) of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * find point on the ray according its distance from the beginning of the ray.
     * @param t distance from the beginning of the ray.
     * @return point on the ray.
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
