package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Ray r = new Ray(new Point(1, 2, 3), new Vector(1, 1, 1));
        Point po = new Point(7, 8, 8);
        Sphere sYes = new Sphere(po, 1);
        Plane pl = new Plane(po, new Vector(1, 0, 0));
        Triangle tNo = new Triangle(new Point(7,7,7), new Point(11, 11, 10), new Point(9, 9, 9));

        // ============ Equivalence Partitions Tests ==============

        //TC01: some shapes have intersections and some doesn't.
        assertEquals(
                3,
                new Geometries(sYes, pl, tNo).findIntersections(r).size(),
                "ERROR: the ray intersect 2 of the geometries in 3 points");

        // =============== Boundary Values Tests ==================

        //TC11: empty collection
        assertNull(new Geometries().findIntersections(new Ray(new Point(1, 2, 3), new Vector(1, 1, 1))),
                "ERROR: empty collection hasn't intersections");

        //TC12: zero intersections points.
        assertNull(new Geometries().findIntersections(new Ray(new Point(1, 2, 3), new Vector(1, 1, 1))),
                "ERROR: all the geometries haven't intersections");

        //TC13: just one shape has intersections.
        Sphere sNo = new Sphere(new Point(4, 4, 4), 1);
        assertEquals(
                1,
                new Geometries(sNo, pl, tNo).findIntersections(r).size(),
                "ERROR: the ray intersect 1 of the geometries in 1 point");

        //TC14: all shapes have intersections.
        Triangle tYes = new Triangle(new Point(7,7,10), new Point(7, 11, 10), new Point(11, 9, 10));
        assertEquals(
                4,
                new Geometries(sYes, pl, tYes).findIntersections(r).size(),
                "ERROR: the ray intersect all the geometries in 4 points");
    }
}