package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static java.lang.Math.*;
import static primitives.Util.alignZero;
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
    //private boolean superSampling = false;
    private int antiAliasingNumRays = 1;
    private boolean adaptiveSuperSampling= false;


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
        this.vRight = forVTo.crossProduct(forVUp).normalize();
    }

    /**
     * start the anti aliasing improvement.
     * set the multiRaysNum field
     *
     * @param multiRaysNum multiRaysNum^2 is the number of rays through a pixel
     * @return the camera object
     */
    public Camera antiAliasingOn(int multiRaysNum, boolean adaptive) {
        if (multiRaysNum < 1) {
            throw new IllegalArgumentException("number of rays through pixels must be positive");
        }
        this.antiAliasingNumRays = multiRaysNum;
        this.adaptiveSuperSampling=adaptive;
        return this;
    }

    /**
     * stop the anti aliasing improvement.
     *
     * @return
     */
    public Camera AntiAliasingOff() {
        this.antiAliasingNumRays = 1;
        return this;
    }

    /**
     * find the rays that go through the specific pixel in the view plane.
     *
     * @param nX the amount of columns of pixels.
     * @param nY the amount of rows of pixels.
     * @param j  the specific column of the wanted pixel.
     * @param i  the specific row of the wanted pixel.
     * @return the ray that go through the pixel.
     */
    public List<Ray> constructRaySuperSampling(int nX, int nY, int j, int i) {
        List<Ray> result = new LinkedList<Ray>();
        //view plane center
        Point Pc = location.add(vTo.scale(distance));
        //ratio- pixel height and width
        double pixelHeight = (double) height / nY;
        double pixelWidth = (double) width / nX;
        double cellHeight = (double) pixelHeight / antiAliasingNumRays;
        double cellWidth = (double) pixelWidth / antiAliasingNumRays;

        //pixel[i,j] center
        Point Pij = Pc;
        Point point;
        double Yi = -(0.5 + i - (nY - 1) / 2d) * pixelHeight + 0.5 * cellHeight; //how to move from the center of the view plane.
        double Xj = (-0.5 + j - (nX - 1) / 2d) * pixelWidth + 0.5 * cellWidth; /***/
        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));
        for (int k = 0; k < antiAliasingNumRays; k++) {
            for (int l = 0; l < antiAliasingNumRays; l++) {
                point = Pij;
                if (k != 0) point = point.add(vRight.scale(cellWidth * k));
                if (l != 0) point = point.add(vUp.scale(cellHeight * l));
                result.add(new Ray(location, point.subtract(location)));
            }
        }
        return result;
    }

    /**
     * find the rays that go through the specific pixel in the view plane.
     *
     * @param nX the amount of columns of pixels.
     * @param nY the amount of rows of pixels.
     * @param j  the specific column of the wanted pixel.
     * @param i  the specific row of the wanted pixel.
     * @return the ray that go through the pixel.
     */
    public Color pixelColorASS(int nX, int nY, int j, int i) {
        //view plane center
        Point Pc = location.add(vTo.scale(distance));
        //ratio- pixel height and width
        double pixelHeight = (double) height / nY;
        double pixelWidth = (double) width / nX;
        double cellHeight = (double) pixelHeight / antiAliasingNumRays;
        double cellWidth = (double) pixelWidth / antiAliasingNumRays;

        //pixel[i,j] center
        Point Pij = Pc;
        Point point;
        double Yi = -(0.5 + i - (nY - 1) / 2d) * pixelHeight + 0.5 * cellHeight; //how to move from the center of the view plane.
        double Xj = (-0.5 + j - (nX - 1) / 2d) * pixelWidth + 0.5 * cellWidth;
        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));
        List<Color> colors=List.of(  //find the colors of the corners of the pixel
                castRay(new Ray(this.location, Pij.subtract(this.location))),
                castRay(new Ray(this.location, Pij.add(vUp.scale(pixelHeight)).subtract(this.location))),
                castRay(new Ray(this.location, Pij.add(vUp.scale(pixelHeight)).add(vRight.scale(pixelWidth)).subtract(this.location))),
                castRay(new Ray(this.location, Pij.add(vRight.scale(pixelWidth)).subtract(this.location))));
        return recursiveConstructRay(Pij, colors, pixelHeight, pixelWidth, vUp, vRight, (int)Math.log(antiAliasingNumRays-1));//***
    }

    /**
     * calculate the color of area with adaptive super sampling- calculate the average of recursions.
     * @param corner the left down corner of the area.
     * @param c the colors of the 4 corners, arranged clockwise. 0 if the left down, 1 if the left up, 2 if the right up, 3 if the right down.
     * @param height the height of the area.
     * @param width the width of the area.
     * @param vUp the vector of the direction up.
     * @param vRight the vector of the direction right.
     * @param level the level of the recursion (we stop at level 0)
     * @return the color of the area (average of the colors that returned from the recursions.
     */
    public Color recursiveConstructRay(Point corner, List<Color> c, double height, double width, Vector vUp, Vector vRight, int level) {
        //if the colors are very similar return this color.
        if (c.get(0).equals(c.get(1)) && c.get(0).equals(c.get(2)) && c.get(0).equals(c.get(3)))
            return c.get(0);
        //stop the recursion.
        if(level<=0) {
            return c.get(0).add(c.get(1)).add(c.get(2)).add(c.get(3)).reduce(4);
        }
        // 01 if the point (and the color) between the point p0 and p1 (for example).
        //Finds 3 internal points to be sent to the recursive calls.
        Point p01= corner.add(vUp.scale(height/2));
        Point p31= corner.add(vRight.scale(width/2));
        Point pCenter= corner.add(vUp.scale(height/2)).add(vRight.scale(width/2));
        //find the colors of all the internal points.
        Color c01=castRay(new Ray(this.location, p01.subtract(this.location)));
        Color c12=castRay(new Ray(this.location, corner.add(vUp.scale(height)).add(vRight.scale(width/2)).subtract(this.location)));
        Color c23=castRay(new Ray(this.location, corner.add(vUp.scale(height/2)).add(vRight.scale(width)).subtract(this.location)));
        Color c31=castRay(new Ray(this.location, p31.subtract(this.location)));
        Color cCenter=castRay(new Ray(this.location, pCenter.subtract(this.location)));
        //call the recursions and calculate the average.
        return  recursiveConstructRay(corner, List.of(c.get(0), c01, cCenter, c31), height / 2, width / 2, vUp, vRight, level-1)
                .add(recursiveConstructRay(p01, List.of(c01, c.get(1), c12, cCenter), height / 2, width / 2, vUp, vRight, level-1))
                .add(recursiveConstructRay(pCenter, List.of(cCenter, c12, c.get(2), c23), height / 2, width / 2, vUp, vRight, level-1))
                .add(recursiveConstructRay(p31, List.of(c31, cCenter, c23, c.get(3)), height / 2, width / 2, vUp, vRight, level-1))
                .reduce(4);
    }

    public Color recursiveConstructRay(Point corner, double height, double width, Vector vUp, Vector vRight, int level) {
        Point pUpLeft = corner.add(vUp.scale(height));
        Point pDownRight = corner.add(vRight.scale(width));
        Point pUpRight = corner.add(vUp.scale(height)).add(vRight.scale(width));
        Color cUpLeft = castRay(new Ray(this.location, pUpLeft.subtract(this.location)));
        Color cUpRight = castRay(new Ray(this.location, pUpRight.subtract(this.location)));
        Color cDownLeft = castRay(new Ray(this.location, corner.subtract(this.location)));
        Color cDownRight = castRay(new Ray(this.location, pDownRight.subtract(this.location)));
        if (cDownLeft.equals(cUpLeft) && cUpLeft.equals(cUpRight) && cUpRight.equals(cDownRight))
            return cDownLeft;
        if(level>0) {
            pUpLeft = corner.add(vUp.scale(height / 2));
            pDownRight = corner.add(vRight.scale(width / 2));
            pUpRight = corner.add(vUp.scale(height / 2)).add(vRight.scale(width / 2));
            cUpLeft = recursiveConstructRay(pUpLeft, height / 2, width / 2, vUp, vRight, level-1);
            cUpRight = recursiveConstructRay(pUpRight, height / 2, width / 2, vUp, vRight, level-1);
            cDownLeft = recursiveConstructRay(corner, height / 2, width / 2, vUp, vRight, level-1);
            cDownRight = recursiveConstructRay(pDownRight, height / 2, width / 2, vUp, vRight, level-1);
        }
        return cDownLeft.add(cDownRight).add(cUpLeft).add(cUpRight).reduce(4);
    }

    public Color recursiveConstructRay(Point corner, Color cDownLeft, double height, double width, Vector vUp, Vector vRight, int level) {
        Point pUpLeft = corner.add(vUp.scale(height));
        Point pDownRight = corner.add(vRight.scale(width));
        Point pUpRight = corner.add(vUp.scale(height)).add(vRight.scale(width));
        Color cUpLeft = castRay(new Ray(this.location, pUpLeft.subtract(this.location)));
        Color cUpRight = castRay(new Ray(this.location, pUpRight.subtract(this.location)));
        //Color cDownLeft = castRay(new Ray(this.location, corner.subtract(this.location)));
        Color cDownRight = castRay(new Ray(this.location, pDownRight.subtract(this.location)));
        if (cDownLeft.equals(cUpLeft) && cUpLeft.equals(cUpRight) && cUpRight.equals(cDownRight))
            return cDownLeft;
        if(level>0) {
            //pUpLeft = corner.add(vUp.scale(height / 2));
            //pDownRight = corner.add(vRight.scale(width / 2));
            //pUpRight = corner.add(vUp.scale(height / 2)).add(vRight.scale(width / 2));
            cDownLeft = recursiveConstructRay(corner, cDownLeft, height / 2, width / 2, vUp, vRight, level-1);
            cUpLeft = recursiveConstructRay(pUpLeft, cUpLeft, height / 2, width / 2, vUp.scale(-1), vRight, level-1);
            cUpRight = recursiveConstructRay(pUpRight, cUpRight, height / 2, width / 2, vUp.scale(-1), vRight.scale(-1), level-1);
            cDownRight = recursiveConstructRay(pDownRight, cDownRight, height / 2, width / 2, vUp, vRight.scale(-1), level-1);
        }
        return cDownLeft.add(cDownRight).add(cUpLeft).add(cUpRight).reduce(4);
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
        double pixelHeight = (double) height / nY;
        double pixelWidth = (double) width / nX;

        //pixel[i,j] center
        Point Pij = Pc;
        double Yi = -(i - (nY - 1) / 2d) * pixelHeight;
        double Xj = (j - (nX - 1) / 2d) * pixelWidth;
        if (Xj != 0)
            Pij = Pij.add(vRight.scale(Xj));
        if (Yi != 0)
            Pij = Pij.add(vUp.scale(Yi));
        Vector Vij = Pij.subtract(location);
        return new Ray(location, Vij);
    }


    /**
     * find the color that specific ray encounter.
     *
     * @param ray ray in the space.
     * @return the color that the ray encounter.
     */
    private Color castRay(Ray ray) {
        return rayTracer.traceRay(ray);
    }

    /**
     * create the image- write the color of every pixel in of the view plane and save it.
     */
    public void renderImage() {
        //check that all the fields hava a value.
        if (location == null || vTo == null || vUp == null || vRight == null ||
                height == 0 || width == 0 || distance == 0 ||
                imageWriter == null || rayTracer == null) {
            throw new MissingResourceException("can't render image because one of the fields of the camera is null", "", "");
        }
        int Nx = imageWriter.getNx(), Ny = imageWriter.getNy();
        Color color;
        //go over the pixels and find the color of each pixel.
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (antiAliasingNumRays == 1) { //if the improvement "anti aliasing" is off- call the appropriate function.
                    Ray ray = constructRay(Nx, Ny, j, i);
                    color = castRay(ray);
                }
                else if(adaptiveSuperSampling){ //if the improvement "adaptive super sampling" is on- call the appropriate function
                     color = pixelColorASS(Nx, Ny, j, i);
                }
                else { //if the improvement "anti aliasing" is on (and not adaptive)- call the appropriate function for getting the rays and calculate the average of the colors.
                    List<Ray> rays = constructRaySuperSampling(Nx, Ny, j, i);
                    color = Color.BLACK;
                    for (Ray ray :
                            rays) {
                        color = color.add(castRay(ray));
                    }
                    color = color.reduce(rays.size());
                }
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * create a grid on the image according to the interval.
     *
     * @param interval the space between the lines.
     * @param color    the color of the lines.
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("can't create a grid because field imageWriter of the camera is null", "ImageWriter", "");
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * call the method writeToImage() of the imageWriter.
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("can't write the image because field imageWriter of the camera is null", "ImageWriter", "");
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

    /**
     * move the camera according to three parameters of distance.
     *
     * @param distanceTo    distance to move in the direction to.
     * @param distanceUp    distance to move in the direction up.
     * @param distanceRight distance to move in the direction right.
     */
    public void move(double distanceTo, double distanceUp, double distanceRight) {
        if (alignZero(distanceTo) != 0) location = location.add(vTo.scale(distanceTo));
        if (alignZero(distanceUp) != 0) location = location.add(vUp.scale(distanceUp));
        if (alignZero(distanceRight) != 0) location = location.add(vRight.scale(distanceRight));
    }

    /**
     * rotate the camera (change the direction vectors).
     * the formula (for vUp and vTo):
     * new v = v * cos + (k x v)sin + k(k*v)(1-cos)
     * https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
     *
     * @param axis  the axis vector of rotation.
     * @param angle the angle of the rotation.
     */
    public void rotate(Vector axis, double angle) {
        double radAngle = angle * PI / 180;
        Vector k = axis.normalize();
        double cos = cos(radAngle), sin = sin(radAngle);
        //calculate the new vector vTo according the formula.
        this.vTo = calcRotatedVector(vTo, k, cos, sin);
        //calculate the new vector vUp according the formula.
        this.vUp = calcRotatedVector(vUp, k, cos, sin);
        //calculate the new vector vRight (orthogonal to the others).
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * help function fo rotation. calculate the new vector according to the parameters.
     * the formula:
     * new v = v * cos + (k x v)sin + k(k*v)(1-cos)
     * assumes all the parameters are correct!
     *
     * @param v   the vector to rotate.
     * @param k   the axis vector of the rotation (normalized)
     * @param cos the cos of the rotation angle.
     * @param sin the sin of the rotation angle.
     * @return the new vector according to the formula.
     */
    private Vector calcRotatedVector(Vector v, Vector k, double cos, double sin) {
        if (sin == 0) {  //if sin is 0 the so v new = v * cos (because cos is 1 and 1-cos is also 0)
            return v.scale(cos);
        }
        Vector vNew;
        double dotPro = alignZero(k.dotProduct(v));
        try {
            vNew = k.crossProduct(v).scale(sin); // (k x v)sin
        } catch (IllegalArgumentException ex) { // cross product is 0 if the vectors parallel (the vector is rotation axis so the rotation not affect)
            return v;
        }
        if (cos != 0) vNew = vNew.add(v.scale(cos)); // + v * cos
        if (dotPro != 0) vNew = vNew.add(k.scale(dotPro * (1 - cos))); // + k(k*v)(1-cos)
        return vNew;
    }
}
