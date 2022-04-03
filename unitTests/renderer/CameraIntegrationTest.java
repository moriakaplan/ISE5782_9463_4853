package renderer;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Intersectable;
import geometries.*;
import org.junit.jupiter.api.Test;

import renderer.Camera;
import primitives.*;

import java.util.List;

/**
 * Integration tests class for {@link renderer.Camera#constructRay(int, int, int, int)}.
 */
public class CameraIntegrationTest {
    Camera camera = new Camera(
            new Point(0, 0, 0),
            new Vector(0, 1, 0),
            new Vector(0, 0, 1))
            .setVPSize(3, 3)
            .setVPDistance(1);

    /**
     * Test method with {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    public void testCameraPlaneIntersections() {
        //TC01- intersections with plane that parallel to the view plane (9 points).
        assertEquals(
                9,
                countIntersections(camera, new Plane(new Point(1, 2, -3), new Vector(0, -2, 0))),
                "ERROR: this case has 9 intersections");
        //TC02- intersections with plane that not parallel to the view plane (9 points).
        assertEquals(
                9,
                countIntersections(camera, new Plane(new Point(1, 2, -3), new Vector(1, -2, 0))),
                "ERROR: this case has 9 intersections");
        //TC03- intersections with plane with big angle with the view plane (9 points).
        assertEquals(
                6,
                countIntersections(camera, new Plane(new Point(0, 2, 0), new Vector(0, -0.5, -1))),
                "ERROR: this case has 6 intersections");

    }

    /**
     * Test method with {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void testCameraTriangleIntersections() {
        //TC01- intersections with little triangle- only the center ray intersect (1 point).
        assertEquals(
                1,
                countIntersections(
                        camera,
                        new Triangle(new Point(0, 2, 1), new Point(-1, 2, -1), new Point(1, 2, -1))),
                "ERROR: this case has 1 intersection in the center");
        //TC02- intersections with tall triangle (2 point).
        assertEquals(
                2,
                countIntersections(
                        camera,
                        new Triangle(new Point(0, 2, 20), new Point(-1, 2, -1), new Point(1, 2, -1))),
                "ERROR: this case has 2 intersections");

    }

    /**
     * Test method with {@link geometries.Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testCameraSphereIntersections() {
        //TC01- intersections with sphere with radius 1 (2 point).
        assertEquals(
                2,
                countIntersections(camera, new Sphere(new Point(0, 3, 0), 1)),
                "ERROR: this case has 2 intersections");

        camera = new Camera(
                new Point(0, 0, 0.5),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(3, 3)
                .setVPDistance(1);

        //TC02- intersections with sphere with radius 2.5 (18 point- 2 for any ray).
        assertEquals(
                18,
                countIntersections(
                        camera,
                        new Sphere(new Point(0, 0, -2.5), 2.5)),
                "ERROR: this case has 18 intersections");

        //TC03- intersections with sphere with radius 2 (10 point).
        assertEquals(
                10,
                countIntersections(
                        camera,
                        new Sphere(new Point(0, 0, -2), 2)),
                "ERROR: this case has 10 intersections");

        //TC04- intersections with sphere with radius 4 (9 points).
        assertEquals(
                9,
                countIntersections(
                        camera,
                        new Sphere(new Point(0, 0, 0), 4)),
                "ERROR: this case has 9 intersections");

        //TC04- intersections with sphere behind the camera with radius 0.4 (0 points).
        assertEquals(
                0,
                countIntersections(
                        camera,
                        new Sphere(new Point(0, 0, 1), 0.4)),
                "ERROR: this case hasn't intersections");

    }

    /**
     * count how many intersections the camera and the intersectable object have.
     *
     * @param cam the camera object.
     * @param geo the intersectable object.
     * @return the number of intersections points.
     */
    private int countIntersections(Camera cam, Intersectable geo) {
        int count = 0;
        int nX = 3, nY = 3;
        for (int j = 0; j < nX; j++) {
            for (int i = 0; i < nY; i++) {
                Ray ray = cam.constructRay(nX, nY, j, i);
                List<Point> intersections = geo.findIntersections(ray);
                if (intersections != null)
                    count += intersections.size();
            }
        }
        return count;
    }
}