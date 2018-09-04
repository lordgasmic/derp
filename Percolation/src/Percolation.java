/**
 * @author Nick Wellman
 * Date: 2014-02-06
 */
public class Percolation {
	
	private final int N; // the true size of the grid
	private final int n; // the fake size of the grid that we must maintain becuase of the silly restrictions where we cant assume 0 and n-1 for the size of the grid
	private boolean[][] state; // the state of the sites, open=true, blocked=false 
	private Coord[][] grid; // the grid that holds our connections to other sites
	private int count;
	
	public static void main(String... args) {
		Percolation p = new Percolation(1);
		
		System.out.println(p.count());
	}

	public Percolation(int n) {
		
		if (n < 1) {
			throw new IndexOutOfBoundsException(String.format("Input for n should be greater than one.  Supplied value: %s", n));
		}
		
		this.n = n;
		N = n + 1;

		state = new boolean[N][N];
		grid = new Coord[N][N];
		
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < N; j++) {
				grid[i][j] = new Coord(i,j);
				state[i][j] = false;
			}
		}
		
		count = n*n; 
	}
	
	public void open(int i, int j) {
		validate(i, j);
		
		state[i][j] = true;
		union(i,j);
	}
	
	public boolean isOpen(int i, int j) {
		validate(i, j);
		
		return state[i][j];
	}
	
	public boolean isFull(int i, int j) {
		validate(i, j);	
		
		if (grid[i][j].getI() == 0) {
			return true;
		}
		
		return false;
	}
	
	public boolean percolates() {
		for (int j = 1; j < N; j++) {
			if (isFull(N, j)) {  // find the first row on the bottom that is full. by definition, it should also percolate
				return true;
			}
		}
		
		return false;
	}
	
	private void union(int i, int j) {
		validate(i,j);
		
		im1(i,j);
		int ip1 = i + 1;
		int jm1 = j - 1;
		int jp1 = j + 1;
		
	}
	
	private void im1(int i, int j) {
		int im1 = i - 1;
		
		if (im1 == 0) { // checking for edge case where the value next to our site outside of the grid
			grid[i][j].setParent(new Coord(0,0)); // if it is in the top row, it is always open to the top and we set the parent to the magical 0 root
			grid[i][j].addToSize(1);
		}
		else { // we are in the grid, hook stuff up
			if (!state[im1][j]) {
				return; // the neighboring site is not open
			}
			
			// the neighboring site is open, and inside the grid
			Coord cm1 = grid[im1][j];
			Coord c = grid[i][j];
			
			if (connected(cm1,c)) {
				return; // already connected, dont bother
			}
			
			Coord cm1p = find(cm1.getI(), cm1.getJ());
			Coord cp = find(c.getI(), c.getJ());
			
			if (cm1p.getSize() <= cp.getSize()) {
				cm1p.setParent(cp);
				grid[im1][j] = grid[i][j]; // set the smaller root to the larger parent
			}
			else {
				grid[i][j] = grid[im1][j];
			}
			
			int sim1 = cm1.getSize();
			int si = c.getSize();
			cm1.addToSize(si);
			c.addToSize(sim1);
			
			grid[im1][j] = cm1;
			grid[i][j] = c;
		}
			
	}
	
	/**
	 * Find the parent for the coord
	 */
	private Coord find(int i, int j) {
		Coord c = new Coord(i,j);
		
		while (c != grid[i][j]){
			c = grid[i][j].getParent();
		}
			
		return c;
	}
	
	private boolean connected(Coord p, Coord q) {
		return find(p.getI(), p.getJ()) == find(q.getI(), q.getJ());
	}
	
	private void validate(int i, int j) {
		if (i <= 0 || i >= N) {
			throw new IndexOutOfBoundsException(String.format("Input of %s is out of bounds for i.  Value should be between 1 and %s, inclusive", i, n));
		}
		if (j <= 0 || j >= N) {
			throw new IndexOutOfBoundsException(String.format("Input of %s is out of bounds for j.  Value should be between 1 and %s, inclusive", j, n));
		}
	}
	
	private int count() {
		return count;
	}
	
	private class Coord {
		int i,j,size;
		
		private Coord left;
		private Coord right;
		private Coord top;
		private Coord bottom;
		private Coord parent;
		
		public Coord(int i, int j) {
			this.i = i;
			this.j = j;
			size = 1;
		}
		
		public int getI() {
			return i;
		}
		
		public int getJ() {
			return j;
		}
		
		public void addToSize(int p) {
			size += p;
		}
		
		public int getSize() {
			return size;
		}
		
		private void setLeft(Coord c) {
			left = c;
		}
		private Coord getLeft() {
			return left;
		}
		private void setRight(Coord c) {
			right = c;
		}
		private Coord getRight() {
			return right;
		}
		private void setTop(Coord c) {
			top = c;
		}
		private Coord getTop() {
			return top;
		}
		private void setBottom(Coord c) {
			bottom = c;
		}
		private Coord getBottom() {
			return bottom;
		}
		private void setParent(Coord c) {
			parent = c;
		}
		private Coord getParent() {
			return parent;
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof Coord)) {
				return false;
			}
			
			Coord c = (Coord) o;
			
			if (this.i == c.getI() && this.j ==c.getJ()) {
				return true;
			}
			
			return false;
		}
	}

}
