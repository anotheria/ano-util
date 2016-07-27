package net.anotheria.util.network;

import net.anotheria.util.StringUtils;

/**
 * Storage class for a single ip range. Ip range is specified by an ip
 * adress and a byte mask. 10.0.0.0/8, 192.168.0.0/16 or 127.0.0.1/8 are
 * examples for private networks.
 * 
 * @author lrosenberg
 */

public class IPRange {
	
	/**
	 * Network mask.
	 */
	private long networkMask;
	
	/**
	 * Masked binary IP.
	 */
	private long maskedIp;

	/**
	 * Creates new IPRange with given ipAdress and mask.
	 */
	public IPRange(String ipAdress, int mask) {
		maskedIp = convertIp(ipAdress);
		// System.out.println("ipAdress: "+ipAdress+" -> "+networkIp);
		networkMask = (long) StrictMath.pow(2, mask) - 1;
		networkMask <<= 32 - mask;
		// System.out.print(networkIp + " --> ");
		maskedIp &= networkMask;
		// System.out.println(networkIp);

	}

	/**
	 * Check whether or not ip param is in the current range.
	 * @param ip to check
	 * @return true if ip param in the current range
	 */
	public boolean mayPass(String ip) {
		long myIp = convertIp(ip);
		return (myIp & networkMask) == maskedIp;
	}

	/**
	 * Converts ipAddress from dotted-decimal notation to binary.
	 * @param ipAdress as dotted-decimal
	 * @return ip address converted to binary form
	 */
	private static long convertIp(String ipAdress) {
        String[] tokens = StringUtils.tokenize(ipAdress, '.');
		if (tokens == null || tokens.length != 4)
			throw new RuntimeException("Illegal ip format: " + ipAdress);
		int[] ipAsByte = new int[4]; // int because byte is signed
		for (int i = 0; i < tokens.length; i++)
			ipAsByte[i] = Integer.parseInt(tokens[i]);
        long result = 0;
        for (int i = 0; i < ipAsByte.length; i++) {
			if (i > 0)
				result <<= 8;
			result |= ipAsByte[i];
		}
		return result;

	}

	@Override
	public String toString() {
		return "nIP: " + maskedIp + ", nM: " + networkMask;
	}
}
