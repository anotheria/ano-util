package net.anotheria.util.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An implementation of the IQueue interface. Note, in the pre-1.5 times we used to have an own implementation here, but now its simply a wrapper to ArrayBlockingQueue.
 *
 * @author lrosenberg
 * @since 22.06.2004
 * @version $Id: $Id
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
	 */
	QueueImpl(int aSize){
		listeners = new ArrayList<>();
		underlyingQueue = new ArrayBlockingQueue<>(aSize);
		size = aSize;
	}
	 

	/** {@inheritDoc} */
	@Override public void addListener(IQueueListener listener) {
		listeners.add(listener);
	}

	/** {@inheritDoc} */
	@Override public int getElementCount() {
		return underlyingQueue.size();
	}

	/** {@inheritDoc} */
	@Override public int size() {
		return size;
	}

	/** {@inheritDoc} */
	@Override public boolean hasElements() {
		return !underlyingQueue.isEmpty();
	}
	
	/** {@inheritDoc} */
	@Override public T nextElement() {
		if (!hasElements())
			throw new RuntimeException("No elements");
		return underlyingQueue.poll(); 
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * Puts a new element in the queue.
	 */
	@Override
	public void putElement(T o) {
		if (!underlyingQueue.offer(o)){
			throw new QueueOverflowException(String.valueOf(o));
		}
	} 

	/** {@inheritDoc} */
	@Override public void removeListener(IQueueListener listener) {
		listeners.remove(listener);
	}
	
	/** {@inheritDoc} */
	@Override public String toString(){
		return "queue count: " + getElementCount();
	}
	

}
