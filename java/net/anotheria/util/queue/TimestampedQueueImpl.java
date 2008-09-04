package net.anotheria.util.queue;

/**
 * Same as normal queue, but with timestamps for last put and get operations.
 * @author lrosenberg
 * Created on 08.11.2004
 */
public class TimestampedQueueImpl<T> extends QueueImpl<T> implements ITimestampedQueue<T>{
	
	private long lastPut;
	private long lastGet;
	
	public TimestampedQueueImpl(int size){
		super(size);
	}
	
	
	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#nextElement()
	 */
	public T nextElement() {
		lastGet = System.currentTimeMillis();
		return super.nextElement();
	}

	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#putElement(java.lang.Object)
	 */
	public void putElement(T o) {
		lastPut = System.currentTimeMillis();
		super.putElement(o);
	}
	
	public long getLastPutTimestamp(){
		return lastPut;
	}
	
	public long getLastGetTimestamp(){
		return lastGet;
	}
	
	

}
