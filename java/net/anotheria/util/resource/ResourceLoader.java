package net.anotheria.util.resource;

/**
 * A source loader for files.
 * 
 * @author lrosenberg
 */
public interface ResourceLoader {

	boolean isAvailable(String resourceName);

	/**
	 * @param resourceName
	 * @return
	 * @throws IllegalArgumentException
	 *             if resource with such name doesn't exist
	 */
	long getLastChangeTimestamp(String resourceName) throws IllegalArgumentException;

	/**
	 * @param resourceName
	 * @return
	 * @throws IllegalArgumentException
	 *             if resource with such name doesn't exist
	 */
	String getContent(String resourceName) throws IllegalArgumentException;
}
