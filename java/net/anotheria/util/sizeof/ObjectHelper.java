package net.anotheria.util.sizeof;

/**
 * Generic helper for objects, calculates only the object overhead by the vm.
 * @author lrosenberg
 */
public class ObjectHelper implements SizeofHelper{

	@Override public int sizeof(Object o) {
		return SizeofUtility.SIZEOF_OBJECT_OVERHEAD;
	}
	 
}
