package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test function getNormal() of Triangle.
        Triangle t = new Triangle(new Point(1, 2,2), new Point(3,4,-1), new Point(0,7,0));
        Vector normal = t.getNormal(new Point( 2, 3,1));
        assertTrue(
                normal.equals(new Vector(11, 7, 12).normalize()) || normal.equals(new Vector(-11, -7, -12).normalize()),
                "ERROR: function getNormal() of Triangle does not work correctly");
    }

    /**
     * test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Plane pl = new Plane(new Point(1,0,0),new Vector(0,0,1));
        Triangle t=new Triangle(new Point(1,0,0), new Point(0,2,0), new Point(0,0,3));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane and the intersection is inside the triangle.
        List<Point> result = t.findIntersections(new Ray(new Point(0,0,0), new Vector(1,2,2)));
        assertEquals(result.get(0),
                new Point(0.375,0.75,75),
                "ERROR: function indIntersections() of Triangle does not work correctly");
        // TC02: Ray intersects the plane and the intersection is outside the triangle against edge.
        result = t.findIntersections(new Ray(new Point(0,0,-2), new Vector(1,2,2)));
        assertNull(result,
                "ERROR: function indIntersections() of Triangle does not work correctly");
        // TC03: Ray intersects the plane and the intersection is outside the triangle against vertex.
        result = t.findIntersections(new Ray(new Point(0,0,-2), new Vector(0,3,2)));
        assertNull(result,
                "ERROR: function indIntersections() of Triangle does not work correctly");

        // =============== Boundary Values Tests ==================

        //**** Group: Ray intersects the plane

        // TC11: Ray intersects the plane and the intersection is on edge.
        result = t.findIntersections(new Ray(new Point(0,0,0), new Vector(1,2,0)));
        assertNull(result,
                "ERROR: function indIntersections() of Triangle does not work correctly");
        // TC12: Ray intersects the plane and the intersection is in vertex.
        result = t.findIntersections(new Ray(new Point(0,0,-2), new Vector(0,2,2)));
        assertNull(result,
                "ERROR: function indIntersections() of Triangle does not work correctly");
        // TC13: Ray intersects the plane and the intersection is on edge's continuation.
        result = t.findIntersections(new Ray(new Point(0,-1,0), new Vector(2,-1,0)));
        assertNull(result,
                "ERROR: function indIntersections() of Triangle does not work correctly");


    }
}