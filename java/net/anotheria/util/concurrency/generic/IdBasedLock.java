package net.anotheria.util.concurrency.generic;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class IdBasedLock<K extends Serializable> implements Serializable {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = 6517735561182643502L;
    private final AtomicInteger refCount = new AtomicInteger(1);
    private final ReentrantLock lock = new ReentrantLock();
    private final IdBasedLockManager<K> parent;
    private final K id;

    IdBasedLock(K anId, IdBasedLockManager<K> aParent) {
        id = anId;
        parent = aParent;
    }

    void increaseRefCount() {
        refCount.incrementAndGet();
    }

    void decreaseRefCount() {
        refCount.decrementAndGet();
    }

    public int getReferenceCount() {
        return refCount.get();
    }

    AtomicInteger getRefCount() {
        return refCount;
    }

    public void lock() {
        lock.lock();
    }

    public void unlockWithoutRelease() {
        lock.unlock();
    }

    public void unlock() {
        lock.unlock();
        parent.releaseLock(this);
    }

    @Override
    public String toString() {
        return "(" + id + ", " + refCount + ")";
    }

    K getId() {
        return id;
    }
}
