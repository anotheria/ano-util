package net.anotheria.util.resource;


/**
 * A Resource listener will be notified on changes in the Resource. 
 */
public interface ResourceListener {
	/**
	 * Called as soon as a Resource change is detected.
	 */
	void resourceUpdated(Resource target);
}
