package net.anotheria.util.concurrency;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * SafeIdBasedLockManager - an optimistic, mostly safe implementation, which is not 100% thread safe, but a little faster.
 *
 * @author another
 * @version $Id: $Id
 */
public class UnsafeIdBasedLockManager<K> extends AbstractIdBasedLockManager<K>
        implements IdBasedLockManager<K> {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = -4257919838109398407L;
    private ConcurrentMap<K, IdBasedLock<K>> locks = new ConcurrentHashMap<>();

    /** {@inheritDoc} */
    @Override
    public IdBasedLock<K> obtainLock(K id) {
        IdBasedLock<K> lock = new IdBasedLock<>(id, this);
        IdBasedLock<K> myLock = locks.putIfAbsent(id, lock);
        if (myLock == null)
            return lock;
        myLock.lock();
        myLock.increaseRefCount();
        if (myLock.getRefCount().get() == 1) {
            //someone else have probably removed the lock in the mean time, re-add
            IdBasedLock raceCond = locks.put(id, myLock);
            //if race cond!= null we have a synch problem here, but we are unsafe after all!
            //future use - merge both locks
        }

        myLock.unlockWithoutRelease();
        return myLock;
    }

    /** {@inheritDoc} */
    @Override
    public void releaseLock(IdBasedLock<K> lock) {
        K id = lock.getId();
        lock.lock();
        if (lock.getRefCount().get() == 1) {
            IdBasedLock previous = locks.remove(id);
        }
        lock.decreaseRefCount();
        lock.unlockWithoutRelease();
    }

    /** {@inheritDoc} */
    @Override
    protected Map<K, IdBasedLock<K>> getLockMap() {
        return locks;
    }
}
