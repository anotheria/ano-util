package net.anotheria.util.sizeof;


/**
 * Sizeof helper for strings.
 * @author lrosenberg
 *
 */
public class StringHelper implements SizeofHelper{
	/**
	 * Returns the size of the internal char array + 3 integer values.
	 */
	@Override public int sizeof(Object o) {
		String s = (String)o;
		return s==null ? 0 : 3*SizeofUtility.SIZEOF_INT + SizeofUtility.SIZEOF_CHAR*s.length();
	}
	
}
