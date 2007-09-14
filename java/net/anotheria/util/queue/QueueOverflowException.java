package net.anotheria.util.queue;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class QueueOverflowException extends RuntimeException{
	public QueueOverflowException(int position){
		super("Queue overflow at "+position);
	}
}
