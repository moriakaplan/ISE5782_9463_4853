package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * data of the camera and the view plane of the scene.
 */
public class Camera {
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * constructor for the camera's data.
     *
     * @param forLocation location of the camera.
     * @param forVTo      direction vector forward.
     * @param forVUp      vector for direction up.
     */
    public Camera(Point forLocation, Vector forVTo, Vector forVUp) {
        this.location = forLocation;
        if (!isZero(forVTo.dotProduct(forVUp)))
            throw new IllegalArgumentException("direction vectors of camera must be orthogonal");
        this.vTo = forVTo.normalize();
        this.vUp = forVUp.normalize();
        this.vRight = forVTo.crossProduct(forVUp).normalize(); //need to be the right and not the left.
    }

    /**
     * find the ray that go through the specific pixel in the view plane.
     *
     * @param nX the amount of columns of pixels.
     * @param nY the amount of rows of pixels.
     * @param j  the specific column of the wanted pixel.
     * @param i  the specific row of the wanted pixel.
     * @return the ray that go through the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //view plane center
        Point Pc = location.add(vTo.scale(distance));

        //ratio- pixel height and width
        double Ry = (double) height / nY;
        double Rx = (double) width / nX;

        //pixel[i,j] center
        Point Pij = Pc;
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));
        Vector Vij = Pij.subtract(location);
        return new Ray(location, Vij);
    }

    /**
     *
     */
    public void renderImage() {
        if (location == null || vTo == null || vUp == null || vRight == null ||
                height == 0 || width == 0 || distance == 0 ||
                imageWriter == null || rayTracer == null) {
            throw new MissingResourceException();
        }
        throw new UnsupportedOperationException("")
    }

    /**
     * create a grid on the image according to the interval.
     *
     * @param interval the space between the lines.
     * @param color    the color of the lines.
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) throw new MissingResourceException();
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
        writeToImage();
    }

    /**
     * call the method writeToImage() of the imageWriter.
     */
    public void writeToImage() {
        if (imageWriter == null) throw new MissingResourceException();
        imageWriter.writeToImage();
    }

    /**
     * setter for the field of the view plane's size.
     *
     * @param forWidth  width of the view plane.
     * @param forHeight height  of the view plane.
     * @return the object of the camera.
     */
    public Camera setVPSize(double forWidth, double forHeight) {
        if (forHeight <= 0 || forWidth <= 0)
            throw new IllegalArgumentException("height and width must be positive numbers");
        this.width = forWidth;
        this.height = forHeight;
        return this;
    }

    /**
     * setter for the field of the distance from the camera to the view plane.
     *
     * @param forDistance distance from the camera to the view plane.
     * @return the object of the camera.
     */
    public Camera setVPDistance(double forDistance) {
        if (forDistance <= 0)
            throw new IllegalArgumentException("distance must be positive numbers");
        this.distance = forDistance;
        return this;
    }

    /**
     * setter for field imageWriter.
     *
     * @param imageWriter the new imageWriter.
     * @return the object of the camera.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * setter for field rayTracer.
     *
     * @param rayTracer the new rayTracer.
     * @return the object of the camera.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * getter for field location.
     *
     * @return the location of the camera.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * getter for field vTo.
     *
     * @return the vector of the direction 'to'.
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * getter for field vUp.
     *
     * @return the vector of the direction 'up'.
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * getter for field vRight.
     *
     * @return the vector of the direction 'right'.
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * getter for field Height.
     *
     * @return the height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * getter for field width.
     *
     * @return the width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * getter for field distance.
     *
     * @return the distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }
}
