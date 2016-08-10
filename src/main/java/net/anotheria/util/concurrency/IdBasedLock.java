package net.anotheria.util.concurrency;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The lock class.
 *
 * @author another
 * @version $Id: $Id
 */
public class IdBasedLock<K> implements Serializable {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = 6517735561182643502L;
	/**
	 * Reference count for this lock.
	 */
    private final AtomicInteger refCount = new AtomicInteger(1);
	/**
	 * The underlying lock object.
	 */
    private final Lock lock = new ReentrantLock();
	/**
	 * My manager.
	 */
    private final IdBasedLockManager<K> parent;
	/**
	 * The id object for the lock.
	 */
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

    /**
     * <p>getReferenceCount.</p>
     *
     * @return a int.
     */
    public int getReferenceCount() {
        return refCount.get();
    }

    AtomicInteger getRefCount() {
        return refCount;
    }

    /**
     * <p>tryLock.</p>
     *
     * @param timeout a long.
     * @param unit a {@link java.util.concurrent.TimeUnit} object.
     * @return a boolean.
     * @throws java.lang.InterruptedException if any.
     */
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
      return lock.tryLock(timeout, unit);
    }

    /**
     * <p>lock.</p>
     */
    public void lock() {
        lock.lock();
    }

    /**
     * <p>unlockWithoutRelease.</p>
     */
    public void unlockWithoutRelease() {
        lock.unlock();
    }

    /**
     * <p>unlock.</p>
     */
    public void unlock() {
        lock.unlock();
        parent.releaseLock(this);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "(" + id + ", " + refCount + ')';
    }

    K getId() {
        return id;
    }
}
