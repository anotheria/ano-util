package net.anotheria.util;

import java.util.Random;

/**
 * This class is an utility for generation of unique identification codes.
 * @author lrosenberg
 * Created on 06.10.2004
 */
public final class IdCodeGenerator {
	/**
	 * The default code length.
	 */
	public static final int CODE_LENGTH = 10;
	/**
	 * The starting ascii character used in code generation.
	 */
	public static final int CODE_START = 97;
	/**
	 * The ending ascii character used in code generation.
	 */
	public static final int CODE_END   = 122; 
	
	/**
	 * The random numbers generator.
	 */
	private static final Random random = new Random(System.currentTimeMillis());;
	
	/**
	 * Generates a code of given length from supplied chars.
	 * @param chars
	 * @param length
	 * @return
	 */
	public static String generateCustomCode(char[] chars, int length){
		String ret = "";
		for (int i=0; i<length; i++){
			ret += chars[random.nextInt(chars.length)];
		}
		return ret;
	}
	
	/**
	 * Generates a code of given length.
	 * @param length
	 * @return
	 */
	public static String generateCode(int length){
		int interval = CODE_END - CODE_START+1;
		String ret = "";
		for (int i=0; i<length; i++)
			ret += (char)(random.nextInt(interval)+CODE_START);
		return ret;
	}
	
	/**
	 * Generates a code with default length (CODE_LENGTH).
	 * @return
	 */
	public static String generateCode(){
		return generateCode(CODE_LENGTH);
	}
	
	/**
	 * Prevent instantiation.
	 */
	private IdCodeGenerator(){
		
	}

}
