package net.anotheria.util.concurrency;

import java.util.Map;

abstract class AbstractIdBasedLockManager<T> {
	int getLockSize(){
		return getLockMap().size();
	}
	
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
