package net.anotheria.util.queue;

/**
 * TODO please remind another to comment this class
 * 
 * @author another
 */
public class QueueOverflowException extends RuntimeException {

	private static final long serialVersionUID = 603271155696840387L;

	public QueueOverflowException(int position) {
		super("Queue overflow at " + position);
	}
}
