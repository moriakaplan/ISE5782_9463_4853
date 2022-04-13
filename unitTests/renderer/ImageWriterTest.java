package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * test method for
     * {@linq renderer.ImageWriter#writePixel(int, int, Color)}.
     */
    @Test
    void testWritePixel() {
        int nX = 800, nY = 500, gap = 50;
        Color green = new Color(0, 150, 120);
        Color yellow = new Color(255, 255, 0);
        ImageWriter writer = new ImageWriter("grid", nX, nY);
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % gap == 0 || j % gap == 0)
                    writer.writePixel(j, i, yellow);
                else
                    writer.writePixel(j, i, green);
            }
        }
        writer.writeToImage();
    }
}