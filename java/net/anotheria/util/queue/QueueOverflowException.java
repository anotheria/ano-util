package net.anotheria.util.queue;

/**
 * TODO please remind another to comment this class
 * 
 * @author another
 */
public class QueueOverflowException extends RuntimeException {

	private static final long serialVersionUID = 603271155696840387L;

	public QueueOverflowException(String message) {
		super(message);
	}

	public QueueOverflowException(int position) {
		super("Queue overflow at " + position);
	}
	public QueueOverflowException(int position, Object o) {
		super("Queue overflow at " + position+", to insert: "+o);
	}
	
	public QueueOverflowException(int writePosition, int readPosition, Object o){
		super("Queue overflow, trying to write at "+writePosition+", last read is "+readPosition+", insert: "+o);
	}
}
