package lighting;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointLightTest {

    @Test
    void testShadowRays() {
        PointLight light=new PointLight(new Color(100,100,100), new Point(0,0,0));
        List<Ray> rays = light.shadowRays(new Point(-1, -1, -1), new Vector(1, 1, 1), 0.2, 3);
        assertEquals(rays,
                List.of());
    }
}