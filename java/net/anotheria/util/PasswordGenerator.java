/*
 * Created on 22.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.util;

import java.util.Random;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PasswordGenerator {
	
	static int rangeBegin[] = {
		33,
		65,
		97, 
	};
	
	static int rangeEnd[] = {
		59,
		90,
		122, 
	};
	
	static char CHARS[];

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
	
	public static void main(String args[]){
		System.out.println(generate(8));
	}
}
