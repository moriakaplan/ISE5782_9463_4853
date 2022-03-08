package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Point p1 = new Point(1, 2, 3);

    @Test
    void testSubtract() {
        assertTrue(
                new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void testAdd() {
        // Test operations with points and vectors
        assertTrue(
                p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0)),
                "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testScale() {
    }

    @Test
    void testDotProduct() {
        // test Dot-Product
        assertTrue(isZero(v1.dotProduct(v3)),
            "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28),
            "ERROR: dotProduct() wrong value");
    }

    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(
                v1.length() * v2.length(), vr.length(),
                0.00001,
                "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(
                isZero(vr.dotProduct(v1)),
                "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(
                isZero(vr.dotProduct(v2)),
                "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    @Test
    void testLengthSquared() {
        // test length..
        assertTrue(
                isZero(v1.lengthSquared() - 14),
                "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),
            "ERROR: length() wrong value");
    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product
        Vector u = v1.normalize();
        assertTrue(isZero(u.length() - 1),
            "ERROR: the normalized vector is not a unit vector");

        assertThrows( // test that the vectors are co-lined
                IllegalArgumentException.class,
                () -> v1.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        assertTrue(
                v1.dotProduct(u) >= 0,
                "ERROR: the normalized vector is opposite to the original one");
    }
}