package net.anotheria.util.resource;

/**
 * General interface for loading data from physically existed resource.
 * Possible implementation: from class path, from file system, URL etc.
 *
 * @author another
 * @version $Id: $Id
 */
public interface ResourceLoader {

	/**
	 * Returns whether resource with given name can be loaded
	 *
	 * @param resourceName the name of a resource to load
	 * @return true if resource with given name can be loaded
	 */
	boolean isAvailable(String resourceName);

	/**
	 * Returns timestamp of last resource modification
	 *
	 * @return timestamp of last resource modification
	 * @throws java.lang.IllegalArgumentException
	 *             if resource with such name is not available
	 * @param resourceName a {@link java.lang.String} object.
	 */
	long getLastChangeTimestamp(String resourceName);

	/**
	 * Returns text content of a resource
	 *
	 * @return text content
	 * @throws java.lang.IllegalArgumentException
	 *             if resource with such name is not available
	 * @param resourceName a {@link java.lang.String} object.
	 */
	String getContent(String resourceName);
}
