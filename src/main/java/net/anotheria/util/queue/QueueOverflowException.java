package net.anotheria.util.queue;

/**
 * TODO please remind another to comment this class
 *
 * @author another
 * @version $Id: $Id
 */
public class QueueOverflowException extends RuntimeException {

	private static final long serialVersionUID = 603271155696840387L;

	/**
	 * <p>Constructor for QueueOverflowException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public QueueOverflowException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for QueueOverflowException.</p>
	 *
	 * @param position a int.
	 */
	public QueueOverflowException(int position) {
		super("Queue overflow at " + position);
	}
	/**
	 * <p>Constructor for QueueOverflowException.</p>
	 *
	 * @param position a int.
	 * @param o a {@link java.lang.Object} object.
	 */
	public QueueOverflowException(int position, Object o) {
		super("Queue overflow at " + position+", to insert: "+o);
	}
	
	/**
	 * <p>Constructor for QueueOverflowException.</p>
	 *
	 * @param writePosition a int.
	 * @param readPosition a int.
	 * @param o a {@link java.lang.Object} object.
	 */
	public QueueOverflowException(int writePosition, int readPosition, Object o){
		super("Queue overflow, trying to write at "+writePosition+", last read is "+readPosition+", insert: "+o);
	}
}
