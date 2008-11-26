package net.anotheria.util.sizeof;



public class StringHelper implements SizeofHelper{

	public int sizeof(Object o) {
		String s = (String)o;
		return s==null ? 0 : 3*SizeofUtility.SIZEOF_INT + SizeofUtility.SIZEOF_CHAR*s.length();
	}
	
}
