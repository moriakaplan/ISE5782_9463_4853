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

import static java.awt.Color.*;

public class FinalTest {
    //static private Point
            //p1 = new Point(0, 0, -1.3),
            //p2 = new Point(1, 0, -1.3),
            //p3 = new Point(0, 0, 0),
            //p4 = new Point(1, 0, 0),
            //p5 = new Point(0, -1, -1.3),
            //p6 = new Point(1, -1, -1.3);
    //static private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
    //static private Color color = new Color(RED);
    //List<primitives.Color> colors = new LinkedList<primitives.Color>();

    //colors.Add(new Color(YELLOW), new Color(GREEN), new Color(RED), new Color(BLUE));
    class Rec extends Geometries {
        Rec(Point p1, Point p2, Point p3, Point p4, Color emission, Material material) {
            super(new Triangle(p1, p2, p3).setEmission(emission).setMaterial(material),
                    new Triangle(p1, p4, p3).setEmission(emission).setMaterial(material));
        }
    }

    class BallsPool extends Geometries{
        BallsPool(Point center, Material m){
            super();
            Point b1 = center.add(new Vector(-40, -40, 0))
                    ,b2 = center.add(new Vector(40, -40, 0))
                    ,b3 = center.add(new Vector(40, 40, 0))
                    ,b4 = center.add(new Vector(-40, 40, 0));
            Point u1 = center.add(new Vector(-40, -40, 20))
                    ,u2 = center.add(new Vector(40, -40, 20))
                    ,u3 = center.add(new Vector(40, 40, 20))
                    ,u4 = center.add(new Vector(-40, 40, 20));
            Point bi1 = center.add(new Vector(-30, -30, 2))
                    ,bi2 = center.add(new Vector(30, -30, 2))
                    ,bi3 = center.add(new Vector(30, 30, 2))
                    ,bi4 = center.add(new Vector(-30, 30, 2));
            Point ui1 = center.add(new Vector(-30, -30, 20))
                    ,ui2 = center.add(new Vector(30, -30, 20))
                    ,ui3 = center.add(new Vector(30, 30, 20))
                    ,ui4 = center.add(new Vector(-30, 30, 20));
            Color red = new Color(200, 10, 10);
            Color blue = new Color(10,10,200);
            Color yellow = new Color(180,180,0);
            super.add(new Rec(b1, b2, b3, b4, blue, m) ,
                      new Rec(b1, b2, u2, u1, blue, m) ,
                      new Rec(b3, b2, u2, u3, blue, m) ,
                      new Rec(b3, b4, u4, u3, blue, m) ,
                      new Rec(b1, b4, u4, u1, blue, m),
                      new Rec(bi1, bi2, bi3, bi4, new Color(ORANGE), m) ,
                      new Rec(u1, u2, ui2, ui1, yellow, m) ,
                      new Rec(u3, u2, ui2, ui3, yellow, m) ,
                      new Rec(u3, u4, ui4, ui3, yellow, m) ,
                      new Rec(u1, u4, ui4, ui1, yellow, m),
                      new Rec(ui1, ui2, bi2, bi1, red, m) ,
                      new Rec(ui3, ui2, bi2, bi3, red, m) ,
                      new Rec(ui3, ui4, bi4, bi3, red, m) ,
                      new Rec(ui1, ui4, bi4, bi1, red, m));

        }
    }

