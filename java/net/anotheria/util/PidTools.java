package net.anotheria.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * Tools for obtaining and manipulation with Process Id (PID) of current JVM on
 * the family of *nix OS. Other OS are not supported and ignored.
 */
public class PidTools {

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
	public static int getPid() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		OutputStream o = null;
		Process p = null;

		try {
			String[] cmd = { "/bin/bash", "-c", "echo $PPID" };
			p = Runtime.getRuntime().exec(cmd);
			log.debug("p=" + p);

			isr = new InputStreamReader(p.getInputStream());
			br = new BufferedReader(isr);
			o = p.getOutputStream();

			String sPid = br.readLine();
			log.debug("PID String of Java application is " + sPid);

			try {
				int pid = Integer.parseInt(sPid);
				log.debug("PID=" + pid);
				return pid;
			} catch (NumberFormatException e) {
				log.error(sPid + " is not a valid PID...");
			}

			return -1;
		} catch (Exception e) {
			log.error("Could not determine PID: " + e);
			return -1;
		} finally {
			if (o != null)
				try {
					o.close();
				} catch (Exception e) {
				}
			if (br != null)
				try {
					br.close();
				} catch (Exception e) {
				}

			if (p != null) {
				try {
					p.getInputStream().close();
				} catch (Exception e) {
				}
				try {
					p.getOutputStream().close();
				} catch (Exception e) {
				}

			}
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
