package net.anotheria.util.queue;

/**
 * An interface for a queue.
 * @author lrosenberg
 * @since 22.06.2004
 */
public interface IQueue<T> {
	/**
	 * Returns true if the queue contains elements.
	 */
	boolean hasElements();
	/**
	 * Returns the number of elements in the queue.
	 */
	int getElementCount();
	/**
	 * Returns the nextElement from the queue, removes the element from
	 * the queue afterwards.
	 */
	T nextElement();
	/**
	 * Puts a new element in the queue.
	 * @throws QueueOverflowException if the queue is full.
	 */
	void putElement(T o);
	/**
	 * Adds a queue listener to the queue.
	 */
	 void addListener(IQueueListener listener);
	/**
	 * Removes a queue listener from the queue.
	 */
	void removeListener(IQueueListener listener);
	
	/**
	 * Returns the size of the queue.
	 */
	int size();
}
