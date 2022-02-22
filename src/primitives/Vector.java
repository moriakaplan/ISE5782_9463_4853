package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (coordinates.equals(Double3.ZERO))
        {
            throw new IllegalArgumentException("zero vector is not allowed");
        }
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
        return new Vector(coordinates.scale(num));
    }
    public double dotProduct(Vector vec)
    {
        Double3 d = coordinates.product(vec.coordinates);
        return d.d1 + d.d2 + d.d3;
    }
    public double lengthSquered()
    {
        return this.dotProduct(this);
    }
    public double length()
    {
        return Math.sqrt(this.lengthSquered());
    }
    public Vector normalize()
    {
        return this.scale(1 / this.length());
    }
}
