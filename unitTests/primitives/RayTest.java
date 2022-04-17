package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(1,1,1));
        Point p1=new Point(1, 1, 1), //closest
                p2=new Point(0, 1, 0),
                p3=new Point(4, 5, 6);
        // ============ Equivalence Partitions Tests ==============
        //TCO1- point in the middle of the list is the closest.
        assertEquals(p1,
                ray.findClosestPoint(List.of(p2, p1, p3)),
                "ERROR: the closest point should be p1 (1,1,1)");

        // =============== Boundary Values Tests ==================
        //TC11- the list is empty.
        assertNull(ray.findClosestPoint(List.of()),
                "ERROR: closest point for empty list should be null");

        //TC12- the first point in the list is the closest.
        assertEquals(p1,
                ray.findClosestPoint(List.of(p1, p2, p3)),
                "ERROR: the closest point should be p1 (1,1,1)");

        //TC13- the last point in the list is the closest.
        assertEquals(p1,
                ray.findClosestPoint(List.of(p2, p3, p1)),
                "ERROR: the closest point should be p1 (1,1,1)");
    }
}