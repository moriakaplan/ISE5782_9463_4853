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
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test constructor of Plane.
        Point p1 = new Point(1, 2,2), p2 = new Point(3,4,-1), p3 = new Point(0,7,0);
        Plane pl = new Plane(p1, p2, p3);
        Point p = pl.getQ0();
        Vector v = pl.getNormal();
        assertTrue(
                p.equals(p1) || p.equals(p2) || p.equals(p3),
                "ERROR: the constructor of Plane does not work correctly (point)");
        assertTrue(
                v.equals(new Vector(11, 7, 12).normalize()) || v.equals(new Vector(-11, -7, -12).normalize()),
                "ERROR: the constructor of Plane does not work correctly (vector)");

        // =============== Boundary Values Tests ==================
        // TC11: Test the case of two same points.
        assertThrows(
                IllegalArgumentException.class,
                ()-> new Plane(p1, p1, p3),
                "ERROR: the constructor got two same points and didn't throw an exception");
        // TC12: Test the case of points on one vector.
        Point p4 = new Point(1, 2,3), p5 = new Point(2,4,6), p6 = new Point(3,6,9);
        assertThrows(
                IllegalArgumentException.class,
                ()-> new Plane(p4, p5, p6),
                "ERROR: the constructor got points on one vector and didn't throw an exception");

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