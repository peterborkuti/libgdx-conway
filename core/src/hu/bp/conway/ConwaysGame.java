package hu.bp.conway;

import hu.bp.common.Util;
import hu.bp.conway.libgdx.Drawer;
import hu.bp.conway.libgdx.MouseEvents;
import hu.bp.conway.modules.Coordinator;
import hu.bp.conway.modules.Universe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ConwaysGame extends ApplicationAdapter {
	static final int NUM_OF_THREADS = 4;
	public static final int WIDTH = 20000;
	public static final int HEIGHT = 20000;

	public static final int UNIVERSE_N = Math.min(WIDTH, HEIGHT) / 10;

	OrthographicCamera camera;
	Coordinator coordinator;
	ExecutorService executor;
	Texture img;
	ShapeRenderer renderer;
	Universe universe;
	MouseEvents mouse = new MouseEvents();
	private boolean camWasReset = false;
	private boolean camWasSet = false;

	private void resetCamera() {
		camera = new OrthographicCamera(WIDTH, HEIGHT);

		camera.translate(WIDTH /2, HEIGHT / 2);
	}

	@Override
	public void create() {
		resetCamera();

		renderer = new ShapeRenderer();
		universe = new Universe(UNIVERSE_N, 0.3f);

		executor = Executors.newFixedThreadPool(NUM_OF_THREADS);
		coordinator = new Coordinator(universe, executor, NUM_OF_THREADS, 1);

		Gdx.input.setInputProcessor(mouse);
	}

	@Override
	public void dispose() {
		Util.shutdownAndAwaitTermination(executor);
		super.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (mouse.isReset() && !camWasReset) {
			resetCamera();
			camWasReset = true;
		}

		if (!mouse.isReset()) {
			camWasReset = false;
		}

		if (!mouse.isReset() && !mouse.isDragging() && !camWasSet) {
			Rectangle mr = mouse.getRectangle();
			Vector3 screenNewOrigin = new Vector3(mr.x + mr.width /2, mr.y + mr.height /2, 0);
			Vector3 newOrigin = camera.unproject(screenNewOrigin);
			float newZoom = camera.zoom * (mr.width / Gdx.graphics.getWidth());
			System.out.println("zoom:" + newZoom);
			System.out.println("origin:" + newOrigin);
			camera.position.set(newOrigin);
			camera.zoom = camera.zoom * (mr.width / Gdx.graphics.getWidth());
			camWasSet = true;
		}

		if (mouse.isDragging()) {
			camWasSet = false;
		}

		camera.update();
		renderer.setProjectionMatrix(camera.combined);

		Drawer.draw(camera, renderer, coordinator.out);

		if (mouse.isDragging()) {
			Drawer.draw(camera, renderer, mouse.getRectangle());
		}

		coordinator.oneStep();
		coordinator.swap();
	}

	@Override
	public void resize(int width, int height) {
	}



}
