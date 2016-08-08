package net.anotheria.util.queue;

/**
 * Interface for a worker class whih performs operation on the queue elements.
 * @author another
 *
 */
public interface IQueueWorker<T extends Object> {
	/**
	 * Called to perform some work.
	 */
	void doWork(T workingElement) throws Exception;
	
}
