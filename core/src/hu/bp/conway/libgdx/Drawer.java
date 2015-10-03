package hu.bp.conway.libgdx;

import hu.bp.conway.modules.Universe;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Drawer {

	public static final float spacer = 0.02f;

	public static void draw(Camera camera, ShapeRenderer renderer, Universe u) {

		renderer.begin(ShapeType.Filled);
		renderer.setColor(0, 1, 0, 1);

		float dx = camera.viewportWidth / u.n;

		float dy = camera.viewportHeight / u.n;

		for (int r = 0; r < u.n; r++) {
			for (int c = 0; c < u.n; c++) {
				if (u.isAlive(r, c)) {
					renderer.rect(c * dx + spacer * dx, r * dy + spacer * dx,
						dx - 2 * spacer * dx, dy - 2 * spacer * dx);
				}
			}
		}

		renderer.end();
	}

	public static void test(ShapeRenderer shapeRenderer) {
		shapeRenderer.begin(ShapeType.Filled);
		 shapeRenderer.identity();
		 shapeRenderer.translate(20, 12, 2);
		 shapeRenderer.rotate(0, 0, 1, 90);
		 shapeRenderer.rect(-10 / 2, -10 / 2, 10, 10);
		 shapeRenderer.end();
	}

	public static void draw(OrthographicCamera camera, ShapeRenderer renderer,
			Rectangle rectangle) {

		Vector3 r1 = new Vector3(rectangle.x, rectangle.y, 0);
		Vector3 r2 = new Vector3(rectangle.x + rectangle.width,
				rectangle.y + rectangle.height, 0);

		Vector3 w1 = camera.unproject(r1);
		Vector3 w2 = camera.unproject(r2);

		Rectangle w = new Rectangle(w1.x, w1.y, w2.x - w1.x, w2.y - w1.y);

		renderer.begin(ShapeType.Line);
		renderer.setColor(0, 0, 1, 1);
		renderer.rect(w.x, w.y, w.width, w.height);
		renderer.end();
	}

}
