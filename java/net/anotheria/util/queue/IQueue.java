package net.anotheria.util.queue;

/**
 * An interface for a queue.
 * @author lrosenberg
 * Created on 22.06.2004
 */
public interface IQueue<T> {
	/**
	 * Returns true if the queue contains elements.
	 * @return
	 */
	public boolean hasElements();
	/**
	 * Returns the number of elements in the queue.
	 * @return
	 */
	public int getElementCount();
	/**
	 * Returns the nextElement from the queue, removes the element from
	 * the queue afterwards.
	 * @return
	 */
	public T nextElement();
	/**
	 * Puts an element into the queue.
	 * @param o
	 */
	public void putElement(T o) throws QueueOverflowException;
	/**
	 * Adds a queue listener to the queue.
	 * @param listener
	 */
	public void addListener(IQueueListener listener);
	/**
	 * Removes a queue listener from the queue.
	 * @param listener
	 */
	public void removeListener(IQueueListener listener);
	
	public int size();
}
