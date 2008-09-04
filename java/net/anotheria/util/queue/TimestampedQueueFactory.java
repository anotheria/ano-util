package net.anotheria.util.queue;

public class TimestampedQueueFactory<T> implements IQueueFactory<T> {
	public ITimestampedQueue<T> createQueue(int size){
		return new TimestampedQueueImpl<T>(size);		
	}

}
