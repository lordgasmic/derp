import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	
	private Node first;
	private Node last;
	private int size;

	public static void main(String[] args) {
		Deque<String> d = new Deque<>();
		
		d.addFirst("1");
		d.addLast("2");
		d.addFirst("0");
		
		
		System.out.println(d.size());
		for (String s : d) {
			System.out.println(s);
		}
	}
	
	public Deque() {
		first = new Node();
		last = new Node();
		first.next = last;
		size = 0;
	}
	
	public boolean isEmpty() {
		if (size <= 0) {
			return true;
		}
		
		return false;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst(Item item) {
		npe(item);
		
		if (isEmpty()) {
			first.item = item;
		}
		else {
			Node oldFirst = first;
			first.item = item;
			first.next = oldFirst;
		}
		
		size++;
	}
	
	public void addLast(Item item) {
		npe(item);
		
		if (isEmpty()) {
			last.item = item;
		}
		else {
			Node oldLast = last;
			last.item = item;
			oldLast.next = last;
		}
		
		size++;
	}
	
	public Item removeFirst() {
		nsee();
		
		size--;
		return first.item;
	}
	
	public Item removeLast() {
		nsee();
		
		size--;
		return last.item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new DequeIterator<Item>();
	}
	
	private void npe(Item item) {
		if (item == null) {
			throw new NullPointerException("You are not allowed to add a null item.");
		}
	}
	
	private void nsee() {
		if (isEmpty()) {
			throw new NoSuchElementException("You can not remove an item.  The Deque is empty.");
		}
	}
	
	private class Node {
		Item item;
		Node next;
	}
	
	private class DequeIterator<Item> implements Iterator<Item> {
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Item item = (Item) current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("You are not allowed to remove items from the iterator");
		}
		
	}
	
}
