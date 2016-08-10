package net.anotheria.util;

/**
 * <p>ThreadUtils class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class ThreadUtils {

	/**
	 * <p>waitIgnoreException.</p>
	 *
	 * @param monitor a {@link java.lang.Object} object.
	 */
	public static void waitIgnoreException(Object monitor){
		waitIgnoreException(monitor, 0);
	}
	
	/**
	 * <p>waitIgnoreException.</p>
	 *
	 * @param monitor a {@link java.lang.Object} object.
	 * @param timeout a long.
	 */
	public static void waitIgnoreException(Object monitor, long timeout){
		try {
			monitor.wait(timeout);
		} catch (InterruptedException ignored) {}
	}
	
	/**
	 * <p>sleepIgnoreException.</p>
	 *
	 * @param sleepTime a long.
	 */
	public static void sleepIgnoreException(long sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException ignored) {}
	}
	
}
