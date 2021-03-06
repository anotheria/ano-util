package net.anotheria.util.queue;

/**
 * A queue that stores timestamps of operations.
 *
 * @author lrosenberg
 * Created on 08.11.2004
 * @version $Id: $Id
 */
public interface ITimestampedQueue<T> extends IQueue<T> {
	/**
	 * Returns the time of the last put.
	 *
	 * @return a long.
	 */
	long getLastPutTimestamp();
	
	/**
	 * Returns the time of the last get.
	 *
	 * @return a long.
	 */
	long getLastGetTimestamp();

}
