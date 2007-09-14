package net.anotheria.util;

/**
 * @author another
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface ISubject {
	public void addObserver(IObserver observer);
	public void removeObserver(IObserver observer);
}
 