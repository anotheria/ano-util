package net.anotheria.util.resource;

import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of physically existing underlying resource with text content. Underlying resource is
 * identified by name. Depending on ResourceLoader (default is
 * ClassPathResourceLoader) name can be absolute or relative on File System, URL
 * etc. Acts as a proxy to hide all routine for a underlying resource loading and updating.
 *
 * @author another
 * @version $Id: $Id
 */
public class Resource {
	/**
	 * The name of the Resource
	 */
	private String name;

	/**
	 * The content for the Resource
	 */
	private String content;
	/**
	 * A list of listeners
	 */
	private List<ResourceListener> listeners;
	/**
	 * Last detected change timestamp
	 */
	private long lastChangeTimestamp;

	/**
	 * The ResurceLoader that retrieves data from its underlying
	 */
	private ResourceLoader resourceLoader;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(Resource.class);

	/**
	 * Creates a new Resource for its underlying in class path
	 *
	 * @param aName
	 *            name of the Resource
	 */
	public Resource(String aName) {
		this(aName, false);
	}

	/**
	 * Creates a new Resource for its underlying in class path
	 *
	 * @param aName
	 *            name of the Resource
	 * @param aWatch
	 *            must be this Resource up to date with its physical changes
	 */
	public Resource(String aName, boolean aWatch) {
		this(aName, new ClassPathResourceLoader(), aWatch);
	}

	/**
	 * Creates a new Resource
	 *
	 * @param aName
	 *            name of the Resource
	 * @param aResourceLoader
	 *            ResourceLoader that is used to load its underlying
	 * @param watch a boolean.
	 */
	public Resource(String aName, ResourceLoader aResourceLoader, boolean watch) {
		name = aName;
		resourceLoader = aResourceLoader;
		listeners = new ArrayList<>();
		lastChangeTimestamp = System.currentTimeMillis();
		reloadContent();
		if (watch)
			WatchDog.INSTANCE.addResourceToWatch(this);
	}

	/**
	 * Adds a listener to this Resource.
	 *
	 * @param listener
	 *            a listener to add
	 */
	public void addListener(ResourceListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the listener from this Resource
	 *
	 * @param listener a {@link net.anotheria.util.resource.ResourceListener} object.
	 */
	public void removeListener(ResourceListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {

        return "Resource " + name + ", listeners: " + listeners.size() + ", " + NumberUtils.makeISO8601TimestampString(lastChangeTimestamp);
	}

	/**
	 * Return the last change timestamp of this Resource in millis
	 *
	 * @return a long.
	 */
	public long getLastChangeTimestamp() {
		return lastChangeTimestamp;

	}

	/**
	 * Returns the name of this Resource
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the content of this Resource
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Returns true if this Resource's change timestamp is older as the given
	 * timestamp
	 *
	 * @param resourceChangeTimestamp a long.
	 * @return a boolean.
	 */
	protected boolean isOlderAs(long resourceChangeTimestamp) {
		return lastChangeTimestamp < resourceChangeTimestamp;
	}

	/**
	 * Called by the WatchDog if a change in the underlying
	 * Resource is detected.
	 *
	 * @param timestamp a long.
	 */
	protected void fireUpdateEvent(long timestamp) {
		reloadContent();
		synchronized (listeners) {
			for (ResourceListener listener : listeners) {
				try {
					log.debug("Calling configurationSourceUpdated on {}", listener);
					listener.resourceUpdated(this);
				} catch (Exception e) {
					log.error("Error in notifying configuration source listener: " + listener, e);
				}
			}
		}
		lastChangeTimestamp = timestamp;
	}

	/**
	 * <p>Getter for the field <code>resourceLoader</code>.</p>
	 *
	 * @return a {@link net.anotheria.util.resource.ResourceLoader} object.
	 */
	protected ResourceLoader getResourceLoader() {
		return resourceLoader;
	}

	private void reloadContent() {
        content = resourceLoader.getContent(name);
	}

}
