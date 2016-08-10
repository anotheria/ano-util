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
 * @version $Id: $Id
 */
public class ArrayUtils {

	/**
	 * Merges two arrays in a third array.
	 *
	 * @param firstArray an array of T objects.
	 * @param secondArray a T object.
	 * @return an array of T objects.
	 */
	public static <T> T[] mergeArrays(T[] firstArray, T... secondArray) {
		T[] ret = Arrays.copyOf(firstArray, firstArray.length + secondArray.length);
		System.arraycopy(secondArray, firstArray.length - firstArray.length, ret, firstArray.length, ret.length - firstArray.length);
		return ret;
	}
	
	/**
	 * <p>mergeArrays.</p>
	 *
	 * @param firstArray an array of int.
	 * @param secondArray a int.
	 * @return an array of int.
	 */
	public static int[] mergeArrays(int[] firstArray, int... secondArray) {
		return toIntArray(mergeArrays(toIntegerArray(firstArray), toIntegerArray(secondArray)));
	}
	
	/**
	 * <p>addToArray.</p>
	 *
	 * @param array an array of T objects.
	 * @param element a T object.
	 * @param <T> a T object.
	 * @return an array of T objects.
	 */
	public static <T> T[] addToArray(T[] array, T element){
		T[] ret = Arrays.copyOf(array, array.length + 1);
		ret[array.length] = element;
		return ret; 
	}
	
	/**
	 * <p>addToArray.</p>
	 *
	 * @param array an array of int.
	 * @param element a int.
	 * @return an array of int.
	 */
	public static int[] addToArray(int[] array, int element){
		int[] ret = Arrays.copyOf(array, array.length + 1);
		ret[array.length] = element;
		return ret;
	}
	
	/**
	 * <p>subArray.</p>
	 *
	 * @param src an array of T objects.
	 * @param from a int.
	 * @param to a int.
	 * @param <T> a T object.
	 * @return an array of T objects.
	 */
	public static <T> T[] subArray(T[] src, int from, int to){
		return Arrays.copyOfRange(src, from, to);
	}
	
	/**
	 * <p>subArray.</p>
	 *
	 * @param src an array of int.
	 * @param from a int.
	 * @param to a int.
	 * @return an array of int.
	 */
	public static int[] subArray(int[] src, int from, int to){
		return Arrays.copyOfRange(src, from, to);
	}
	
	/**
	 * Returns the List that contains elements from array.
	 * NOTE: vararg of parameterized types (here @param array) for primitive arrays doesn't work properly.
	 * This utility have to be accomplished with asList methods for each primitive arrays!!!
	 *
	 * @param array a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static <T> Iterable<T> asList(T... array){
		return Arrays.asList(array);
	}
	
	/**
	 * <p>asList.</p>
	 *
	 * @param array an array of int.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Integer> asList(int[] array){
		List<Integer> ret = new ArrayList<>(array.length);
		for(int a:array)
			ret.add(a);
		return ret;
	}
	
	/**
	 * <p>asList.</p>
	 *
	 * @param array an array of long.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Long> asList(long[] array){
		List<Long> ret = new ArrayList<>(array.length);
		for(long a:array)
			ret.add(a);
		return ret;
	}
	
	/**
	 * <p>asList.</p>
	 *
	 * @param array an array of float.
	 * @return a {@link java.lang.Iterable} object.
	 */
	public static Iterable<Float> asList(float[] array){
		List<Float> ret = new ArrayList<>(array.length);
		for(float a:array)
			ret.add(a);
		return ret;
	}
	
	/**
	 * <p>asArray.</p>
	 *
	 * @param integerList a {@link java.util.List} object.
	 * @return an array of int.
	 */
	public static int[] asArray(List<Integer> integerList){
		int[] ret = new int[integerList.size()];
		for(int i = 0; i < integerList.size(); i++)
			ret[i] = integerList.get(i);
		return ret;
	}
	
	/**
	 * <p>toIntArray.</p>
	 *
	 * @param integerArray a {@link java.lang.Integer} object.
	 * @return an array of int.
	 */
	public static int[] toIntArray(Integer... integerArray){
		int[] ret = new int[integerArray.length];
		for(int i = 0; i < integerArray.length; i++)
			ret[i] = integerArray[i];
		return ret;
	}
	
