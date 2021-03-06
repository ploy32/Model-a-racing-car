package edu.cg.models.Car;

import java.util.LinkedList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import edu.cg.algebra.Point;
import edu.cg.algebra.Vec;
import edu.cg.models.BoundingSphere;
import edu.cg.models.IIntersectable;
import edu.cg.models.IRenderable;
import edu.cg.models.SkewedBox;

public class Back implements IRenderable, IIntersectable {
	private SkewedBox baseBox = new SkewedBox(Specification.B_BASE_LENGTH, Specification.B_BASE_HEIGHT,
			Specification.B_BASE_HEIGHT, Specification.B_BASE_DEPTH, Specification.B_BASE_DEPTH);
	private SkewedBox backBox = new SkewedBox(Specification.B_LENGTH, Specification.B_HEIGHT_1,
			Specification.B_HEIGHT_2, Specification.B_DEPTH_1, Specification.B_DEPTH_2);
	private PairOfWheels wheels = new PairOfWheels();
	private Spolier spoiler = new Spolier();

	@Override
	public void render(GL2 gl) {
		gl.glPushMatrix();

		Materials.SetBlackMetalMaterial(gl);
		gl.glTranslated(Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0, 0.0, 0.0);
		baseBox.render(gl);
		Materials.SetRedMetalMaterial(gl);
		gl.glTranslated(-1.0 * (Specification.B_LENGTH / 2.0 - Specification.B_BASE_LENGTH / 2.0),
				Specification.B_BASE_HEIGHT, 0.0);
		backBox.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + Specification.TIRE_RADIUS, 0.5 * Specification.TIRE_RADIUS,
				0.0);
		wheels.render(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslated(-Specification.B_LENGTH / 2.0 + 0.5 * Specification.S_LENGTH,
				0.5 * (Specification.B_HEIGHT_1 + Specification.B_HEIGHT_2), 0.0);
		spoiler.render(gl);

		gl.glPopMatrix();

		//pipes addition
		Materials.setMaterialRims (gl);
		GLU glu;
		GLUquadric quad;
		glu = new GLU();
		quad = glu.gluNewQuadric();
		gl.glPushMatrix();

		gl.glTranslated(-Specification.B_LENGTH / 2 - 0.02, 0.04, 0.03);
		gl.glRotated(90.0, 0.0, 2.0, 0.0);
		glu.gluCylinder(quad, (Specification.S_ROD_RADIUS - 0.006), (Specification.S_ROD_RADIUS - 0.006), Specification.S_ROD_HIEGHT - 0.03, 15, 2);

		gl.glPopMatrix();
		gl.glPushMatrix(); // second pipe

		gl.glTranslated(-Specification.B_LENGTH / 2 - 0.02, 0.04, -0.03);
		gl.glRotated(90.0, 0.0, 2.0, 0.0);
		glu.gluCylinder(quad, (Specification.S_ROD_RADIUS - 0.006), (Specification.S_ROD_RADIUS - 0.006), Specification.S_ROD_HIEGHT - 0.03, 15, 2);
		gl.glPopMatrix();
		glu.gluDeleteQuadric(quad);
	}

	@Override
	public void init(GL2 gl) {

	}

	@Override
	public List<BoundingSphere> getBoundingSpheres() {
		// TODO: Return a list of bounding spheres the list structure is as follow:
		// s1
		// where:
		// s1 - sphere bounding the car front
		LinkedList<BoundingSphere> res = new LinkedList<BoundingSphere>();
		double radius_V;
		Point center_P;
		BoundingSphere b_sphere_b;

		radius_V = new Vec (Specification.B_LENGTH / 2,
				Specification.B_HEIGHT / 2,
				Specification.B_DEPTH / 2).norm();
		center_P = new Point(0, Specification.B_HEIGHT / 2, 0);
		b_sphere_b = new BoundingSphere(radius_V, center_P);
		b_sphere_b.setSphereColore3d (0,0,1);
		res.add(b_sphere_b);
		return res;

	}

}
