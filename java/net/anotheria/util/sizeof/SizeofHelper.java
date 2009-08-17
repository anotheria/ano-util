package net.anotheria.util.sizeof;

/**
 * A helper class which can be used to register a handler for a object type which is not scanable due to security restrictions.
 * @author another
 *
 */
public interface SizeofHelper {
	/**
	 * Returns the calculated size of the object.
	 * @param o
	 * @return
	 */
	int sizeof(Object o);
}
