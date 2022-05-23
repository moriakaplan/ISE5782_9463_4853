package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test function getNormal of Sphere.
        Sphere s = new Sphere(new Point(1, 2, 3), 5);
        assertEquals(
                s.getNormal(new Point(1, 7, 3)),
                new Vector(0, 1, 0),
                "ERROR: function getNormal() of Sphere does not work correctly");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "ERROR: #TC01- Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "ERROR: #TC02- Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "ERROR: #TC02- Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.9, -0.5, 0.1), new Vector(0.1, -1.5, 1.9)));
        assertEquals(1, result.size(), "ERROR: #TC03- Wrong number of points (need to be 1");
        assertEquals(
                List.of(new Point(0.9228170552569, -0.842255828854, 0.5335240498818)),
                result,
                "ERROR: #TC03- crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(1, -2, 2), new Vector(0.1, -1.5, 1.9))),
                "ERROR: #TC04- Ray's line out of sphere");


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0.1, 0)));
        assertEquals(1, result.size(), "#TC03- Wrong number of points (need to be 1");
        assertEquals(
                List.of(new Point(0.019801980198, 0.1980198019802, 0)),
                result,
                "ERROR: #TC11- crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, -0.1, 0))),
                "ERROR: #TC12- Ray's line out of sphere");


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point p3 = new Point(1, -1, 0);
        Point p4 = new Point(1, 1, 0);
        result = sphere.findIntersections(new Ray(new Point(1, -2, 0),
                new Vector(0, 1, 0)));
        assertEquals(2, result.size(), "ERROR: #TC13- Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p4, p3), result, "ERROR: #TC13- Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, -1, 0), new Vector(0, 1, 0)));
        assertEquals(1, result.size(), "ERROR: #TC14- Wrong number of points (need to be 1");
        assertEquals(
                List.of(new Point(1, 1, 0)),
                result,
                "#TC14- crosses sphere");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "ERROR: #TC15- Wrong number of points (need to be 1");
        assertEquals(
                List.of(new Point(2, 0, 0)),
                result,
                "ERROR: #TC15- crosses sphere");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "ERROR: #TC14- Wrong number of points (need to be 1");
        assertEquals(
                List.of(new Point(2, 0, 0)),
                result,
                "ERROR: #TC11- crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "ERROR: #TC17- Ray's line out of sphere");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0))),
                "ERROR: #TC17- Ray's line out of sphere");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -1, 0), new Vector(0, 1, 0))),
                "ERROR: #TC19- Ray's line is tangent to the sphere");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 1, 0))),
                "ERROR: #TC20- Ray's line is tangent to the sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), new Vector(0, 1, 0))),
                "ERROR: #TC21- Ray's line is tangent to the sphere");


        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 1, 0))),
                "ERROR: #TC22- Ray's line out of sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersections(Ray, double)}.
     */
    @Test
    public void testFindGeoIntersectionsMaxDistance() {
        // TC01: MaxDistance is between the 2 points- 1 point.
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findGeoIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)), 2);
        assertEquals(1, result.size(), "ERROR: #TC02- Wrong number of points");
        assertEquals(p1, result.get(0).point, "ERROR: #TC02- Ray crosses sphere");

    }
}