package lordgasmic.common.mercury;

public class PrintableHg extends UHEPRNG {

	@Override
	public char getRandomChar() {
		return (char)(33 + random(94));
	}

}
