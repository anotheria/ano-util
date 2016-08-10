package net.anotheria.util;

import java.util.Random;

/**
 * A simple utility for creation of safe passwords.
 *
 * @author another
 * @version $Id: $Id
 */
public class PasswordGenerator {

	private final static int[] RANGE_BEGIN = {
			33,
			65,
			97,
	};

	private final static int[] RANGE_END = {
			59,
			90,
			122,
	};

	/**
	 * Internal holder for useable characters.
	 */
	private final static char[] CHARS;

	static{
		int sum = 0;
		for (int i=0; i<RANGE_BEGIN.length; i++)
			sum += RANGE_END[i]-RANGE_BEGIN[i]+1;
		
		CHARS = new char[sum];
		
		int a=0;
		for (int t=0; t<RANGE_BEGIN.length;t++){
			for (int i=RANGE_BEGIN[t]; i<=RANGE_END[t]; i++)
				CHARS[a++] = (char)i;				
		}
		
	}
	
	/**
	 * Generates a password of given length.
	 *
	 * @param length the length of the password.
	 * @return a string of the given length.
	 */
	public static String generate(int length){
		Random rnd = new Random();
		rnd.setSeed(System.currentTimeMillis());
		String ret = "";
		for (int i=0;i<length;i++){
			int r = rnd.nextInt(CHARS.length);
			ret += CHARS[r];
		}
		
		return ret;
		
	}

}
