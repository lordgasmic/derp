package lordgasmic.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import lordgasmic.common.exception.FuckYouAsshatException;

public class Threading implements Runnable {

	private List<Thread> threads;
	private ConcurrentLinkedQueue<? extends Runnable> runnables;

	public Threading() {
		this(1, null);
	}

	/**
	 * @param numberOfThreads
	 * @param runnables
	 */
	public Threading(int numberOfThreads, List<? extends Runnable> runnables) {
		setNumberOfThreads(numberOfThreads);
		setRunnables(runnables);
	}

	public boolean begin() throws InterruptedException, FuckYouAsshatException {
		if (threads == null || runnables == null)
			throw new FuckYouAsshatException("Set your shit, asshat!");

		for (Thread t : threads) {
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}

		return true;
	}

	public void run() {
		try {
			while (!runnables.isEmpty()) {
				Thread t = new Thread(runnables.poll());
				t.start();
				t.join();
			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setRunnables(List<? extends Runnable> runnables) {
		if (runnables == null) {
			this.runnables = new ConcurrentLinkedQueue<>();
		}
		else {
			this.runnables = new ConcurrentLinkedQueue<>(runnables);
		}

	}

	public void setNumberOfThreads(int numberOfThreads) {
		threads = new ArrayList<Thread>();
		for (int i = 0; i < numberOfThreads; i++) {
			threads.add(new Thread(this));
		}
	}
}
