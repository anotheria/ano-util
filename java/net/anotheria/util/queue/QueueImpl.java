package net.anotheria.util.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An implementation of the IQueue interface. Note, in the pre-1.5 times we used to have an own implementation here, but now its simply a wrapper to ArrayBlockingQueue.
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

	@Override public int getElementCount() {
		return underlyingQueue.size();
	}

	@Override public int size() {
		return size;
	}

	@Override public boolean hasElements() {
		return !underlyingQueue.isEmpty();
	}
	
	@Override public T nextElement() {
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
	public void putElement(T o) throws QueueOverflowException {
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
