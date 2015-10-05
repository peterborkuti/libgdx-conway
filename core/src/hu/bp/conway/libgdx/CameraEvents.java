package hu.bp.conway.libgdx;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CameraEvents extends InputAdapter {

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return super.keyUp(keycode);
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
	private int x0 = 0,y0 = 0, x1 = MIN_DX, y1 = MIN_DY;
	private OrthographicCamera camera;


	public CameraEvents(OrthographicCamera camera) {
		this.camera = camera;
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
			x0 = screenX;
			y0 = screenY;
			dragging = true;
			worldDragStartPosition = camera.unproject(screenCoordscamera.position;
		}

		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		dragging = false;
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (dragging) {
			if (screenX > x0) {
				x1 = screenX;
			}
			if (screenY > y0) {
				y1 = screenY;
			}
		}
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}


}
