package net.anotheria.util;

public class ThreadUtils {

	public static void waitIgnoreException(Object monitor){
		waitIgnoreException(monitor, 0);
	}
	
	public static void waitIgnoreException(Object monitor, long timeout){
		try {
			monitor.wait(timeout);
		} catch (InterruptedException ignored) {}
	}
	
	public static void sleepIgnoreException(long sleepTime){
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException ignored) {}
	}
	
}
