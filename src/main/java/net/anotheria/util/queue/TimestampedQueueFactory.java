package net.anotheria.util.queue;

/**
 * QueueFactory which creates TimestampedQueues.
 *
 * @author another
 * @version $Id: $Id
 */
public class TimestampedQueueFactory<T> implements IQueueFactory<T> {
	/** {@inheritDoc} */
	@Override public ITimestampedQueue<T> createQueue(int size){
		return new TimestampedQueueImpl<>(size);
	}

}
