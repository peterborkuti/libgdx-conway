package hu.bp.conway.libgdx;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;

public class MouseEvents extends InputAdapter {
	private boolean reset = true;
	private boolean dragging = false;
	public static final int MIN_DX = 10;
	public static final int MIN_DY = 10;
	private int x0 = 0,y0 = 0, x1 = MIN_DX, y1 = MIN_DY;

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
			reset = false;
			x0 = screenX;
			y0 = screenY;
			x1 = x0 + MIN_DX;
			y1 = y1 + MIN_DY;
			dragging = true;
		}
		else {
			reset = true;
		}

		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (Buttons.LEFT == button) {
			x1 = screenX;
			y1 = screenY;
			dragging = false;
		}
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
