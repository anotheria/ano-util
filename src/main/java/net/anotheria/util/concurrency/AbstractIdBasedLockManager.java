package net.anotheria.util.concurrency;

import java.util.Map;

abstract class AbstractIdBasedLockManager<T> {
	int getLockSize(){
		return getLockMap().size();
	}
	
	String debugString(){
		return getLockMap().toString();
	}
	
	protected abstract Map<T, IdBasedLock<T>> getLockMap();

}
