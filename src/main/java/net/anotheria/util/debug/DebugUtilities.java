package net.anotheria.util.debug;

import net.anotheria.util.NumberUtils;

import java.util.List;

/**
 * Some utilities for debugging, feel free to add yours.
 *
 * @author lrosenberg
 * Created on 13.05.2004
 * @version $Id: $Id
 */
public class DebugUtilities {
	/**
	 * Prints out an array to the stdout.
	 *
	 * @param arr a {@link java.lang.Object} object.
	 */
	public static final void printArray(Object... arr){
		for (int i=0; i<arr.length; i++){
			System.out.println((i + 1) + " " + arr[i]);
		}
	}
	/**
	 * Prints out a list to the stdout.
	 *
	 * @param l a {@link java.util.List} object.
	 */
	public static final void printList(List<?>  l){
		System.out.println(listToString(l));
	}
	
	/**
	 * returns the string-representation of a list
	 *
	 * @param l a {@link java.util.List} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String listToString(List<?> l) {
		StringBuilder result = new StringBuilder();
		if( l!= null) {
			if(!l.isEmpty()) {
				for (int i=0; i<l.size(); i++){
					result.append(i + 1).append(' ').append(l.get(i));
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
	 *
	 * @param l a {@link java.util.List} object.
	 */
	public static final void printBinaryList(List<?>  l){
		for (int i=0; i<l.size(); i++){
			System.out.println((i + 1) + "\t" + Long.toBinaryString(Long.parseLong(l.get(i).toString())));
		}
	}

	/**
	 * <p>main.</p>
	 *
	 * @param a a {@link java.lang.String} object.
	 */
	public static void main(String... a){
		System.out.println("1372299300 "+ NumberUtils.makeISO8601TimestampString(1372299300));
	}
}
  
