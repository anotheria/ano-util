package net.anotheria.util.resource;


/**
 * A Resource listener will be notified on changes in the configuration. 
 */
public interface ResourceListener {
	/**
	 * Called by the registry as soon as a source change is detected.
	 * @param target
	 */
	void resourceUpdated(Resource target);
}
