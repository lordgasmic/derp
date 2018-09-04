package lordgasmic.common.mercury;

public class UHEPRNGImpl {

	// add entryopy
	// hash
	// mash
	// prng.string

	// generate
	// prng.initstate
	// prng.hashString
		
	/**
	 * periodicRehash
	 * addEntropy
	 * prng.addentropy
	 * -hash
	 * --mash
	 * prng.string
	 * -random
	 * --rawprng
	 */
	
	/**
	 * Generate
	 * prng.initState
	 * -mash
	 * prng.hashString - seedkey
	 * -cleanString
	 * -mash
	 * prng(range) -- random
	 * -rawprng
	 */

	public static void main(String[] args) {
		UHEPRNG prng = new NumberHg();

		String key = prng.getKey(UHEPRNG.DEFAULT_KEY_LENGTH);

		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
		System.out.println(prng.generate(10000, key));
	}

}
