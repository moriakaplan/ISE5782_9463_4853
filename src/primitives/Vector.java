package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
        {
            throw new IllegalArgumentException("zero vector is not allowed");
        }
    }

    @Override
    public String toString() {
        return "Vector{}";
    }

    public Vector(Double3 d)
    {
        this(d.d1, d.d2, d.d3);
    }
    public Vector subtract(Vector other)
    {
        return super.subtract((Point)other); /*#*/
    }
    public Vector add(Vector vec)
    {
        return (Vector)super.add(vec); /*#*/
    }
    public Vector scale(double num)
    {
        return new Vector(xyz.scale(num));
    }
    public double dotProduct(Vector vec)
    {
        Double3 d = xyz.product(vec.xyz);
        return d.d1 + d.d2 + d.d3;
    }
    public Vector crossProduct(Vector vec)
    {
        return new Vector(1, 1, 1); /*#*/
    }
    public double lengthSquared()
    {
        return this.dotProduct(this);
    }
    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }
    public Vector normalize()
    {
        return this.scale(1 / this.length());
    }
}
