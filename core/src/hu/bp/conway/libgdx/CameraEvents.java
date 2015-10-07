package hu.bp.conway.libgdx;

import hu.bp.conway.ConwaysGame;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CameraEvents extends InputAdapter {

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean scrolled(int amount) {
		camera.zoom += amount * camera.zoom / 10;

		return super.scrolled(amount);
	}

	private boolean reset = true;
	private boolean dragging = false;
	public static final int MIN_DX = 10;
	public static final int MIN_DY = 10;
	private static final float P = 0.05f;
	private static final float I = 0.1f;
	private int x0 = 0,y0 = 0, x1 = MIN_DX, y1 = MIN_DY;
	private OrthographicCamera camera;
	private Vector3 worldTouchDownPos;
	private float goalX;
	private float goalY;
	private float integralX;
	private float integralY;

	private final float MINDIFFX = ConwaysGame.WIDTH / ConwaysGame.UNIVERSE_N;
	private final float MINDIFFY = ConwaysGame.HEIGHT / ConwaysGame.UNIVERSE_N;

	public CameraEvents(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void updateCamera() {
		if (dragging) {
			float errX = goalX - camera.position.x;
			float errY = goalY - camera.position.y;

			if ((Math.abs(errX) > MINDIFFX) ||
				(Math.abs(errY) > MINDIFFY)) {

					float proportionalX = P * errX;
					float proportionalY = P * errY;
			
					integralX += errX;
					integralY += errY;
			
			
					float controlX = proportionalX + I * integralX;
					float controlY = proportionalY + I * integralY;
	
	
					System.out.println("diff:" + errX + "," + errY + "," + controlX + "," + controlY);
					camera.position.x += controlX;
					camera.position.y += controlY;
			}
			else {
				integralX = 0;
				integralY = 0;
				dragging = false;
			}
		}
	}

	public Rectangle getRectangle() {
		if (x0 < x1 && y0 < y1) {
			return 
				new Rectangle(
					x0, y0, Math.max(MIN_DX,x1 - x0), Math.max(MIN_DY, y1 -y0));
		}
		else {
			return new Rectangle(x0, y0, MIN_DX, MIN_DY);
		}
	}

	/**
	 * if true, dont use the value of getRectangle, but show the whole world
	 * @return
	 */
	public boolean isReset() {
		return reset;
	}

	public boolean isDragging() {
		return dragging;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Buttons.LEFT == button) {
			worldTouchDownPos = camera.unproject(new Vector3(screenX, screenY, 0));
			goalX = worldTouchDownPos.x;
			goalY = worldTouchDownPos.y;
			dragging = true;
		}

		if (Buttons.RIGHT == button) {
			goalX = ConwaysGame.WIDTH / 2;
			goalY = ConwaysGame.HEIGHT / 2;
			dragging = true;
		}

		System.out.println("TouchDown:" + screenX + "," + screenY);

		return super.touchDown(screenX, screenY, pointer, button);
	}


}
