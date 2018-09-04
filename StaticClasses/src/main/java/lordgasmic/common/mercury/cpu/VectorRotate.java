package lordgasmic.common.mercury.cpu;

public class VectorRotate implements Runnable {

	public volatile boolean running = true; 
	
	@Override
	public void run() {
		while (running)
			rotate(Math.random());
	}
	
	public double rotate(double n) {
		double x,y;

		x = Math.random();
		y = Math.random();
		
	    double dx = (x * Math.cos(n)) - (y * Math.sin(n));
	    double dy = (x * Math.sin(n)) + (y * Math.cos(n));
	    
	    return dy/dx;
	}

	
}
