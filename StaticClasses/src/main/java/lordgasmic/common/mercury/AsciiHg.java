package lordgasmic.common.mercury;

public class AsciiHg extends UHEPRNG {

	@Override
	public char getRandomChar() {
		return (char)(random(0x100));
	}

}
