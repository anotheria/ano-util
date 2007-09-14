package net.anotheria.util.debug;

import java.util.List;

/**
 * Some utilities for debugging, feel free to add yours.
 * @author lrosenberg
 * Created on 13.05.2004
 */
public class DebugUtilities {
	/**
	 * Prints out an array to the stdout.
	 * @param arr
	 */
	public static final void printArray(Object[] arr){
		for (int i=0; i<arr.length; i++){
			System.out.println(""+(i+1)+" "+arr[i]);
		}
	}
	/**
	 * Prints out a list to the stdout.
	 * @param l
	 */
	public static final void printList(List  l){
		System.out.println(listToString(l));
	}
	
	/**
	 * returns the string-representation of a list
	 */
	public static String listToString(List l) {
		StringBuffer result = new StringBuffer();
		if( l!= null) {
			if(l.size() > 0) {
				for (int i=0; i<l.size(); i++){
					result.append(""+(i+1)+" "+l.get(i));
				}
			} else {
				return "empty";
			}
		} else {
			return "null";
		}
		return result.toString();
	}
	
	/**
	 * Prints out a list to the stdout.
	 * @param l
	 */
	public static final void printBinaryList(List  l){
		for (int i=0; i<l.size(); i++){
			System.out.println(""+(i+1)+"\t"+Long.toBinaryString(Long.parseLong(l.get(i).toString())));
		}
	}
}
  