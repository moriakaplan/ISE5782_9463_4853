package renderer;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Intersectable;
import geometries.*;
import org.junit.jupiter.api.Test;

import renderer.Camera;
import primitives.*;

public class CameraIntegrationTest {
    Camera camera = new Camera(
            new Point(0, 0, 0),
            new Vector(0, 1, 0),
            new Vector(0, 0, 1))
            .setVPDistance(1);

    public void testCameraPlaneIntersections(){
        //TC01- intersections with plane that parallel to the view plane (9 points).
        assertEquals(
                9,
                countIntersections(camera.setVPSize(3, 3),new Plane(new Point(1, 2, -3), new Vector(0, -2, 0))),
                "ERROR: ");

    }

    public void testCameraTriangleIntersections(){

    }

    public void testCameraSphereIntersections(){

    }

    private int countIntersections(Camera cam, Intersectable geo){
        return 0;
    }
}