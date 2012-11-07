package net.anotheria.util.queue;

/**
 * A factory for a queue.
 * @author lrosenberg
 *
 * @param <T>
 */
public interface IQueueFactory<T> {
	/**
	 * Creates a new queue of the given size.
	 * @param size
	 * @return
	 */
	IQueue<T> createQueue(int size);
}
