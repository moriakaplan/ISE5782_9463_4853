/**
 * 
 */
package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import lighting.*;
import geometries.*;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000);//.antiAliasingOn(9, false);

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))/*,
				new Sphere(new Point(60, 50, 0), 3d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.7)),
				new Triangle(new Point(50, 50, 0), new Point(80, 40, -30),new Point(60,40,-10))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.3)).setEmission(new Color(GREEN)),
				new Triangle(new Point(45, 45, -5), new Point(95, 35, -35),new Point(60,35,-15))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.3)).setEmission(new Color(YELLOW))*/);

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));
		camera.antiAliasingOn(17, true);
		//scene.softShadowOn(1, 0.1);
		//ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		ImageWriter imageWriter = new ImageWriter("refractionShadowAdaptiveSuperSampling", 600, 600);
		//ImageWriter imageWriter = new ImageWriter("refractionShadowWithTriangle", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}
	/**
	 * Produce a picture of two triangles lighted by a spotlight with a partially
	 * transparent Sphere producing partial shadow with anti aliasing
	 */
	@Test
	public void antiAliasingTest() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadowAntialiasing", 600, 600);
		camera.setImageWriter(imageWriter) //
				.antiAliasingOn(9, false)
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}

	@Test
	public void ImageWithSomeShapes() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
		scene.geometries.add(
				new Plane(new Point(0, -35, 0), new Vector(0,1,0)).setEmission(new Color(GRAY))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.7)),
				new Sphere(new Point(28, 10, -10), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Triangle(new Point(100, -35, -250), new Point(-50, -35, -80), new Point(-30, 40, -120)).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.7))
		);

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, -50), new Vector(-20, -5, 5)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionReflectionShadowSomeShapes1", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();

		camera.move(0, 0, 500);
		camera.rotate(camera.getvUp(), 25);

		imageWriter = new ImageWriter("refractionReflectionShadowSomeShapes2", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}

	@Test
	public void ImageWithSomeShapes2() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(-70, 170, 140)).setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.5)), //
				new Triangle(new Point(75, 75, -150), new Point(-70, 70, -140), new Point(-70, 170, 140)).setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.5)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionReflectionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage(); //
		camera.writeToImage();
	}
}
