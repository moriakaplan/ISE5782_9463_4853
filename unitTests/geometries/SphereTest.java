package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test function getNormal of Sphere.
        Sphere s=new Sphere(new Point(1, 2,3), 5);
        assertEquals(
                s.getNormal(new Point(1, 7, 3)),
                new Vector(0, 1, 0),
                "ERROR: function getNormal() of Sphere does not work correctly");
    }
}