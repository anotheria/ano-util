/*
 * Created on 22.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.util;

import java.util.Random;

/**
 * A simple utility for creation of safe passwords.
 */
public class PasswordGenerator {
	
	private final static int rangeBegin[] = {
		33,
		65,
		97, 
	};
	
	private final static int rangeEnd[] = {
		59,
		90,
		122, 
	};

	/**
	 * Internal holder for useable characters.
	 */
	private final static char CHARS[];

	static{
		int sum = 0;
		for (int i=0; i<rangeBegin.length; i++)
			sum += rangeEnd[i]-rangeBegin[i]+1;
		
		CHARS = new char[sum];
		
		int a=0;
		for (int t=0; t<rangeBegin.length;t++){
			for (int i=rangeBegin[t]; i<=rangeEnd[t]; i++)
				CHARS[a++] = (char)i;				
		}
		
	}
	
	/**
	 * Generates a password of given length.
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
