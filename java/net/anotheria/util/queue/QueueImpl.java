package net.anotheria.util.queue;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the IQueue interface.
 * @author lrosenberg
 * Created on 22.06.2004
 */
public class QueueImpl implements IQueue{
	
	
	private List listeners;
	
	private int currentElement;
	private int lastElement;
	private int size;
	
	private Object[] elements;
	
	
	QueueImpl(int size){
		listeners = new ArrayList();
		currentElement = -1;
		lastElement = -1;
		this.size = size;
		elements = new Object[size];
	}
	

	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#addListener(de.friendscout.vincent.util.IQueueListener)
	 */
	public void addListener(IQueueListener listener) {
		listeners.add(listeners);
	}

	/** Not implemented.
	 * @see de.friendscout.vincent.util.IQueue#getElementCount()
	 */
	public synchronized int getElementCount() {
		if (lastElement >= currentElement)
			return lastElement - currentElement;
		return elements.length - currentElement + lastElement;
	}

	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#hasElements()
	 */
	public synchronized boolean hasElements() {
		return currentElement != lastElement;
	}

	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#nextElement()
	 */
	public synchronized Object nextElement() {
		if (!hasElements())
			throw new RuntimeException("No elements");
		currentElement++;
		if (currentElement >= size)
			currentElement = 0;
		Object ret = elements[currentElement];
		elements[currentElement] = null;
		return ret; 
	}
	
	/**
	 * Puts a new element in the queue. If the queue is full a runtime exception ("Queue overflow.") is thrown.
	 */
	public synchronized void putElement(Object o){
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

	/* (non-Javadoc)
	 * @see de.friendscout.vincent.util.IQueue#removeListener(de.friendscout.vincent.util.IQueueListener)
	 */
	public void removeListener(IQueueListener listener) {
		listeners.remove(listener);
	}
	
	public String toString(){
		return "queue size: "+size+" last: "+lastElement+" curr: "+currentElement + " count: " + getElementCount();
	}
	
	public int size(){
		return size;
	}


}
