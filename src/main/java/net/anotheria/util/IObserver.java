package net.anotheria.util;

/**
 * An observer watches a subject and becomes notified when it changes.
 *
 * @author another
 * @version $Id: $Id
 */
public interface IObserver {
	/**
	 * Called by the subject if its updated.
	 *
	 * @param subject a {@link net.anotheria.util.ISubject} object.
	 */
	void update(ISubject subject);
}
 
