package net.anotheria.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * An util for handling of arrays.
 * 
 * @author denis
 *
 */
public class ArrayUtils {

	/**
	 * Merges two arrays in a third array.
	 */
	public static <T> T[] mergeArrays(T[] firstArray, T... secondArray) {
		T[] ret = Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
		System.arraycopy(secondArray, firstArray.length - firstArray.length, ret, firstArray.length, ret.length - firstArray.length);
		return ret;
	}
	
	public static int[] mergeArrays(int[] firstArray, int... secondArray) {
		return toIntArray(mergeArrays(toIntegerArray(firstArray), toIntegerArray(secondArray)));
	}
	
	public static <T> T[] addToArray(T[] array, T element){
		T[] ret = Arrays.copyOf(array, array.length + 1);
		ret[array.length] = element;
		return ret; 
	}
	
	public static int[] addToArray(int[] array, int element){
		int[] ret = Arrays.copyOf(array, array.length + 1);
		ret[array.length] = element;
		return ret;
	}
	
	public static <T> T[] subArray(T[] src, int from, int to){
		return Arrays.copyOfRange(src, from, to);
	}
	
	public static int[] subArray(int[] src, int from, int to){
		return Arrays.copyOfRange(src, from, to);
	}
	
	/**
	 * Returns the List that contains elements from array.
	 * NOTE: vararg of parameterized types (here @param array) for primitive arrays doesn't work properly.
	 * This utility have to be accomplished with asList methods for each primitive arrays!!!
	 * 
	 */
	public static <T> Iterable<T> asList(T... array){
		return Arrays.asList(array);
	}
	
	public static Iterable<Integer> asList(int[] array){
		List<Integer> ret = new ArrayList<>(array.length);
		for(int a:array)
			ret.add(a);
		return ret;
	}
	
	public static Iterable<Long> asList(long[] array){
		List<Long> ret = new ArrayList<>(array.length);
		for(long a:array)
			ret.add(a);
		return ret;
	}
	
	public static Iterable<Float> asList(float[] array){
		List<Float> ret = new ArrayList<>(array.length);
		for(float a:array)
			ret.add(a);
		return ret;
	}
	
	public static int[] asArray(List<Integer> integerList){
		int[] ret = new int[integerList.size()];
		for(int i = 0; i < integerList.size(); i++)
			ret[i] = integerList.get(i);
		return ret;
	}
	
	public static int[] toIntArray(Integer... integerArray){
		int[] ret = new int[integerArray.length];
		for(int i = 0; i < integerArray.length; i++)
			ret[i] = integerArray[i];
		return ret;
	}
	
	public static Integer[] toIntegerArray(int... intArray){
		Integer[] ret = new Integer[intArray.length];
		for(int i = 0; i < intArray.length; i++)
			ret[i] = intArray[i];
		return ret;
	}
	
	public static int[] parseStringListAsInt(List<String> stringList){
		int[] ret = new int[stringList.size()];
		for(int i = 0; i < stringList.size(); i++)
			ret[i] = Integer.parseInt(stringList.get(i));
		return ret;
	}
	
	public static int[] parseStringArray(String... stringArray){
		int[] ret = new int[stringArray.length];
		for(int i = 0; i < stringArray.length; i++)
			ret[i] = Integer.parseInt(stringArray[i]);
		return ret;
	}
	
	/**
	 * Converts an Objects list to the list of Strings by calling toString method of each Object from incoming list.
	 * @deprecated Use StringUtils.toStringList() instead.
	 *  
	 * @param list incoming list of Objects
	 * @return list where each incoming object converted to string.
	 */
	@Deprecated
	public static List<String> toStringList(Collection<?> list){
		List<String> ret = new ArrayList<>(list.size());
		for(Object el: list)
			ret.add(el.toString());
		return ret;
	}
	
	public static List<String> toStringList(int... array){
		List<String> ret = new ArrayList<>(array.length);
		for(int el: array)
			ret.add(String.valueOf(el));
		return ret;
	}
	
	public static <T> List<String> toStringList(T... array){
		List<String> ret = new ArrayList<>(array.length);
		for(T el: array)
			ret.add(String.valueOf(el));
		return ret;
	}
	
	public static <T> String toString(int... array){
		return toString(toIntegerArray(array));
	}
	public static <T> String toString(T... array){
		if(array.length == 0)
			return "[]";
		String ret = "[";
		ret += array[0];
		for(int i = 1; i < array.length; i++)
			ret += "," + array[i];
		ret += "]";
		return ret;
	}
	
	public static boolean contains(int[] array, int element){
		for(int i:array)
			if(i == element)
				return true;
		return false;
	}
	public static <T> boolean contains(T[] array, T element){
		for(T a:array)
			if(a.equals(element))
				return true;
		return false;
	}
	
	/**
	 * Finds all elements that the array1 and the array2 have in common. 
	 * The array1 and the array2 must be sorted ascent. 
	 * @return the array of elements that the array1 and the array2 have in common
	 */
	public static int[] intersection(int[] array1, int... array2){
		if(array1.length == 0 || array2.length == 0)
			return new int[0];
		if(!isSorted(array1) || !isSorted(array2))
			throw new RuntimeException("Arrays must be sorted: array1 " + toString(array1) + ", array2 " + toString(array2));
		int position1 = 0;
		int position2 = 0;
		
		int[] buff = new int[Math.min(array1.length, array2.length)];
		int positionBuff = 0;
		
		while(position1 < array1.length && position2 < array2.length){
			int a1 = array1[position1];
			int a2 = array2[position2];
			if(a1 == a2) {
				buff[positionBuff] = a1;
				position1++;
				position2++;
				positionBuff++;
			} else if(a1 > a2) {
				position2++;
			} else {
				position1++;
			}
		}
		int[] ret = new int[positionBuff];
		System.arraycopy(buff, 0, ret, 0, positionBuff);
		return ret;
	}

	public static boolean isSorted(boolean desc, int... array){
		for(int i = 0; i < array.length - 1; i++)
			if((array[i+1] - array[i]) * (desc? -1: 1) < 0)
				return false;
		return true;
	}
	
	public static boolean isSorted(int... array){
		return isSorted(false, array);
	}


	public static <T> T[] createInstance(T[] a, int size){
		 return (T[]) Array.newInstance(a.getClass().getComponentType(), size);
	}
	
}
