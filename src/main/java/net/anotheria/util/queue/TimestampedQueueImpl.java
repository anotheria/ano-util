package net.anotheria.util.queue;

/**
 * Same as normal queue, but with timestamps for last put and get operations.
 *
 * @author lrosenberg
 * Created on 08.11.2004
 * @version $Id: $Id
 */
public class TimestampedQueueImpl<T> extends QueueImpl<T> implements ITimestampedQueue<T> {
	
	private long lastPut;
	private long lastGet;
	
	/**
	 * <p>Constructor for TimestampedQueueImpl.</p>
	 *
	 * @param size a int.
	 */
	public TimestampedQueueImpl(int size){
		super(size);
	}
	
	/** {@inheritDoc} */
	@Override public T nextElement() {
		lastGet = System.currentTimeMillis();
		return super.nextElement();
	}

	/** {@inheritDoc} */
	@Override public void putElement(T o) {
		lastPut = System.currentTimeMillis();
		super.putElement(o);
	}
	
	/** {@inheritDoc} */
	@Override public long getLastPutTimestamp(){
		return lastPut;
	}
	
	/** {@inheritDoc} */
	@Override public long getLastGetTimestamp(){
		return lastGet;
	}
}
