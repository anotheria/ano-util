package net.anotheria.util.resource;

/**
 * General interface for the loading physically existed resource.
 * Possible implementation can be from class path, from File System, URL
 * 
 */
public interface ResourceLoader {

	/**
	 * Returns whether resource with given name can be loaded
	 * @param resourceName the name of a resource to load
	 * @return true if resource with given name can be loaded
	 */
	boolean isAvailable(String resourceName);

	/**
	 * Returns timestamp of last resource modification
	 * @param resourceName
	 * @return timestamp of last resource modification
	 * @throws IllegalArgumentException
	 *             if resource with such name is not available
	 */
	long getLastChangeTimestamp(String resourceName) throws IllegalArgumentException;

	/**
	 * Returns text content of a resource 
	 * @param resourceName
	 * @return text content
	 * @throws IllegalArgumentException
	 *             if resource with such name is not available
	 */
	String getContent(String resourceName) throws IllegalArgumentException;
}
