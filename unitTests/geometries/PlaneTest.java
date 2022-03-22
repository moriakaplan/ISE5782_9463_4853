package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     */
    @Test
    void testConstructor() {
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test constructor and function get normal of Plane.
        Point p1 = new Point(1, 2,2);
        Plane pl = new Plane(p1, new Point(3,4,-1), new Point(0,7,0));
        Vector normal = pl.getNormal(p1);
        assertTrue(
                normal.equals(new Vector(11, 7, 12).normalize()) || normal.equals(new Vector(-11, -7, -12).normalize()),
                "ERROR: function getNormal() of Plane does not work correctly");
    }
}