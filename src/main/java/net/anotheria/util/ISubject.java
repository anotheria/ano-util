package net.anotheria.util;

/**
 * The subject in the subject observer pattern.
 *
 * @author another
 * @version $Id: $Id
 */
public interface ISubject {
	/**
	 * Adds an observer to this subject.
	 *
	 * @param observer the observer to add.
	 */
	void addObserver(IObserver observer);
	/**
	 * Removes an observer from this subject.
	 *
	 * @param observer the observer to remove.
	 */
	void removeObserver(IObserver observer);
}
 
