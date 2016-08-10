package net.anotheria.util;

import net.anotheria.util.sorter.IComparable;

import java.util.Collection;

/**
 * An abstract class which implements useful methods to compare basic types.
 *
 * @author another
 * @version $Id: $Id
 */
public abstract class BasicComparable implements IComparable{
	
	/**
	 * Compares two ints.
	 *
	 * @param a first int to compare.
	 * @param b second int to compare.
	 * @return 0 if a==b, -1 if a&lt;b or +1 if a&gt;b.
	 */
	public static final int compareInt(int a, int b){
		return a < b ? 
			-1 : a == b ? 
			0 : 1;						
	}

	/**
	 * Compares two longs.
	 *
	 * @param a first long to compare.
	 * @param b second long to compare.
	 * @return 0 if a==b, -1 if a&lt;b or +1 if a&gt;b.
	 */
	public static final int compareLong(long a, long b){
		return a < b ? 
			-1 : a == b ? 
			0 : 1;						
	}
	
	/**
	 * <p>compareBool.</p>
	 *
	 * @deprecated use compareBoolean instead.
	 * @param a a boolean.
	 * @param b a boolean.
	 * @return a int.
	 */
	@Deprecated
    public static final int compareBool(boolean a, boolean b){
		return compareBoolean(a,b);
	}

	/**
	 * Compares two booleans.
	 *
	 * @param a first boolean to compare.
	 * @param b second boolean to compare.
	 * @return 0 if a==b, -1 if a &amp; !b or +1 if !a &amp; b.
	 */
	public static final int compareBoolean(boolean a, boolean b){
		return a==b ? 0 :
				a && !b ? -1 : 1;
	}
	
	/**
	 * Compares two floats.
	 *
	 * @param a first float to compare.
	 * @param b second float to compare.
	 * @return 0 if a==b, -1 if a&lt;b or +1 if a&gt;b.
	 */
	public static final int compareFloat(float a, float b){
		return Float.compare(a, b);			
	}
	
	/**
	 * Compares two doubles.
	 *
	 * @param a first double to compare.
	 * @param b second double to compare.
	 * @return 0 if a==b, -1 if a&lt;b or +1 if a&gt;b.
	 */
	public static final int compareDouble(double a, double b){
		return Double.compare(a, b); 
	}

	/**
	 * Compares two strings.
	 *
	 * @param a first String to compare.
	 * @param b second String to compare.
	 * @return  a negative integer, zero, or a positive integer as the
	 *		the second specified String is greater than, equal to, or less
	 *		than first String, ignoring case considerations.
	 */
	public static final int compareString(String a, String b){
		return a == null ? 
				(b == null ? 0 : -1) : 
				(b == null ? 1 : a.compareToIgnoreCase(b));
	}
	
	/**
	 * Compares two List.
	 *
	 * @param a first List to compare.
	 * @param b second List to compare.
	 * @return 0 if a.size==b.size, -1 if a.size&lt;b.size or +1 if a.size&gt;b.size.
	 * @param <T> a T object.
	 */
	public static final <T> int compareList(Collection<T> a, Collection<T> b){
		return a == null ? 
				(b == null ? 0 : -1 ) : 
				(b == null ? 1 : compareInt(a.size(), b.size()));
	}
}
