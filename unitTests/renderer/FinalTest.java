package renderer;

import geometries.*;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.awt.Color.*;

public class FinalTest {
    class Rec extends Geometries {
        Rec(Point p1, Point p2, Point p3, Point p4, Color emission, Material material) {
            super(new Triangle(p1, p2, p3).setEmission(emission).setMaterial(material),
                    new Triangle(p1, p4, p3).setEmission(emission).setMaterial(material));
        }
    }

    class BallsPool extends Geometries {
        Point center;
        BallsPool(Point _center, Material m) {
            super();
            center = _center;
            Point b1 = center.add(new Vector(-50, -50, 0)), b2 = center.add(new Vector(50, -50, 0)), b3 = center.add(new Vector(50, 50, 0)), b4 = center.add(new Vector(-50, 50, 0));
            Point u1 = center.add(new Vector(-50, -50, 20)), u2 = center.add(new Vector(50, -50, 20)), u3 = center.add(new Vector(50, 50, 20)), u4 = center.add(new Vector(-50, 50, 20));
            Point bi1 = center.add(new Vector(-40, -40, 2)), bi2 = center.add(new Vector(40, -40, 2)), bi3 = center.add(new Vector(40, 40, 2)), bi4 = center.add(new Vector(-40, 40, 2));
            Point ui1 = center.add(new Vector(-40, -40, 20)), ui2 = center.add(new Vector(40, -40, 20)), ui3 = center.add(new Vector(40, 40, 20)), ui4 = center.add(new Vector(-40, 40, 20));
            Color red = new Color(200, 10, 10);
            Color blue = new Color(10, 10, 200);
            Color yellow = new Color(180, 180, 0);
            super.add(new Rec(b1, b2, b3, b4, blue, m),
                    new Rec(b1, b2, u2, u1, blue, m),
                    new Rec(b3, b2, u2, u3, blue, m),
                    new Rec(b3, b4, u4, u3, blue, m),
                    new Rec(b1, b4, u4, u1, blue, m),
                    new Rec(bi1, bi2, bi3, bi4, new Color(ORANGE), m),
                    new Rec(u1, u2, ui2, ui1, yellow, m),
                    new Rec(u3, u2, ui2, ui3, yellow, m),
                    new Rec(u3, u4, ui4, ui3, yellow, m),
                    new Rec(u1, u4, ui4, ui1, yellow, m),
                    new Rec(ui1, ui2, bi2, bi1, red, m),
                    new Rec(ui3, ui2, bi2, bi3, red, m),
                    new Rec(ui3, ui4, bi4, bi3, red, m),
                    new Rec(ui1, ui4, bi4, bi1, red, m));
        }
        @Override
        public void createBoundingBox() {
            double minX = center.getX() - 50
                    ,minY = center.getY() - 50
                    ,minZ = center.getZ()
                    ,maxX = center.getX() + 50
                    ,maxY = center.getY() + 50
                    ,maxZ = center.getZ() + 20;
            box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));

        }
    }

    class Slide extends Geometries {
        Point center;
        Slide(Point _center, Material material, Material railM) {
            super();
            center = _center;
            Point br1 = center.add(new Vector(40, -10, 0)),
                    br2 = center.add(new Vector(40, 10, 0)),
                    br3 = center.add(new Vector(30, 10, 0)),
                    br4 = center.add(new Vector(30, -10, 0));
            Point u1 = center.add(new Vector(0, -10, 40)),
                    u2 = center.add(new Vector(0, 10, 40)),
                    u3 = u2
                    , u4 = u1;
            Point b1 = center.add(new Vector(0, -10, 30)),
                    b2 = center.add(new Vector(0, 10, 30)),
                    b3 = b2
                    , b4 = b1;
            Point bl1 = center.add(new Vector(-30, -10, 0)),
                    bl2 = center.add(new Vector(-30, 10, 0)),
                    bl3 = center.add(new Vector(-40, 10, 0)),
                    bl4 = center.add(new Vector(-40, -10, 0));
            Point s11 = center.add(new Vector(40, -10, 10)),
                    s12 = center.add(new Vector(40, 10, 10)),
                    s21 = center.add(new Vector(30, -10, 20)),
                    s22 = center.add(new Vector(30, 10, 20)),
                    s31 = center.add(new Vector(20, -10, 30)),
                    s32 = center.add(new Vector(20, 10, 30)),
                    s41 = center.add(new Vector(10, -10, 40)),
                    s42 = center.add(new Vector(10, 10, 40)),
                    si1 = center.add(new Vector(30, -10, 10)),
                    so1 = center.add(new Vector(30, 10, 10)),
                    si2 = center.add(new Vector(20, -10, 20)),
                    so2 = center.add(new Vector(20, 10, 20)),
                    si3 = center.add(new Vector(10, -10, 30)),
                    so3 = center.add(new Vector(10, 10, 30));
            Point r1 = center.add(new Vector(10, -10, 50)),
                    r2 = center.add(new Vector(10, 10, 50)),
                    r3 = center.add(new Vector(-10, 10, 50)),
                    r4 = center.add(new Vector(-10, -10, 50)),
                    rr1 = center.add(new Vector(40, -10, 20)),
                    rr2 = center.add(new Vector(40, 10, 20)),
                    rl1 = center.add(new Vector(-40, -10, 20)),
                    rl2 = center.add(new Vector(-40, 10, 20));
            Color green = new Color(10, 200, 10);
            Color blue = new Color(30, 30, 200);
            super.add(new Rec(br1, br2, br3, br4, green, material)
                    , new Rec(br4, br3, b2, b1, green, material)
                    , new Rec(br4, br1, u1, b1, green, material)
                    , new Rec(b1, b2, u2, u1, green, material)

                    , new Triangle(s11, br1, si1).setEmission(green).setMaterial(material)
                    , new Rec(br1, br2, s12, s11, green, material)
                    , new Triangle(br2, s12, so1).setEmission(green).setMaterial(material)
                    , new Rec(s11, s12, so1, si1, green, material)

                    , new Triangle(s21, si2, si1).setEmission(green).setMaterial(material)
                    , new Rec(si1, so1, s22, s21, green, material)
                    , new Triangle(s22, so2, so1).setEmission(green).setMaterial(material)
                    , new Rec(s21, s22, so2, si2, green, material)

                    , new Triangle(s31, si3, si2).setEmission(green).setMaterial(material)
                    , new Rec(si2, so2, s32, s31, green, material)
                    , new Triangle(so3, s32, so2).setEmission(green).setMaterial(material)
                    , new Rec(s31, s32, so3, si3, green, material)

                    , new Triangle(s41, u1, si3).setEmission(green).setMaterial(material)
                    , new Rec(si3, so3, s42, s41, green, material)
                    , new Triangle(u2, s42, so3).setEmission(green).setMaterial(material)
                    , new Rec(s41, s42, u2, u1, green, material)

                    , new Rec(bl1, bl2, bl3, bl4, green, material)
                    , new Rec(bl1, bl2, b3, b4, green, material)
                    , new Rec(bl1, b4, u4, bl4, green, material)
                    , new Rec(bl4, bl3, u3, u4, green, material)
                    , new Rec(bl2, b3, u3, bl3, green, material)

                    , new Rec(b1, r1, rr1, br4, blue, railM)
                    , new Triangle(br4, rr1, br1).setEmission(blue).setMaterial(railM)
                    , new Rec(b2, r2, rr2, br3, blue, railM)
                    , new Triangle(br3, rr2, br2).setEmission(blue).setMaterial(railM)
                    , new Rec(b1, r4, rl1, bl1, blue, railM)
                    , new Triangle(bl4, rl1, bl1).setEmission(blue).setMaterial(railM)
                    , new Rec(b2, r3, rl2, bl2, blue, railM)
                    , new Triangle(bl3, rl2, bl2).setEmission(blue).setMaterial(railM)
                    , new Triangle(r4, r1, b1).setEmission(blue).setMaterial(railM)
                    , new Triangle(r3, r2, b2).setEmission(blue).setMaterial(railM));

        }

        @Override
        public void createBoundingBox() {
            double minX = center.getX() - 40
                    ,minY = center.getY() - 10
                    ,minZ = center.getZ()
                    ,maxX = center.getX() + 40
                    ,maxY = center.getY() + 10
                    ,maxZ = center.getZ() + 50;
            box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));

        }
    }

    class BallsGrid extends Geometries {
        Point start;
        BallsGrid(Point _start, Vector v1, Vector v2, int size1, int size2, double radius, Material material) {
            start = _start;
            v1.normalize();
            v2.normalize();
            Point center;
            for (int i = 0; i < size1; i++) {
                for (int j = 0; j < size2; j++) {
                    center = start.add(v1.scale(radius * 1.01 * (1 + 2 * i))).add(v2.scale(radius * 1.01 * (1 + 2 * j)));
                    super.add(new Sphere(center, radius).setEmission(randColor()).setMaterial(material));
                }
            }
        }
        @Override
        public void createBoundingBox() {
            double minX = start.getX() - 40
                    ,minY = start.getY() - 10
                    ,minZ = start.getZ()
                    ,maxX = start.getX() + 40
                    ,maxY = start.getY() + 10
                    ,maxZ = start.getZ() + 50;
            box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
        }

    }

    class RhombusesGrid extends Geometries {
        Point start;
        RhombusesGrid(Point _start, Vector v1, Vector v2, int size1, int size2, int areaSize1, int areaSize2,  Color color, Material material) {
            start = _start;
            v1=v1.normalize().scale(size1/2);
            v2=v2.normalize().scale(size2/2);
            Point rCenter;
            Color c;
            for (int i = 0; i < areaSize1 / size1; i++) {
                for (int j = 0; j < areaSize2 / size2; j++) {
                    rCenter = start.add(v1.scale(1 + 2*i)).add(v2.scale(1+2*j)); //the center of the current rhombus.
                    super.add(new Rec(rCenter.subtract(v1), rCenter.subtract(v2), rCenter.add(v1),rCenter.add(v2), color, material));
                }
            }
        }
        @Override
        public void createBoundingBox() {
            double minX = start.getX() - 40
                    ,minY = start.getY() - 10
                    ,minZ = start.getZ()
                    ,maxX = start.getX() + 40
                    ,maxY = start.getY() + 10
                    ,maxZ = start.getZ() + 50;
            box = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));

        }
    }


    Color randColor() {
        List<Color> colors = new LinkedList<Color>();
        colors.add(new Color(200, 200, 0));
        colors.add(new Color(10, 10, 150));
        colors.add(new Color(150, 10, 10));
        colors.add(new Color(200, 100, 100));
        colors.add(new Color(10, 150, 10));
        Random rand = new Random();
        int i = rand.nextInt(colors.size());
        return colors.get(i);
    }

    @Test
    public void BallsPool() {
        Color wallE = new Color(GRAY);
        Color yellowLight = new Color(250, 250, 10);
        Color blue = new Color(10, 10, 300);
        Color yellow = new Color(180, 180, 0);

        Material wallM = new Material().setKd(0.5).setKs(0.5).setShininess(5);
        Material poolM = new Material().setKd(0.7).setKs(0.8).setShininess(150);
        Material railM = new Material().setKd(0.7).setKs(0.8).setShininess(50).setKt(0.75);

        Point lightPlace = new Point(0, 0, 96);

        Scene scene = new Scene("pool");
        Camera camera = new Camera(new Point(0, -200, 50), new Vector(0, 10, -1), new Vector(0, 1, 10)) //
                .setVPSize(250, 250).setVPDistance(200);//.multiThreadingOn();
        Camera cameraUp = new Camera(new Point(0, -100, 90), new Vector(0, 10, -8), new Vector(0, 8, 10)) //
                .setVPSize(300, 300).setVPDistance(170);//.multiThreadingOn();
        scene.geometries.add(new Plane(new Point(0, 100, 0), new Vector(0, -1, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(100, 0, 0), new Vector(-1, 0, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(-100, 0, 0), new Vector(1, 0, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(50, 50, 50)).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(0, 0, 100), new Vector(0, 0, -1)).setEmission(new Color(180, 180, 180)).setMaterial(wallM));
        //scene.geometries.add(new RhombusesGrid(new Point(-99.9,99.9,99.9), new Vector(1,0,0), new Vector(0,0,-1), 10, 20, 200, 200, new Color(50, 50, 50), new Material().setKr(0.9).setShininess(10).setKs(0.5).setKd(0.5)));
        //scene.geometries.add(new Floor(new Point(-100, 100, 0.1), new Vector(1,0,0), new Vector(0,-1,0), 20, 200, 200, wallE, wallM));

        //scene.geometries.add(new BallsPool(new Point(0,0,0), poolM));
        //scene.geometries.add(new BallsGrid(new Point(28, 28, 3), new Vector(-1, 0, 0), new Vector(0, -1, 0), 11, 11, 2.5, poolM));
        //scene.geometries.add(new BallsGrid(new Point(25.5, 25.5, 6.25), new Vector(-1, 0, 0), new Vector(0, -1, 0), 10, 10, 2.5, poolM));
        //scene.geometries.add(new BallsGrid(new Point(0, 23, 10.5), new Vector(-1, 0, 0), new Vector(0, -1, 0), 5, 8, 2.5, poolM));

        scene.geometries.add(new BallsPool(new Point(-10, 0, 0), poolM));
        scene.geometries.add(new Slide(new Point(40, 0, 0), poolM, railM));
        scene.geometries.add(new Sphere(lightPlace, 4).setEmission(yellow).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50).setKt(0.85)));
        scene.geometries.add(new Sphere(lightPlace.subtract(new Vector(0, 0, 4)), 10).setEmission(new Color(50, 50, 35)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50).setKt(0.9)));


        scene.lights.add(new SpotLight(new Color(RED), new Point(50, 99, 99), new Vector(50, -50, -100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new SpotLight(new Color(GREEN), new Point(-50, 99, 99), new Vector(-50, -50, -100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new SpotLight(blue, new Point(50, -50, 99), new Vector(50, 99, -100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new SpotLight(yellowLight, new Point(-50, -50, 99), new Vector(-50, 99, -100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new PointLight(new Color(40, 40, 20), lightPlace));
        scene.lights.add(new PointLight(new Color(70, 70, 70), new Point(0, -200, 50)));
        //scene.softShadowOn(7, 3);
        camera.setImageWriter(new ImageWriter("ballsPool", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        camera.writeToImage();
        cameraUp.setImageWriter(new ImageWriter("ballsPoolUp", 400, 400))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage();
        cameraUp.writeToImage();
    }

}


