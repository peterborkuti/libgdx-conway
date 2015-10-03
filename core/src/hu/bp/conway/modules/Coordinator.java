package hu.bp.conway.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Coordinator implements Runnable {
	public Universe out;
	private Universe in;
	private ExecutorService executor;
	private List<FutureTask<?>> f;
	private int numOfThreads;
	private int repeat;

	public Coordinator(Universe in, ExecutorService executor, int numOfThreads, int repeat) {
		this.in = new Universe(in);
		out = new Universe(in.n, false);
		f = new ArrayList<FutureTask<?>>();
		this.numOfThreads = numOfThreads;
		this.repeat = repeat;
		this.executor = executor;
	}

	public void oneStep() {
		f.clear();

		int len = in.n / numOfThreads;

		for (int c = 0; c < in.n - 1; c += len) {
			f.add(new FutureTask<Void>(
				new Walker(in, out, 0, c, in.n, len), null));
		}

		for (FutureTask<?> ff: f) {
			executor.execute(ff);
		}

		for (FutureTask<?> ff: f) {
			try {
				ff.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	public void swap() {
		Universe tmp;
		tmp = in;
		in = out;
		out = tmp;
	}

	@Override
	public void run() {

		System.out.println("\n" + in.toString());
		for (int i = 0; i < repeat; i ++) {

			oneStep();
			System.out.println("\n" + out.toString());

			if (in.equals(out)) {
				break;
			}

			swap();

		}
	}

	private void print(Universe u) {
		System.out.println();
		System.out.println(u.toString().replaceAll(",", "\n").replaceAll(" ", "-"));
	}

}
