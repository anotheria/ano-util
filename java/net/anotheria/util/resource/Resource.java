package net.anotheria.util.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import net.anotheria.util.NumberUtils;

import org.apache.log4j.Logger;

/**
 * Represents a loaded configuration source for example a file. Doesn't contain the content of the file, only metadata is included. The ConfigurationSource object is a surogate which is used to execute functions
 * which are semantically ment to be executed on the configuration source itself, like registering for a change event etc.
 * @author lrosenberg
 */
public class Resource {
	/**
	 * The key for the underlying source
	 */
	private String name;
	
	private String content;
	/**
	 * A list of listeners
	 */
	private List<ResourceListener> listeners;
	/**
	 * Last detected change timestamp
	 */
	private long lastChangeTimestamp;
	
	private ResourceLoader resourceLoader;
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(Resource.class);
	
	/**
	 * Creates a new configuration source
	 * @param aKey
	 */
	public Resource(String aName){
		this(aName, false);
	}
	
	public Resource(String aName, boolean aWatch){
		this(aName, aWatch, new ClassPathResourceLoader());
	}
	
	
	/**
	 * Creates a new configuration source
	 * @param aKey
	 */
	public Resource(String aName, boolean watch, ResourceLoader aResourceLoader){
		name = aName;
		resourceLoader = aResourceLoader;
		listeners = new ArrayList<ResourceListener>(); 
		lastChangeTimestamp = System.currentTimeMillis();
		reloadContent();
		if(watch)
			WatchDog.INSTANCE.addResourceToWatch(this);
	}
	
	/**
	 * Adds a listener to this source. 
	 * @param listener a listener to add
	 */
	public void addListener(ResourceListener listener){
		synchronized(listeners){
			listeners.add(listener);
		}
	}
	/**
	 * Removes the listener from this source
	 * @param listener
	 */
	public void removeListener(ResourceListener listener){
		synchronized(listeners){
			listeners.remove(listener);
		}
	}
	
	@Override public String toString(){
		return "Resource "+name+", listeners: "+listeners.size()+", "+NumberUtils.makeISO8601TimestampString(getLastChangeTimestamp());
	}

	/**
	 * Return the last change timestamp of this source in millis
	 * @return
	 */
	public long getLastChangeTimestamp() {
		return lastChangeTimestamp;
		
	}

	/**
	 * Returns the config key of this source
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	public String getContent(){
		return content;
	}
	
	/**
	 * Returns true if this source's change timestamp is older as the given timestamp
	 * @param sourceChangeTimestamp
	 * @return
	 */
	protected boolean isOlderAs(long sourceChangeTimestamp){
		return lastChangeTimestamp < sourceChangeTimestamp;
	}
	
	/**
	 * Called by the ConfigurationSourceRegistry if a change in the underlying source is detected.
	 * @param timestamp
	 */
	protected void fireUpdateEvent(long timestamp){
		reloadContent();
		synchronized(listeners){
			for (ResourceListener listener : listeners){
				try{
					log.debug("Calling configurationSourceUpdated on "+listener);
					listener.resourceUpdated(this);
				}catch(Exception e){
					log.error("Error in notifying configuration source listener:"+listener, e);
				}
			}
		}
		lastChangeTimestamp = timestamp;
	}
	
	protected ResourceLoader getResourceLoader(){
		return resourceLoader;
	}
	
	private void reloadContent(){
		content = getResourceLoader().getContent(getName());
	}
	
	
}
