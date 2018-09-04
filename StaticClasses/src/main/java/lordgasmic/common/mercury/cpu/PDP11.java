package lordgasmic.common.mercury.cpu;

import java.util.Random;

public class PDP11 implements Runnable {
	
	public volatile boolean running = true; 

	@Override
	public void run() {
		while (running)
			tan(Math.random(), 1000);
	}
	
	private double tan(double n, int count) {
		if (count > 0) {
			count--;
			tan(Math.tan(Math.atan(n)), count);
		}
		
		return n;
	}
	

}
