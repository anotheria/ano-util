package net.anotheria.util.queue;

/**
 * A factory for a queue.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public interface IQueueFactory<T> {
	/**
	 * Creates a new queue of the given size.
	 *
	 * @param size a int.
	 * @return a {@link net.anotheria.util.queue.IQueue} object.
	 */
	IQueue<T> createQueue(int size);
}
