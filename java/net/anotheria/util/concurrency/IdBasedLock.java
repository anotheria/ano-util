package net.anotheria.util.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class IdBasedLock {
	private final AtomicInteger refCount = new AtomicInteger(1);
	private final ReentrantLock lock = new ReentrantLock();
	private final IdBasedLockManager parent;
	private final String id;
	
	IdBasedLock(String anId, IdBasedLockManager aParent){
		id = anId;
		parent = aParent;
	}
	
	void increaseRefCount(){
		refCount.incrementAndGet();
	}
	
	void decreaseRefCount(){
		refCount.decrementAndGet();
	}

	public int getReferenceCount(){
		return refCount.get();
	}
	
	AtomicInteger getRefCount(){
		return refCount;
	}
	
	public void lock(){
		lock.lock();
	}
	
	public void unlockWithoutRelease(){
		lock.unlock();
	}
	
	public void unlock(){
		lock.unlock();
		parent.releaseLock(this);
	}
	
	@Override public String toString(){
		return "("+id+", "+refCount+")";
	}
	
	String getId(){
		return id;
	}
}
