
public class TestPercolation {
	
	public static void main(String... args) {
		TestPercolation tp = new TestPercolation();
		
		tp.onCreate();
		tp.testValidate();
		tp.onFinish();
		
	}

	
	Percolation perc;
	
	public void onCreate() {
		perc = new Percolation(5);
	}
	
	public void onFinish() {
		perc = null;
	}
	
	public void testValidate() {
		perc.isOpen(2, 1);
	}
}
