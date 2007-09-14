package net.anotheria.util;

import java.util.Random;

/**
 * This class is an utility for generation of unique identification codes.
 * @author lrosenberg
 * Created on 06.10.2004
 */
public class IdCodeGenerator {
	public static final int CODE_LENGTH = 10;
	public static final int CODE_START = 97;
	public static final int CODE_END   = 122; 
	
	private static Random random;
	
	static{
		random = new Random(System.currentTimeMillis());
	}
	
	public static String generateCustomCode(char[] chars, int length){
		String ret = "";
		for (int i=0; i<length; i++){
			ret += chars[random.nextInt(chars.length)];
		}
		return ret;
	}
	
	public static String generateCode(int length){
		int interval = CODE_END - CODE_START+1;
		String ret = "";
		for (int i=0; i<length; i++)
			ret += (char)(random.nextInt(interval)+CODE_START);
		return ret;
	}
	
	public static String generateCode(){
		return generateCode(CODE_LENGTH);
	}

}
