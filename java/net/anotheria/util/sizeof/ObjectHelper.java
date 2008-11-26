package net.anotheria.util.sizeof;

public class ObjectHelper implements SizeofHelper{

	public int sizeof(Object o) {
		return SizeofUtility.SIZEOF_OBJECT_OVERHEAD;
	}
	 
}
