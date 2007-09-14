package net.anotheria.util.queue;

/**
 * A listener for queue state change notifications.
 * @author lrosenberg
 * Created on 22.06.2004
 */
public interface IQueueListener {
	/**
	 * Called if the queue is emptied.
	 *
	 */
	public void queueEmpty();
	/**
	 * Called if an element put into the queue.
	 *
	 */
	public void queueElementAdded();
	/**
	 * Called if the queue is full.
	 *
	 */
	public void queueFull();
	 
}
