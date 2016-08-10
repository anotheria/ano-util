package net.anotheria.util.resource;


/**
 * A Resource listener will be notified on changes in the Resource.
 *
 * @author another
 * @version $Id: $Id
 */
public interface ResourceListener {
	/**
	 * Called as soon as a Resource change is detected.
	 *
	 * @param target a {@link net.anotheria.util.resource.Resource} object.
	 */
	void resourceUpdated(Resource target);
}
