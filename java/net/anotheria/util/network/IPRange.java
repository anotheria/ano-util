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
	private long networkMask;
	private long networkIp;

	public IPRange(String ipAdress, int mask) {
		networkIp = convertIp(ipAdress);
		// System.out.println("ipAdress: "+ipAdress+" -> "+networkIp);
		networkMask = (long) Math.pow(2, mask) - 1;
		networkMask <<= 32 - mask;
		// System.out.print(networkIp + " --> ");
		networkIp &= networkMask;
		// System.out.println(networkIp);

	}

	public boolean mayPass(String ip) {
		long myIp = convertIp(ip);
		return (myIp & networkMask) == networkIp;
	}

	private long convertIp(String ipAdress) {
		long result = 0;
		String tokens[] = StringUtils.tokenize(ipAdress, '.');
		if (tokens == null || tokens.length != 4)
			throw new RuntimeException("Illegal ip format: " + ipAdress);
		int[] ipAsByte = new int[4]; // int because byte is signed
		for (int i = 0; i < tokens.length; i++)
			ipAsByte[i] = Integer.parseInt(tokens[i]);
		for (int i = 0; i < ipAsByte.length; i++) {
			if (i > 0)
				result <<= 8;
			result |= ipAsByte[i];
		}
		return result;

	}

	public String toString() {
		return "nIP: " + networkIp + ", nM: " + networkMask;
	}
}
