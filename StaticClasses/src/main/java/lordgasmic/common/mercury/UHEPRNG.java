package lordgasmic.common.mercury;

import java.util.Date;

/**
 * TODO: Create threaded entropy that just spins making up new random entropy to
 * use
 * 
 * @author nwellman
 * 
 */
public abstract class UHEPRNG {

	int o = 48;
	long c = 1;
	int p = o;
	double[] s = new double[o];
	int k = 0;
	
	public static int DEFAULT_KEY_LENGTH = 256;
	
	// when our "uheprng" is initially invoked our PRNG state is initialized
	// from the
	// browser's own local PRNG. This is okay since although its generator might
	// not
	// be wonderful, it's useful for establishing large startup entropy for our
	// usage.
	public UHEPRNG() {
		for (int i = 0; i < o; i++)
			s[i] = mash(Math.random());
	}
	
	/**
	 * Returns a psudo random number in the range of [0, range)
	 * @param range
	 * @return
	 */
	public int generate(int range, String key) {
		initState();
		hashString(key);
		return random(range);
	}
	
	public String getKey(int length)	{
		addEntropy(this.toString());
		return string(length);
	}
	
	public abstract char getRandomChar();

	// this PRIVATE (internal access only) function is the heart of the
	// multiply-with-carry
	// (MWC) PRNG algorithm. When called it returns a pseudo-random number in
	// the form of a
	// 32-bit JavaScript fraction (0.0 to <1.0) it is a PRIVATE function used by
	// the default
	// [0-1] return function, and by the random 'string(n)' function which
	// returns 'n'
	// characters from 33 to 126.
	private double rawprng() {
		if (++p >= o)
			p = 0;
		double t = 1768863 * s[p] + c * 2.3283064365386963e-10; // 2^-32
		return s[p] = t - (c = (long) t);
	}

	// this EXPORTED function is the default function returned by this library.
	// The values returned are integers in the range from 0 to range-1. We first
	// obtain two 32-bit fractions (from rawprng) to synthesize a single high
	// resolution 53-bit prng (0 to <1), then we multiply this by the caller's
	// "range" param and take the "floor" to return a equally probable integer.
	protected int random(int range) {
		long l = (long) (rawprng() * 0x200000);
		double floor = Math.floor(range * (rawprng() + (l) * 1.1102230246251565e-16)); // 2^-53
		return (int)floor;
	}

	// this EXPORTED function 'string(n)' returns a pseudo-random string of
	// 'n' printable characters ranging from chr(33) to chr(126) inclusive.
	private String string(int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++)
			sb.append((char) (33 + random(94)));
		return sb.toString();
	}

	// this PRIVATE "hash" function is used to evolve the generator's internal
	// entropy state. It is also called by the EXPORTED addEntropy() function
	// which is used to pour entropy into the PRNG.
	private void hash(String arguments) {
		for (int i = 0; i < arguments.length(); i++) {
			for (int j = 0; j < o; j++) {
				s[j] -= mash(arguments.charAt(i));
				if (s[j] < 0)
					s[j] += 1;
			}
		}
	}

	// this EXPORTED "clean string" function removes leading and trailing spaces
	// and non-printing
	// control characters, including any embedded carriage-return (CR) and
	// line-feed (LF) characters,
	// from any string it is handed. this is also used by the 'hashstring'
	// function (below) to help
	// users always obtain the same EFFECTIVE uheprng seeding key.
	private String cleanString(String inStr) {
		inStr = inStr.trim();
		inStr = inStr.replace("\r", "");
		inStr = inStr.replace("\n", "");
		return inStr;
	}

	// this EXPORTED "hash string" function hashes the provided character string
	// after first removing
	// any leading or trailing spaces and ignoring any embedded carriage returns
	// (CR) or Line Feeds (LF)
	private void hashString(String inStr) {
		inStr = cleanString(inStr);
		mash(inStr); // use the string to evolve the 'mash' state
		for (int i = 0; i < inStr.length(); i++) { // scan through the characters in our string
			k = inStr.charAt(i); // get the character code at the location
			for (int j = 0; j < o; j++) { // "mash" it into the UHEPRNG state
				s[j] -= mash(k);
				if (s[j] < 0)
					s[j] += 1;
			}
		}
	}

	// this handy exported function is used to add entropy to our uheprng at any
	// time
	private void addEntropy(Object... args) {
		hash((k++) + (new Date().getTime()) + join(args) + Math.random());
	}

	private String join(Object[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++)
			sb.append(args[i].toString());
		return sb.toString();
	}

	// if we want to provide a deterministic startup context for our PRNG,
	// but without directly setting the internal state variables, this allows
	// us to initialize the mash hash and PRNG's internal state before providing
	// some hashing input
	private void initState() {
		mash("");
		for (int i = 0; i < o; i++)
			s[i] = mash(' ');
		c = 1;
		p = o;
	}

	/*
	 * ==========================================================================
	 * == This is based upon Johannes Baagoe's carefully designed and efficient
	 * hash function for use with JavaScript. It has a proven "avalanche" effect
	 * such that every bit of the input affects every bit of the output 50% of
	 * the time, which is good. See:
	 * http://baagoe.com/en/RandomMusings/hash/avalanche.xhtml
	 * ==================
	 * ==========================================================
	 */
	private double mash(Object o) {
		double n = 0xefc8249d;
		if (o != null) {
			String data = o.toString();
			for (int i = 0; i < data.length(); i++) {
				n += data.charAt(i);
				double h = 0.02519603282416938 * n;
				n = (long) h >>> 0;
				h -= n;
				h *= n;
				n = (long) h >>> 0;
				h -= n;
				n += h * 0x100000000L; // 2^32
			}

			long a = (long) n >>> 0;
			double b = a * 2.3283064365386963e-10; // 2^-32
			return b;
		} else
			return 0xefc8249d;
	}

}
