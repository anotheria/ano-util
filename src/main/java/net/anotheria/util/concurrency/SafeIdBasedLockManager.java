package net.anotheria.util.concurrency;


import java.util.HashMap;
import java.util.Map;

/**
 * <p>SafeIdBasedLockManager class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class SafeIdBasedLockManager<K> extends AbstractIdBasedLockManager<K>
        implements IdBasedLockManager<K> {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = -7086955847344168761L;
    private Map<K, IdBasedLock<K>> locks = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public IdBasedLock<K> obtainLock(K id) {
        synchronized (this) {
            IdBasedLock<K> lock = locks.get(id);
            if (lock != null) {
                lock.increaseRefCount();
                return lock;
            }

            lock = new IdBasedLock<>(id, this);
            locks.put(id, lock);
            return lock;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void releaseLock(IdBasedLock<K> lock) {
        synchronized (this) {
            K id = lock.getId();
            if (lock.getRefCount().get() == 1) {
                locks.remove(id);
            }
            lock.decreaseRefCount();
        }
    }

    /** {@inheritDoc} */
    @Override
    protected Map<K, IdBasedLock<K>> getLockMap() {
        return locks;
    }
}
