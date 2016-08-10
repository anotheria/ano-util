package net.anotheria.util.queue;

/**
 * Interface for a worker class whih performs operation on the queue elements.
 *
 * @author another
 * @version $Id: $Id
 */
public interface IQueueWorker<T extends Object> {
	/**
	 * Called to perform some work.
	 *
	 * @param workingElement a T object.
	 * @throws java.lang.Exception if any.
	 */
	void doWork(T workingElement) throws Exception;
	
}
