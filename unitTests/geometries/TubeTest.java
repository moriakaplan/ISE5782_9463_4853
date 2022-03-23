package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test function getNormal of Tube- case 1.
        Ray r = new Ray(new Point(1, 2,3), new Vector(0, 5, 3));
        Tube t = new Tube(r, 5);
        assertEquals(
                t.getNormal(new Point(6, 7, 6)),
                new Vector(1, 0, 0),
                "ERROR: function getNormal() of Tube does not work correctly");
        // TC02: Test function getNormal of Tube- case 2.
        r = new Ray(new Point(0, 2,0), new Vector(0, 0, 5));
        t = new Tube(r, 5);
        assertEquals(
                t.getNormal(new Point(5, 2, 8)),
                new Vector(1, 0, 0),
                "ERROR: function getNormal() of Tube does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: test the case of point near the head of the ray.
        assertEquals(
                t.getNormal(new Point(5, 2, 0)),
                new Vector(1, 0, 0),
                "ERROR: function getNormal() of Tube does not work correctly for point near the head of the ray");
    }
}