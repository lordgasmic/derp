package lordgasmic.common.mercury;

public class NumberHg extends UHEPRNG {

	@Override
	public char getRandomChar() {
		return (char)(48 + random(10));
	}
	
}
