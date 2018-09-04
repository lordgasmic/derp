package lordgasmic.common.mercury;

public class Hg implements Runnable {

	private volatile boolean isRunning = true;
	private volatile String retVal = "init";
	
	public Hg() {
		new Thread(this).start();
		new Thread(this).start();
		new Thread(this).start();
	}

	public void run() {
		while (isRunning) {
			double n = 0xefc8249d;
			String data = retVal.toString();
			for (int i = 0; i < data.length(); i++) {
				n += data.charAt(i);
				double h = 0.02519603282416938 * n;
				n = (long)h >>> 0;
				h -= n;
				h *= n;
				n = (long)h >>> 0;
				h -= n;
				n += h * 0x100000000L; // 2^32
			}

			long a = (long)n >>> 0;
			double b = a * 2.3283064365386963e-10;
			
			retVal = String.valueOf(b); // 2^-32
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void isRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public String getVal() {
		return new String(retVal);
	}
}
