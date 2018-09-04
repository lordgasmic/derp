package lordgasmic.common;

import java.util.ArrayList;
import java.util.List;

import lordgasmic.common.exception.FuckYouAsshatException;
import lordgasmic.common.util.Threading;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestThreading {

	private Threading threading;
	
	@Before
	public void onCreate() {
		threading = new Threading();
		
		threading.setRunnables(getThreads());
		threading.setNumberOfThreads(4);
	}

	@After
	public void onFinish() {
		threading = null;
	}

	@Test
	public void testThreading() throws InterruptedException, FuckYouAsshatException {
		threading.begin();
	}
	
	private List<? extends Runnable> getThreads() {
		class TestRunnable implements Runnable {
			
			int i;
			int wait;

			TestRunnable(int i, int wait) {
				this.i = i;
				this.wait = wait;
			}
			
			@Override
			public void run() {
				for (int j = 0; j < 10; j++) {
					System.out.println(i);
					try {
						Thread.sleep(wait);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		List<TestRunnable> retList = new ArrayList<>();
		retList.add(new TestRunnable(1, 1000));
		retList.add(new TestRunnable(2, 1000));
		
		return retList;
	}
}
