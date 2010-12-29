package net.anotheria.util.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeIdBasedLockManager extends AbstractIdBasedLockManager implements IdBasedLockManager{
	private ConcurrentMap<String, IdBasedLock> locks = new ConcurrentHashMap<String, IdBasedLock>();
	
	@Override
	public IdBasedLock obtainLock(String id){
		IdBasedLock lock = new IdBasedLock(id, this);
		IdBasedLock myLock = locks.putIfAbsent(id, lock);
		if (myLock==null)
			return lock;
		myLock.lock();
			myLock.increaseRefCount();
			if (myLock.getRefCount().get()==1){
				//someone else have probably removed the lock in the mean time, re-add
				IdBasedLock raceCond = locks.put(id, myLock);
				//if race cond!= null we have a synch problem here, but we are unsafe after all!
				//future use - merge both locks 
			}
		
		myLock.unlockWithoutRelease();
		return myLock;
	}
	
	@Override
	public void releaseLock(IdBasedLock lock){
		String id = lock.getId();
		lock.lock();
			if (lock.getRefCount().get()==1){
				IdBasedLock previous = locks.remove(id);
			}
			lock.decreaseRefCount();
		lock.unlockWithoutRelease();
	}

	@Override
	protected Map<String, IdBasedLock> getLockMap() {
		return locks;
	}
}
