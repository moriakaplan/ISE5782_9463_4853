package geometries;

import primitives.Point;

/**
 * class for triangle, a type of polygon with 3 vertices.
 */
public class Triangle extends Polygon {

    /**
     * constructor for initialize by 3 points
     * @param p1 first point- one fo the vertices of the triangle.
     * @param p2 second point- one fo the vertices of the triangle.
     * @param p3 Third point- one fo the vertices of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
