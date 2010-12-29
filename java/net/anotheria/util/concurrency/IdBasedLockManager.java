package net.anotheria.util.concurrency;

public interface IdBasedLockManager {
	IdBasedLock obtainLock(String id);
	void releaseLock(IdBasedLock lock);
}