    class Slide extends Geometries{
        Slide(Point center, Material material)
        {
            super();
            Point br1 = center.add(new Vector(40,-10,0))
                    ,br2 = center.add(new Vector(40,10,0))
                    ,br3 = center.add(new Vector(30,10,0))
                    ,br4 = center.add(new Vector(30,-10,0));
            Point u1 = center.add(new Vector(0,-10, 40))
                    ,u2 = center.add(new Vector(0,10,40))
                    ,u3 = center.add(new Vector(-10,10,40))
                    ,u4 = center.add(new Vector(-10,-10,40));
            Point b1 = center.add(new Vector(0,-10, 30))
                    ,b2 = center.add(new Vector(0,10,30))
                    ,b3 = center.add(new Vector(-10,10,30))
                    ,b4 = center.add(new Vector(-10,-10,30));
            Point bl1 = center.add(new Vector(-40,-10,0))
                    ,bl2 = center.add(new Vector(-40,10,0))
                    ,bl3 = center.add(new Vector(-50,10,0))
                    ,bl4 = center.add(new Vector(-50,-10,0));
            Point s11 = center.add(new Vector(40, -10,10))
                    ,s12 = center.add(new Vector(40, 10 , 10))
                    ,s21 = center.add(new Vector(30, -10,20))
                    ,s22 = center.add(new Vector(30, 10 , 20))
                    ,s31 = center.add(new Vector(20, -10,30))
                    ,s32 = center.add(new Vector(20, 10 , 30))
                    ,s41 = center.add(new Vector(10, -10,40))
                    ,s42 = center.add(new Vector(10, 10 , 40))
                    ,si1 = center.add(new Vector(30, -10, 10))
                    ,so1 = center.add(new Vector(30, 10, 10))
                    ,si2 = center.add(new Vector(20, -10, 20))
                    ,so2 = center.add(new Vector(20, 10, 20))
                    ,si3 = center.add(new Vector(10, -10, 30))
                    ,so3 = center.add(new Vector(10, 10, 30));
            Color green = new Color(10, 200, 10);
            Color blue = new Color(10,10,200);
            Color yellow = new Color(180,180,0);
            super.add(new Rec(br1, br2, br3, br4, green, material)
                    //,new Rec(br1, br2, u2, u1, red, material)
                    //,new Rec(br2, u2, b2, br3, red, material)
                    ,new Rec(br4,br3,b2,b1,green,material)
                    ,new Rec(br4,br1,u1,b1,green,material)
                    ,new Rec(b1,b2,u2,u1,green,material)

                    ,new Triangle(s11, br1, si1).setEmission(green).setMaterial(material)
                    ,new Rec(br1, br2, s12, s11, green, material)
                    ,new Triangle(br2, s12, so1).setEmission(green).setMaterial(material)
                    ,new Rec(s11, s12, so1, si1, green, material)

                    ,new Triangle(s21, si2, si1).setEmission(green).setMaterial(material)
                    ,new Rec(si1, so1, s22, s21, green, material)
                    ,new Triangle(s22, so2, so1).setEmission(green).setMaterial(material)
                    ,new Rec(s21, s22, so2, si2, green, material)

                    ,new Triangle(s31, si3, si2).setEmission(green).setMaterial(material)
                    ,new Rec(si2, so2, s32, s31, green, material)
                    ,new Triangle(so3, s32, so2).setEmission(green).setMaterial(material)
                    ,new Rec(s31, s32, so3, si3, green, material)

                    ,new Triangle(s41, u1, si3).setEmission(green).setMaterial(material)
                    ,new Rec(si3, so3, s42, s41, green, material)
                    ,new Triangle(u2, s42, so3).setEmission(green).setMaterial(material)
                    ,new Rec(s41, s42, u2, u1, green, material)

                    ,new Rec(b1, b2, b3, b4, green, material)
                    ,new Rec(b1, u1, u4, b4, green, material)
                    ,new Rec(u1, u2, u3, u4, green, material)
                    ,new Rec(u2, b2, b3, u3, green, material)
                    ,new Rec(bl1, bl2, bl3, bl4, green, material)
                    ,new Rec(bl1, bl2, b3, b4, green, material)
                    ,new Rec(bl1, b4, u4, bl4, green, material)
                    ,new Rec(bl4, bl3, u3, u4, green, material)
                    ,new Rec(bl2, b3, u3, bl3, green, material));

        }
    }

