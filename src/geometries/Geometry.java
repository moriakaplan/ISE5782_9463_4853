package geometries;

import primitives.*;

/**
 * An interface for geometric objects that can give an normal vector for any point on the object.
 */
public abstract class Geometry extends Intersectable{
    protected Color emission=Color.BLACK;
    private Material material = new Material();

    /**
     * getter for field emission
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for field emission
     * @param emission the new emission color of the object
     * @return the geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * find an normal vector that is perpendicular to the plane of the geometric object.
     * @param p point on thecasing of the geometry.
     * @return normal vector.
     */
    public abstract Vector getNormal(Point p);

    /**
     *
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     *
     * @return
     */
    public Material getMaterial() {
        return material;
    }
}
