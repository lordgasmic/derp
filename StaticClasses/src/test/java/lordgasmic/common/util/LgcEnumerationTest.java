package lordgasmic.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LgcEnumerationTest {

	private LgcEnumeration<String> string;	
	
	@Before
	public void onCreate() {
		string = new LgcEnumeration<>(getStrings());
		assertTrue(string.hasMoreElements());
	}
	
	@After
	public void onFinish() {
		string = null;
	}
	
	@Test
	public void test() {
		while (string.hasMoreElements()) {
			String s = string.nextElement();
			assertNotNull(s);
			System.out.println(s);
		}
		
		assertFalse(string.hasMoreElements());
		assertNull("This should be null", string.nextElement());
	}
	
	private List<String> getStrings() {
		List<String> list = new ArrayList<>();
		
		list.add("1");
		list.add("2");
		list.add("3");
		
		return list;
	}
}
