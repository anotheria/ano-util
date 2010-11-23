package net.anotheria.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.apache.log4j.Logger;

/**
 * Tools for obtaining and manipulation with Process Id (PID) of current JVM on
 * the family of *nix OS. Other OS are not supported and ignored.
 */
public final class PidTools {

	/**
	 * Logger
	 */
	static final Logger log = Logger.getLogger(PidTools.class);

	/**
	 * Private constructor to prevent instantiation
	 */
	private PidTools() {
	}

	/**
	 * Returns PID for current JVM under *nix OS or -1 if PID could not
	 * determine.
	 * 
	 * @return PID of the current JVM
	 */
	public static final int getPid() {
		String processName = ManagementFactory.getRuntimeMXBean().getName();
		if (processName==null)
			return -1;
		int indexOfAtt = processName.indexOf('@');
		if (indexOfAtt==-1)
			return -1;
		
		try{
			return Integer.parseInt(processName.substring(0, indexOfAtt));
		}catch(NumberFormatException e){
			return -1;
		}
	}

	/**
	 * Writes PID of the current JVM to file set by "pidfile" System Property or
	 * "logs/file" if property was not initialized.
	 */
	public static void logPid() {
		int pid = getPid();
		if (pid <= 0)
			return;

		String pidFile = System.getProperty("pidfile");
		if (pidFile == null)
			pidFile = "logs/pid";

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(pidFile));
			out.write(pid + "");
			out.flush();
		} catch (IOException e) {
			log.error("Could not write PID " + pid + " into file " + pidFile + ": " + e, e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
				}

		}
	}

	public static void main(String[] args) {
		System.out.println("My PID: " + getPid());
	}

}
