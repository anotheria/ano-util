package net.anotheria.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An utility for handling of arrays.
 * @author denis
 *
 */
public class ArrayUtils {

	/**
	 * Merges two arrays in a third array.
	 * @param <T>
	 * @param firstArray
	 * @param secondArray
	 * @return
	 */
	public static <T> T[] mergeArrays(T[] firstArray, T[] secondArray) {
		T[] ret = Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
		for(int i = firstArray.length; i < ret.length; i++)
			ret[i] = secondArray[i - firstArray.length];
		return ret;
	}
	
	public static int[] mergeArrays(int[] firstArray, int[] secondArray) {
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
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> List<T> asList(T... array){
		return Arrays.asList(array);
	}
	
	public static List<Integer> asList(int[] array){
		List<Integer> ret = new ArrayList<Integer>(array.length);
		for(int a:array)
			ret.add(a);
		return ret;
	}
	
	public static List<Long> asList(long[] array){
		List<Long> ret = new ArrayList<Long>(array.length);
		for(long a:array)
			ret.add(a);
		return ret;
	}
	
	public static List<Float> asList(float[] array){
		List<Float> ret = new ArrayList<Float>(array.length);
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
	
	public static int[] parseStringArray(String[] stringArray){
		int[] ret = new int[stringArray.length];
		for(int i = 0; i < stringArray.length; i++)
			ret[i] = Integer.parseInt(stringArray[i]);
		return ret;
	}
	
	/**
	 * Converts an Objects list to the list of Strings by calling toString method of each Object from incoming list.
	 * @Deprecated. Use StringUtils.toStringList() instead.
	 *  
	 * @param list incoming list of Objects
	 * @return list where each incoming object converted to string.
	 */
	@Deprecated
	public static List<String> toStringList(List<?> list){
		List<String> ret = new ArrayList<String>(list.size());
		for(Object el: list)
			ret.add(el.toString());
		return ret;
	}
	
	public static List<String> toStringList(int... array){
		List<String> ret = new ArrayList<String>(array.length);
		for(int el: array)
			ret.add(String.valueOf(el));
		return ret;
	}
	
	public static <T> List<String> toStringList(T... array){
		List<String> ret = new ArrayList<String>(array.length);
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
	 * @param array1
	 * @param array2
	 * @return the array of elements that the array1 and the array2 have in common
	 */
	public static int[] intersection(int[] array1, int[] array2){
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
	
	@SuppressWarnings("unchecked")
	public static <T> T[] createInstance(T[] a, int size){
		 return (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
	}
	
	public static final void main(String args[]){
		int[] a1 = {0,2,3,6,8,10,11,12,13,14,34};
		int[] a2 = {1,2,3,5,6,9,10,13,53};
		System.out.println("Array1: " + Arrays.toString(a1));
		System.out.println("Array2: " + Arrays.toString(a2));
		System.out.println("Intersection Array1 and Array1: " + Arrays.toString(intersection(a1, a1)));
		System.out.println("Intersection Array1 and Array2: " + Arrays.toString(intersection(a1, a2)));
		System.out.println("Intersection Array2 and Array1: " + Arrays.toString(intersection(a2, a1)));
		
		int[] b1 = {};
		int[] b2 = {1,2,3,5,6,9,10,13,53};
		System.out.println("Array b1: " + Arrays.toString(b1));
		System.out.println("Array b2: " + Arrays.toString(b2));
		System.out.println("Intersection Array b1 and Array b1: " + Arrays.toString(intersection(b1, b1)));
		System.out.println("Intersection Array b1 and Array b2: " + Arrays.toString(intersection(b1, b2)));
	}
	
}
