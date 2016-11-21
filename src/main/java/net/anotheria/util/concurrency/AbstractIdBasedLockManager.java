package net.anotheria.util.concurrency;

import java.util.Map;

/**
 * Base implementation class for IdLockManagers.
 * @param <T>
 */
abstract class AbstractIdBasedLockManager<T> {
	/**
	 * Return the size of the lock map.
	 * @return
	 */
	int getLockSize(){
		return getLockMap().size();
	}

	/**
	 * Create a debug string for debug purposes.
	 * @return
	 */
	String debugString(){
		return getLockMap().toString();
	}
	
	/**
	 * <p>getLockMap.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	protected abstract Map<T, IdBasedLock<T>> getLockMap();

}
