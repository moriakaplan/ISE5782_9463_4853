package primitives;

/**
 * this class represents a vector in three-dimensional space by point- the vector is from the ZERO point (0,0,0) to this point.
 */
public class Vector extends Point {
    /**
     * Constructor- initialize the data with three double numbers.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("zero vector is not allowed");
        }
    }

    /**
     * Constructor- initialize the data with Double3 object.
     * @param d coordinates
     */
    Vector(Double3 d) {
        this(d.d1, d.d2, d.d3);
    }

    @Override
    public String toString() {
        return "Vector{}";
    }

    /**
     * subtract another vector from this vector.
     * @param other right handle side operand for subtracting
     * @return vector- the result of the subtracting.
     */
    public Vector subtract(Vector other) {
        return super.subtract(other);
    }

    /**
     * add another vector from this vector.
     * @param vec right handle side operand for addition
     * @return vector- the result of the addition.
     */
    public Vector add(Vector vec) {
        return (Vector) super.add(vec);
    }

    /**
     * product with scalar number.
     * Scale (multiply) floating point triad by a number into a new triad where each
     * number is multiplied by the number.
     * call the method of class Double3.
     *
     * @param num right handle side operand for scaling
     * @return result of scale
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * Do dot product between 2 vectors.
     * @param vec the another vector for product.
     * @return the result- sum of products of the coordinates.
     */
    public double dotProduct(Vector vec) {
        Double3 d = xyz.product(vec.xyz);
        return d.d1 + d.d2 + d.d3;
    }

    /**
     * Do cross product between 2 vectors- find a vector that is vertical to the two vectors.
     * @param vec the another vector for product.
     * @return the result- vector that is vertical to the two vectors.
     */
    public Vector crossProduct(Vector vec) {
        Double3 a = this.xyz;
        Double3 b = vec.xyz;
        return new Vector(a.d2 * b.d3 - a.d3 * b.d2, a.d3 * b.d1 - a.d1 * b.d3, a.d1 * b.d2 - a.d2 * b.d1);
    }

    /**
     * find the length squared of the vector- dot product of the vector with itself.
     * @return the length squared of the vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * find the length of the vector- the square root of the dot product of the vector with itself.
     * @return the length of the vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * divide the vector with its length for create normalize vector- vector with the same direction ane length 1.
     * @return the normal vector.
     */
    public Vector normalize() {
        return this.scale(1 / this.length());
    }
}
