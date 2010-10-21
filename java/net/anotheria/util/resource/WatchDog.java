package net.anotheria.util.resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.anotheria.util.NumberUtils;

import org.apache.log4j.Logger;

/**
 * ConfigurationSourceRegistry is the singleton object that controls and manages all known configuration sources. It also has an internal thread that checks the sources for update in defined time periods.
 * Currently the update interval is 10 seconds.
 * @author lrosenberg
 */
public enum WatchDog {
	/**
	 * The one and only instance of the ConfigurationSourceRegistry.
	 */
	INSTANCE(10000);
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(WatchDog.class);
	
	private Map<String, Resource> watchingRegistry;
	private volatile long pause;
	
	private WatchDog(long aPause){
		watchingRegistry = new ConcurrentHashMap<String, Resource>();
		pause = aPause;
		new WatcherThread().start();
	}
	
	public long getPause() {
		return pause;
	}

	public void setPause(long aPause) {
		pause = aPause;
	}
	
	public void addResourceToWatch(Resource resource){
		watchingRegistry.put(resource.getName(), resource);
	}
	
	public void removeResourceFromWatch(Resource resource){
		watchingRegistry.remove(resource.getName());
	}
	
	/**
	 * WatcherThread runs in background and checks whether a configuration source has been updated all X seconds. In case it did, it fires an update event on the source triggering a reconfiguration.
	 * @author lrosenberg.
	 *
	 */
	private final class WatcherThread extends Thread{
		
		
		private WatcherThread(){
			setDaemon(true);
		}
		
		@Override public void run(){
			try{
				while(!Thread.interrupted()){
					Thread.sleep(getPause());
					Set<Resource>  watchedResources = new HashSet<Resource>(watchingRegistry.values());
					for (Resource source : watchedResources){
						ResourceLoader loader = source.getResourceLoader();
						try{
							long lastUpdate = loader.getLastChangeTimestamp(source.getName());
							log.debug("Checking source: "+source+", lastUpdateFromLoader= "+NumberUtils.makeISO8601TimestampString(lastUpdate)+", storedLastUpdate="+NumberUtils.makeISO8601TimestampString(source.getLastChangeTimestamp()));
							if (source.isOlderAs(lastUpdate)){
								log.debug("firing update event: "+ source);
								//System.out.println("firing update on source: "+source);
								source.fireUpdateEvent(lastUpdate);
							}
						}catch(IllegalArgumentException e){
							log.warn("Apparently checking for non existing source, how did it came into the registry anyway?", e);
						}
						
					}
				}
			}catch(InterruptedException e){}
		}
	}


}
