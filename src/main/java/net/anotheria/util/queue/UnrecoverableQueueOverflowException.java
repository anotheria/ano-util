package net.anotheria.util.queue;

/**
 * <p>UnrecoverableQueueOverflowException class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class UnrecoverableQueueOverflowException extends Exception {

	/**
	 * <p>Constructor for UnrecoverableQueueOverflowException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public UnrecoverableQueueOverflowException(String message, Throwable cause) {
		super(message, cause);
	}

}