	/**
	 * <p>toIntegerArray.</p>
	 *
	 * @param intArray a int.
	 * @return an array of {@link java.lang.Integer} objects.
	 */
	public static Integer[] toIntegerArray(int... intArray){
		Integer[] ret = new Integer[intArray.length];
		for(int i = 0; i < intArray.length; i++)
			ret[i] = intArray[i];
		return ret;
	}
	
	/**
	 * <p>parseStringListAsInt.</p>
	 *
	 * @param stringList a {@link java.util.List} object.
	 * @return an array of int.
	 */
	public static int[] parseStringListAsInt(List<String> stringList){
		int[] ret = new int[stringList.size()];
		for(int i = 0; i < stringList.size(); i++)
			ret[i] = Integer.parseInt(stringList.get(i));
		return ret;
	}
	
	/**
	 * <p>parseStringArray.</p>
	 *
	 * @param stringArray a {@link java.lang.String} object.
	 * @return an array of int.
	 */
	public static int[] parseStringArray(String... stringArray){
		int[] ret = new int[stringArray.length];
		for(int i = 0; i < stringArray.length; i++)
			ret[i] = Integer.parseInt(stringArray[i]);
		return ret;
	}
	
	/**
	 * Converts an Objects list to the list of Strings by calling toString method of each Object from incoming list.
	 *
	 * @deprecated Use StringUtils.toStringList() instead.
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
	
	/**
	 * <p>toStringList.</p>
	 *
	 * @param array a int.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> toStringList(int... array){
		List<String> ret = new ArrayList<>(array.length);
		for(int el: array)
			ret.add(String.valueOf(el));
		return ret;
	}
	
	/**
	 * <p>toStringList.</p>
	 *
	 * @param array a T object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T> List<String> toStringList(T... array){
		List<String> ret = new ArrayList<>(array.length);
		for(T el: array)
			ret.add(String.valueOf(el));
		return ret;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @param array a int.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public static <T> String toString(int... array){
		return toString(toIntegerArray(array));
	}
	/**
	 * <p>toString.</p>
	 *
	 * @param array a T object.
	 * @param <T> a T object.
	 * @return a {@link java.lang.String} object.
	 */
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
	
	/**
	 * <p>contains.</p>
	 *
	 * @param array an array of int.
	 * @param element a int.
	 * @return a boolean.
	 */
	public static boolean contains(int[] array, int element){
		for(int i:array)
			if(i == element)
				return true;
		return false;
	}
	/**
	 * <p>contains.</p>
	 *
	 * @param array an array of T objects.
	 * @param element a T object.
	 * @param <T> a T object.
	 * @return a boolean.
	 */
	public static <T> boolean contains(T[] array, T element){
		for(T a:array)
			if(a.equals(element))
				return true;
		return false;
	}
	
	/**
	 * Finds all elements that the array1 and the array2 have in common.
	 * The array1 and the array2 must be sorted ascent.
	 *
	 * @return the array of elements that the array1 and the array2 have in common
	 * @param array1 an array of int.
	 * @param array2 a int.
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

	/**
	 * <p>isSorted.</p>
	 *
	 * @param desc a boolean.
	 * @param array a int.
	 * @return a boolean.
	 */
	public static boolean isSorted(boolean desc, int... array){
		for(int i = 0; i < array.length - 1; i++)
			if((array[i+1] - array[i]) * (desc? -1: 1) < 0)
				return false;
		return true;
	}
	
	/**
	 * <p>isSorted.</p>
	 *
	 * @param array a int.
	 * @return a boolean.
	 */
	public static boolean isSorted(int... array){
		return isSorted(false, array);
	}


	/**
	 * <p>createInstance.</p>
	 *
	 * @param a an array of T objects.
	 * @param size a int.
	 * @param <T> a T object.
	 * @return an array of T objects.
	 */
	public static <T> T[] createInstance(T[] a, int size){
		 return (T[]) Array.newInstance(a.getClass().getComponentType(), size);
	}
	
}
