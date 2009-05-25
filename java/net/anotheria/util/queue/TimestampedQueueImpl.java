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
	
	@Override public T nextElement() {
		lastGet = System.currentTimeMillis();
		return super.nextElement();
	}

	@Override public void putElement(T o) {
		lastPut = System.currentTimeMillis();
		super.putElement(o);
	}
	
	@Override public long getLastPutTimestamp(){
		return lastPut;
	}
	
	@Override public long getLastGetTimestamp(){
		return lastGet;
	}
}
