package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

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
}