package net.anotheria.util.queue;

/**
 * A queue that stores timestamps of operations.
 * @author lrosenberg
 * Created on 08.11.2004
 */
public interface ITimestampedQueue<T> extends IQueue<T> {
	/**
	 * Returns the time of the last put.
	 */
	long getLastPutTimestamp();
	
	/**
	 * Returns the time of the last get.
	 */
	long getLastGetTimestamp();

}
