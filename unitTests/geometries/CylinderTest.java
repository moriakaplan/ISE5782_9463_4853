package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test function getNormal of Cylinder for point on the tube.
        Ray r = new Ray(new Point(0, 2,0), new Vector(0, 0, 5));
        Cylinder c = new Cylinder(r, 5, 10);
        assertEquals(
                c.getNormal(new Point(5, 2, 8)),
                new Vector(1, 0, 0),
                "ERROR: function getNormal() of Cylinder does not work correctly for point on the tube");
        // TC02: Test function getNormal of Cylinder for point on the first base.
        assertEquals(
                c.getNormal(new Point(-1, 1, 0)),
                new Vector(0, 0, -1),
                "ERROR: function getNormal() of Cylinder does not work correctly for point on the first base");
        // TC03: Test function getNormal of Cylinder for point on the other base.
        assertEquals(
                c.getNormal(new Point(-1, 1, 10)),
                new Vector(0, 0, 1),
                "ERROR: function getNormal() of Cylinder does not work correctly for point on the other base");

        // =============== Boundary Values Tests ==================
        // TC11: test the case of point in the center of the first base.
        assertEquals(
                c.getNormal(new Point(0, 2, 0)),
                new Vector(0, 0, -1),
                "ERROR: function getNormal() of Cylinder does not work correctly for point in the center of the first base");
        // TC12: test the case of point in the center of the other base.
        assertEquals(
                c.getNormal(new Point(0, 2, 10)),
                new Vector(0, 0, 1),
                "ERROR: function getNormal() of Cylinder does not work correctly for point in the center of the other base");
    }
}