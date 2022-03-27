package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /** test method for {@link geometries.Plane#findIntersections(Ray)} */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        Plane pl = new Plane(new Point(1,0,0),new Vector(0,0,1));
        List<Point> result = pl.findIntersections(new Ray(new Point(1,1,1), new Vector(-1,-2,-3)));
        assertEquals(result.get(0),
                new Point(0.67,0.33,0),
                "ERROR: function indIntersections() of Plane does not work correctly");

        // TC02: Ray does not intersect the plane
        result = pl.findIntersections(new Ray(new Point(1,1,1), new Vector(1,2,3)));
        assertNull(result.get(0),
                "ERROR: function indIntersections() of Plane does not work correctly");


        // =============== Boundary Values Tests ==================

        //**** Group: Ray is parallel to the plane
        // TC11: Ray is parallel to the plane and included in the plane
        result = pl.findIntersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertNull(result.get(0),
                "ERROR: TC11- Ray is parallel to the plane and included in the plane");

        // TC12: Ray is parallel to the plane and not included in the plane
        result = pl.findIntersections(new Ray(new Point(0,0,1), new Vector(1,0,0)));
        assertNull(result.get(0),
                "ERROR: TC12- Ray is parallel to the plane and not included in the plane");


        //**** Group: Ray is orthogonal to the plane
        //TC13: Ray is orthogonal to the plane and p0 before the plane
        result = pl.findIntersections(new Ray(new Point(-1,-1,-1), new Vector(0,0,1)));
        assertEquals(result.get(0),
                new Point(-1,-1,0),
                "ERROR: TC13- Ray is orthogonal to the plane and p0 before the plane");

        //TC14: Ray is orthogonal to the plane and p0 in the plane
        result = pl.findIntersections(new Ray(new Point(-1,-1,0), new Vector(0,0,1)));
        assertNull(result.get(0),
                "ERROR: TC14- Ray is orthogonal to the plane and p0 in the plane");

        //TC15: Ray is orthogonal to the plane and p0 after the plane
        result = pl.findIntersections(new Ray(new Point(-1,-1,1), new Vector(0,0,1)));
        assertNull(result.get(0),
                "ERROR: TC15- ay is orthogonal to the plane and p0 after the plane");


        //**** Group: Ray begins at the plane
        //TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        result = pl.findIntersections(new Ray(new Point(0,1,0), new Vector(1,2,1)));
        assertNull(result.get(0),
                "ERROR: TC16- Ray is neither orthogonal nor parallel to and begins at the plane");

        //TC17: Ray is neither orthogonal nor parallel to the plane and begins at q0
        result = pl.findIntersections(new Ray(new Point(1,0,0), new Vector(1,2,1)));
        assertNull(result.get(0),
                "ERROR: TC17- Ray is neither orthogonal nor parallel to and begins at q0");
    }
}
