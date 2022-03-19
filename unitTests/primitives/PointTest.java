package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class PointTest {
    Point p1 = new Point(1, 2, 3);

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test operations with points and vectors- subtract.
        assertEquals(
                new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1),
                "ERROR: Function add in Point does not work correctly");
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test operations with points and vectors- add.
        assertEquals(
                p1.add(new Vector(-1, -2, -3)), new Point(0, 0, 0),
                "ERROR: Point + Vector does not work correctly in function add");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test distanceSquared.
        assertTrue(
                isZero(new Point(1, 2, 3).distanceSquared(new Point(2, 4, 6)) - 14),
                "ERROR: distanceSquared() wrong value");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: test distance.
        assertTrue(isZero(new Point(7, 4, -2).distance(new Point(7, 1, 2)) - 5),
                "ERROR: distance() wrong value");
    }
}