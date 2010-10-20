package net.anotheria.util.resource;


/**
 * A source loader for files.
 * 
 * @author lrosenberg
 */
public interface ResourceLoader {

	boolean isAvailable(String resourceName);

	long getLastChangeTimestamp(String resourceName);

	String getContent(String resourceName);
}
