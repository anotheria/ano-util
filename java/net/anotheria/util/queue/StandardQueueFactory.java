package net.anotheria.util.queue;

/**
 * A factory which creates queues.
 * @author lrosenberg
 * Created on 22.06.2004
 */
public class StandardQueueFactory<T> implements IQueueFactory<T>{
	public IQueue<T> createQueue(int size){
		return new QueueImpl<T>(size);
	}
	
}