    class BallsGrid extends Geometries{
        BallsGrid(Point start, Vector v1, Vector v2, int size1, int size2, double radius, Material material){
            v1.normalize();
            v2.normalize();
            Point center;
            for (int i = 0; i < size1; i++) {
                for (int j = 0; j < size2; j++) {
                    center = start.add(v1.scale(radius*1.01*(1+2*i))).add(v2.scale(radius*1.01*(1+2*j)));
                    super.add(new Sphere(center, radius).setEmission(randColor()).setMaterial(material));
                }
            }
        }
    }

    Color randColor(){
        List<Color> colors = new LinkedList<Color>();
        colors.add(new Color(200,200,0));
        colors.add(new Color(10,10,150));
        colors.add(new Color(150,10,10));
        colors.add(new Color(200, 100,100));
        colors.add(new Color(10,150,10));
        Random rand = new Random();
        int i=rand.nextInt(colors.size());
        return colors.get(i);
        //return new Color(rand.nextInt(200),rand.nextInt(200),rand.nextInt(200));
    }
    @Test
    public void testAll() {
        //class Chair extends Geometries {
        //    //static private Point p1=new Point(0,0,0), p2=new Point(1,0,0), p3=new Point(0,0,1.5), p4=new Point(1, 0, 1.5),
        //    //        p5=new Point(0, -1, 0), p6=new Point(1, -1, 0);
        //    static private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
        //    static private Color color = new Color(WHITE);
//
        //    public Chair() {
        //        super(new Triangle(p1, p2, p3).setEmission(color),
        //                new Triangle(p2, p3, p4).setEmission(color),
        //                new Triangle(p1, p2, p6).setEmission(color),
        //                new Triangle(p1, p5, p6).setEmission(color));
        //    }
        //}

        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(0, -50, 1), new Vector(0, 1, -0.02), new Vector(0, 0.02, 1)) //
                .setVPSize(5, 5).setVPDistance(50);
        Camera camera2 = new Camera(new Point(0, 0.5, 50), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(5, 5).setVPDistance(50);

        //Geometry t = new Triangle(p1, p2, p3);
        //t.setEmission(color);
        //t.setMaterial(material);
        //scene.geometries.add( //
        //        new Chair(),
        //        new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)).setEmission(new Color(GRAY))
        //                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.7)));

        scene.lights.add( //
                new PointLight(new Color(200, 120, 0), new Point(1, 15, 1)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("startingOfChair", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        camera2.setImageWriter(new ImageWriter("startingOfChair2", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera2.writeToImage();
    }

    @Test
    public void testPool() {
        Point b1 = new Point(-10, -20, -10);
        Point b2 = new Point(10, -20, -10);
        Point b3 = new Point(10, 20, -10);
        Point b4 = new Point(-10, 20, -10);
        Point u1 = new Point(-10, -20, 10);
        Point u2 = new Point(10, -20, 10);
        Point u3 = new Point(10, 20, 10);
        Point u4 = new Point(-10, 20, 10);
        Color e1 = new Color(0, 100, 0);
        Color e2 = new Color(0, 0, 100);
        Material m1 = new Material();
        Material m2 = new Material().setKt(0.2).setKr(0.8);

        Scene scene = new Scene("pool");
        Camera camera = new Camera(new Point(0, -50, 20), new Vector(0, 5, -2), new Vector(0, 2, 5)) //
                .setVPSize(60, 60).setVPDistance(50);
        scene.geometries.add(new Triangle(b1, b2, b3).setEmission(e1).setMaterial(m1));
        scene.geometries.add(new Triangle(b1, b4, b3).setEmission(e1).setMaterial(m1));

        scene.geometries.add(new Triangle(b1, b2, u1).setEmission(e1).setMaterial(m1));
        scene.geometries.add(new Triangle(b2, u1, u2).setEmission(e1).setMaterial(m1));

        scene.geometries.add(new Triangle(b2, b3, u2).setEmission(e1).setMaterial(m1));
        scene.geometries.add(new Triangle(b3, u2, u3).setEmission(e1).setMaterial(m1));

        scene.geometries.add(new Triangle(b3, b4, u3).setEmission(e1).setMaterial(m1));
        scene.geometries.add(new Triangle(b4, u3, u4).setEmission(e1).setMaterial(m1));

        scene.geometries.add(new Triangle(b4, b1, u4).setEmission(e1).setMaterial(m1));
        scene.geometries.add(new Triangle(b1, u4, u1).setEmission(e1).setMaterial(m1));

        scene.geometries.add(new Triangle(u1, u2, u3).setEmission(e2).setMaterial(m2));
        scene.geometries.add(new Triangle(u1, u4, u3).setEmission(e2).setMaterial(m2));

        scene.geometries.add(new Plane(b1, new Vector(0, 0, 1)).setEmission(new Color(PINK)));
        scene.geometries.add(new Plane(new Point(0, 0, 30), new Vector(0, 0, -1)).setEmission(new Color(GRAY)).setMaterial(new Material()));
        scene.geometries.add(new Plane(new Point(0, 50, 0), new Vector(0, -1, 0)).setEmission(new Color(RED)).setMaterial(new Material()));
        scene.geometries.add(new Plane(new Point(30, 0, 0), new Vector(-1, 0, 0)).setEmission(new Color(GRAY)).setMaterial(new Material()));
        scene.geometries.add(new Plane(new Point(-30, 0, 0), new Vector(1, 0, 0)).setEmission(new Color(GRAY)).setMaterial(new Material()));

        scene.lights.add(new PointLight(new Color(YELLOW), new Point(5, 0, 3)));
        camera.setImageWriter(new ImageWriter("pool", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

    @Test
    public void jimbory() {
        //Point b1 = center.add(new Vector(-40, -40, 0))
        //        ,b2 = center.add(new Vector(40, -40, 0))
        //        ,b3 = center.add(new Vector(40, 40, 0))
        //        ,b4 = center.add(new Vector(-40, 40, 0));
        //Point u1 = center.add(new Vector(-40, -40, 20))
        //        ,u2 = center.add(new Vector(40, -40, 20))
        //        ,u3 = center.add(new Vector(40, 40, 20))
        //        ,u4 = center.add(new Vector(-40, 40, 20));
        //Point bi1 = center.add(new Vector(-30, -30, 2))
        //        ,bi2 = center.add(new Vector(30, -30, 2))
        //        ,bi3 = center.add(new Vector(30, 30, 2))
        //        ,bi4 = center.add(new Vector(-30, 30, 2));
        //Point ui1 = center.add(new Vector(-30, -30, 20))
        //        ,ui2 = center.add(new Vector(30, -30, 20))
        //        ,ui3 = center.add(new Vector(30, 30, 20))
        //        ,ui4 = center.add(new Vector(-30, 30, 20));

        Color wallE = new Color(GRAY);
        Color red = new Color(200, 10, 10);
        Color blue = new Color(10,10,200);
        Color yellow = new Color(180,180,0);

        Material wallM = new Material().setKd(0.5).setKs(0.5).setShininess(5);
        Material poolM = new Material().setKd(0.7).setKs(0.8).setShininess(150);

        Point lightPlace=new Point(0, 0,96);

        Scene scene = new Scene("pool");
        Camera camera = new Camera(new Point(0, -200, 50), new Vector(0, 10, -1), new Vector(0, 1, 10)) //
                .setVPSize(250, 250).setVPDistance(200);
        Camera cameraUp = new Camera(new Point(0, -80, 90), new Vector(0, 10, -9), new Vector(0, 9, 10)) //
                .setVPSize(250, 250).setVPDistance(150);
        scene.geometries.add(new Plane(new Point(0, 100, 0), new Vector(0, -1, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(100, 0, 0), new Vector(-1, 0, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(-100, 0, 0), new Vector(1, 0, 0)).setEmission(wallE).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(0, 0, 0), new Vector(0, 0, 1)).setEmission(new Color(50,50,50)).setMaterial(wallM));
        scene.geometries.add(new Plane(new Point(0, 0, 100), new Vector(0, 0, -1)).setEmission(new Color(180,180,180)).setMaterial(wallM));

        scene.geometries.add(new BallsPool(new Point(0,0,0), poolM));
        //scene.geometries.add(new Slide(new Point(0,0,0), poolM));
        //scene.geometries.add(new BallsGrid(new Point(28,28,3), new Vector(-1,0,0), new Vector(0,-1,0), 11,11, 2.5, poolM));
        //scene.geometries.add(new BallsGrid(new Point(25.5,25.5,6.25), new Vector(-1,0,0), new Vector(0,-1,0), 10,10, 2.5, poolM));
        //scene.geometries.add(new BallsGrid(new Point(0,23,10.5), new Vector(-1,0,0), new Vector(0,-1,0), 5,8, 2.5, poolM));
        //scene.geometries.add(new BallsGrid(new Point(40,40,25), new Vector(-1,0,0), new Vector(0,-1,0), 2,2, 10, poolM));
        scene.geometries.add(new BallsPool(new Point(0,0,0), poolM));
        scene.geometries.add(new Slide(new Point(40,0,0), poolM));
        scene.geometries.add(new Sphere(lightPlace, 4).setEmission(yellow).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50).setKt(0.85)));
        scene.geometries.add(new Sphere(lightPlace.subtract(new Vector(0,0,4)), 10).setEmission(new Color(50,50,35)).setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50).setKt(0.9)));

        //scene.geometries.add(new Rec(b1, b2, b3, b4, blue, poolM));
        //scene.geometries.add(new Rec(b1, b2, u2, u1, blue, poolM));
        //scene.geometries.add(new Rec(b3, b2, u2, u3, blue, poolM));
        //scene.geometries.add(new Rec(b3, b4, u4, u3, blue, poolM));
        //scene.geometries.add(new Rec(b1, b4, u4, u1, blue, poolM));
        //
        //scene.geometries.add(new Rec(bi1, bi2, bi3, bi4, new Color(ORANGE), poolM));
        //scene.geometries.add(new Rec(u1, u2, ui2, ui1, yellow, poolM));
        //scene.geometries.add(new Rec(u3, u2, ui2, ui3, yellow, poolM));
        //scene.geometries.add(new Rec(u3, u4, ui4, ui3, yellow, poolM));
        //scene.geometries.add(new Rec(u1, u4, ui4, ui1, yellow, poolM));
        //
        //scene.geometries.add(new Rec(ui1, ui2, bi2, bi1, red, poolM));
        //scene.geometries.add(new Rec(ui3, ui2, bi2, bi3, red, poolM));
        //scene.geometries.add(new Rec(ui3, ui4, bi4, bi3, red, poolM));
        //scene.geometries.add(new Rec(ui1, ui4, bi4, bi1, red, poolM));

        scene.lights.add(new SpotLight(new Color(RED), new Point(50,99, 99), new Vector(50,-99,-100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new SpotLight(new Color(GREEN), new Point(-50,99, 99), new Vector(-50,-99,-100)).setNarrowBeam(5).setKl(0.0001).setKq(0.00001));
        scene.lights.add(new PointLight(new Color(40, 40, 20), lightPlace));
        scene.lights.add(new PointLight(new Color(100,100,100), new Point(0, -200, 50)));
        scene.softShadowOn(7, 3);
        camera.setImageWriter(new ImageWriter("ballsPoolSoftShadow", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        //cameraUp.setImageWriter(new ImageWriter("ballsPoolUp", 400, 400)) //
        //        .setRayTracer(new RayTracerBasic(scene)) //
        //        .renderImage(); //
        //cameraUp.writeToImage();
    }
}


