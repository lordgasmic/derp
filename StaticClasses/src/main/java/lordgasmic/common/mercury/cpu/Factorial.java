package lordgasmic.common.mercury.cpu;

import java.util.Random;

public class Factorial implements Runnable {

	public volatile boolean running = true;

	@Override
	public void run() {
		Random r = new Random();
		while (running) {
			factorial(5000, 1);
		}
	}

	private double factorial(int i, double sum) {
		if (i > 1) {
			sum *= i;
			i--;
			return factorial(i, sum);
		}

		return sum;
	}

}
