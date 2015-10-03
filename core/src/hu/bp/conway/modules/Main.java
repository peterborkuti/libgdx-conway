package hu.bp.conway.modules;

import hu.bp.common.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	static long timer; 
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Universe in = new Universe(25, 0.4f);

		Coordinator c;

		for (int i = 1; i < 3; i++) {
			c = new Coordinator(in, executor, i, 1000);
			long timer = Util.startTimer();
			c.run();
			Util.stopTimer(timer);
		}
		/*
		c = new Coordinator(in, 4);
		startTimer();
		c.run();
		stopTimer();
		*/
		
		Util.shutdownAndAwaitTermination(executor);
	}

}
