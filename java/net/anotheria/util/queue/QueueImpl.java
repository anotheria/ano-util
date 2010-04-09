package net.anotheria.util.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An implementation of the IQueue interface.
 * @author lrosenberg
 * @since 22.06.2004
 */
public class QueueImpl<T> implements IQueue<T> {
	
	/**
	 * Listeners of the queue.
	 */
	private List<IQueueListener> listeners;
	
	private ArrayBlockingQueue<T> underlyingQueue;
	
	private int size;
	/**
	 * Creates a new QueueImpl.
	 * @param aSize
	 */
	QueueImpl(int aSize){
		listeners = new ArrayList<IQueueListener>();
		underlyingQueue = new ArrayBlockingQueue<T>(aSize);
		size = aSize;
	}
	

	@Override public void addListener(IQueueListener listener) {
		listeners.add(listener);
	}

	public synchronized int getElementCount() {
		return underlyingQueue.size();
	}

	public int size() {
		return size;
	}

	@Override public synchronized boolean hasElements() {
		return !underlyingQueue.isEmpty();
	}
	
	@Override public synchronized T nextElement() {
		if (!hasElements())
			throw new RuntimeException("No elements");
		return underlyingQueue.poll(); 
	}
	
	
	/**
	 * Puts a new element in the queue.
	 * @param o
	 * @throws QueueOverflowException if the queue is full.
	 */
	@Override
	public synchronized void putElement(T o) throws QueueOverflowException {
		if (!underlyingQueue.offer(o)){
			throw new QueueOverflowException(""+o); 
		}
	} 

	@Override public void removeListener(IQueueListener listener) {
		listeners.remove(listener);
	}
	
	@Override public String toString(){
		return "queue count: " + getElementCount();
	}
	

}
