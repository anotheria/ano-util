package net.anotheria.util;

/**
 * An observer watches a subject and becomes notified when it changes.
 */
public interface IObserver {
	/**
	 * Called by the subject if its updated.
	 */
	void update(ISubject subject);
}
 