package net.anotheria.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Tools for obtaining and manipulation with Process Id (PID) of current JVM on
 * the family of *nix OS. Other OS are not supported and ignored.
 *
 * @author another
 * @version $Id: $Id
 */
public final class PidTools {

	/**
	 * Logger
	 */
	static final Logger log = LoggerFactory.getLogger(PidTools.class);

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

		try (BufferedWriter out = new BufferedWriter(new FileWriter(pidFile))) {
			out.write(String.valueOf(pid));
			out.flush();
		} catch (IOException e) {
            log.error("Could not write PID '"+pid+"' into file '"+pidFile+ '\'', e);
		}
	}

}
