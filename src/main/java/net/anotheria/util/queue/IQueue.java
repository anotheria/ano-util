package net.anotheria.util.queue;

/**
 * An interface for a queue.
 *
 * @author lrosenberg
 * @since 22.06.2004
 * @version $Id: $Id
 */
public interface IQueue<T> {
	/**
	 * Returns true if the queue contains elements.
	 *
	 * @return a boolean.
	 */
	boolean hasElements();
	/**
	 * Returns the number of elements in the queue.
	 *
	 * @return a int.
	 */
	int getElementCount();
	/**
	 * Returns the nextElement from the queue, removes the element from
	 * the queue afterwards.
	 *
	 * @return a T object.
	 */
	T nextElement();
	/**
	 * Puts a new element in the queue.
	 *
	 * @throws net.anotheria.util.queue.QueueOverflowException if the queue is full.
	 * @param o a T object.
	 */
	void putElement(T o);
	 /**
	  * Adds a queue listener to the queue.
	  *
	  * @param listener a {@link net.anotheria.util.queue.IQueueListener} object.
	  */
	 void addListener(IQueueListener listener);
	/**
	 * Removes a queue listener from the queue.
	 *
	 * @param listener a {@link net.anotheria.util.queue.IQueueListener} object.
	 */
	void removeListener(IQueueListener listener);
	
	/**
	 * Returns the size of the queue.
	 *
	 * @return a int.
	 */
	int size();
}
