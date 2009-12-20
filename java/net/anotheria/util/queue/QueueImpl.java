package net.anotheria.util.queue;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Current queue element.
	 */
	private int currentElement;
	/**
	 * Last queue element.
	 */
	private int lastElement;
	/**
	 * Size of the queue.
	 */
	private int size;
	
	/**
	 * Elements of the queue.
	 */
	private Object[] elements;
	
	
	/**
	 * Creates a new QueueImpl.
	 * @param aSize
	 */
	QueueImpl(int aSize){
		listeners = new ArrayList<IQueueListener>();
		currentElement = -1;
		lastElement = -1;
		this.size = aSize;
		elements = new Object[aSize];
	}
	

	@Override public void addListener(IQueueListener listener) {
		listeners.add(listener);
	}

	public synchronized int getElementCount() {
		if (lastElement >= currentElement)
			return lastElement - currentElement;
		return elements.length - currentElement + lastElement;
	}

	@Override public synchronized boolean hasElements() {
		return currentElement != lastElement;
	}
	
	@Override public synchronized T nextElement() {
		if (!hasElements())
			throw new RuntimeException("No elements");
		currentElement++;
		if (currentElement >= size)
			currentElement = 0;
		T ret = (T)elements[currentElement];
		elements[currentElement] = null;
		return ret; 
	}
	
	/**
	 * Puts a new element in the queue.
	 * @param o
	 * @throws QueueOverflowException if the queue is full.
	 */
	@Override
	public synchronized void putElement(T o) throws QueueOverflowException {
		lastElement++;
		if (lastElement==size)
			lastElement=0;
		if (lastElement==currentElement){
			lastElement--;
			if (lastElement<0)
				lastElement = size-1;
			throw new QueueOverflowException(lastElement); 
			
		}
		
		elements[lastElement] = o;
	}

	@Override public void removeListener(IQueueListener listener) {
		listeners.remove(listener);
	}
	
	@Override public String toString(){
		return "queue size: "+size+" last: "+lastElement+" curr: "+currentElement + " count: " + getElementCount();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override public int size(){
		return size;
	}


}
