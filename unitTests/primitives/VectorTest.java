package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);
    Point p1 = new Point(1, 2, 3);

    @Test
    void testSubtract() {
        // TC01: Test operations with points and vectors- subtract.
        assertEquals(
                new Vector(1, 1, 1), new Point(2, 3, 4).subtract(p1),
                "ERROR: Point - Point does not work correctly");
    }

    @Test
    void testAdd() {
        // TC01: Test operations with points and vectors- add.
        assertEquals(
                p1.add(new Vector(-1, -2, -3)), new Point(0, 0, 0),
                "ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testScale() {
        // TC01: test scale.
        assertEquals(
                v2, v1.scale(-2),
                "ERROR: scale() does not work correctly");
    }

    @Test
    void testDotProduct() {
        // TC01: test Dot-Product for orthogonal vectors.
        assertTrue(isZero(v1.dotProduct(v3)),
            "ERROR: dotProduct() for orthogonal vectors is not zero");
        // TC02: test Dot-Product.
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
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");

    }

    @Test
    void testLengthSquared() {
        // TC01: test lengthSquared.
        assertTrue(
                isZero(v1.lengthSquared() - 14),
                "ERROR: lengthSquared() wrong value");
    }

    @Test
    void testLength() {
        // TC01: test length.
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),
            "ERROR: length() wrong value");
    }

    @Test
    void testNormalize() {
        Vector u = v1.normalize();

        // TC01: test vector normalization vs vector length.
        assertTrue(isZero(u.length() - 1),
            "ERROR: the normalized vector is not a unit vector");

        // TC02: test that the vectors are co-lined.
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC03: test vector normalization vs dotProduct.
        assertTrue(
                v1.dotProduct(u) >= 0,
                "ERROR: the normalized vector is opposite to the original one");
    }
}