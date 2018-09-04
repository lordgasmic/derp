package lordgasmic.common.util;
import java.util.Enumeration;
import java.util.List;


public class LgcEnumeration<T> implements Enumeration<T>{
	
	private int counter;
	private List<T> type;
	
	public LgcEnumeration(List<T> type) {
		this.type = type;
		counter = 0;
	}

	@Override
	public boolean hasMoreElements() {
		if (type.size() > counter) {
			return true;
		}
		
		return false;
	}

	@Override
	public T nextElement() {
		T retStream = null;
		if (hasMoreElements()) {
			retStream = type.get(counter);
			counter++;
		}
		return retStream;
	}

}
