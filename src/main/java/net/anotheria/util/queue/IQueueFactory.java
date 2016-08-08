package net.anotheria.util.queue;

/**
 * A factory for a queue.
 * @author lrosenberg
 *
 */
public interface IQueueFactory<T> {
	/**
	 * Creates a new queue of the given size.
	 */
	IQueue<T> createQueue(int size);
}
