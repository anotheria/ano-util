package net.anotheria.util.queue;

/**
 * A factory which creates queues.
 * @author lrosenberg
 * Created on 22.06.2004
 */
public class QueueFactory {
	public static final IQueue createQueue(int size){
		return new QueueImpl(size);
	}
	
	public static final ITimestampedQueue createTimestampedQueue(int size){
		return new TimestampedQueueImpl(size);		
	}
}
