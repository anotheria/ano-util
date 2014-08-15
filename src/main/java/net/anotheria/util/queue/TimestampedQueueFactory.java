package net.anotheria.util.queue;

/**
 * QueueFactory which creates TimestampedQueues.
 * @author another
 *
 * @param <T>
 */
public class TimestampedQueueFactory<T> implements IQueueFactory<T> {
	@Override public ITimestampedQueue<T> createQueue(int size){
		return new TimestampedQueueImpl<T>(size);		
	}

}