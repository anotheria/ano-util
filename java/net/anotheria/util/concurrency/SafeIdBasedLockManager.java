package net.anotheria.util.concurrency;

import java.util.HashMap;
import java.util.Map;

public class SafeIdBasedLockManager extends AbstractIdBasedLockManager implements IdBasedLockManager{
	
	private Map<String, IdBasedLock> locks = new HashMap<String, IdBasedLock>();
	
	public synchronized IdBasedLock obtainLock(String id){
		IdBasedLock lock = locks.get(id);
		if (lock!=null){
			lock.increaseRefCount();
			return lock;
		}
		
		lock = new IdBasedLock(id, this);
		locks.put(id, lock);
		return lock;
	}
	public synchronized void releaseLock(IdBasedLock lock){
		String id = lock.getId();
		if (lock.getRefCount().get()==1){
			locks.remove(id);
		}
		lock.decreaseRefCount();
	}
	
	
	@Override
	protected Map<String, IdBasedLock> getLockMap() {
		return locks;
	}

}
