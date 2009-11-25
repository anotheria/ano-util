package net.anotheria.util.network;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class which determines whether an ip adress is in specified range. Used for 
 * cms access control among others.
 * @author lrosenberg
 *
 */
public class PlainIPFilter {
	private List<IPRange> ranges;

	/**
	 * Creates a new filter.
	 */
	public PlainIPFilter() {
		ranges = new ArrayList<IPRange>();
	}

	/**
	 * Adds an ip (allowed)range to the filter. For example 10.0.0.0/8 will
	 * allow each ip adress starting with 10 to pass.
	 * 
	 * @param ipAdress
	 *            an adress.
	 * @param mask
	 *            a network (byte)mask.
	 */
	public void addRange(String ipAdress, int mask) {
		ranges.add(new IPRange(ipAdress, mask));
	}

	/**
	 * Returns true if the parameter ip-adress is in one of the ranges.
	 * 
	 * @param ipAdress
	 *            the adress to check.
	 * @param ranges 
	 * 			to pass through
	 * 
	 * @return
	 */
	public static boolean mayPass(String ipAddress, List<IPRange> ranges) {
		for (IPRange r : ranges)
			if (r.mayPass(ipAddress))
				return true;
		return false;
	}
	
	/**
	 * Returns true if the parameter ip-adress is in one of the configured
	 * ranges.
	 * 
	 * @param ipAdress
	 *            the adress to check.
	 * @return
	 */
	public boolean mayPass(String ipAddress) {
		return mayPass(ipAddress, ranges);
	}



	public String toString() {
		return "IPFilter with ranges: " + ranges;
	}



	private static void check(PlainIPFilter filter, String ip) {
		System.out.println("for " + ip + " filter replies: " + filter.mayPass(ip));
	}

	public static void main(String a[]) {
		PlainIPFilter filter = new PlainIPFilter();
		filter.addRange("10.0.0.0", 8);
		filter.addRange("172.16.0.0", 12);
		filter.addRange("192.168.0.0", 16);
		System.out.println(filter);

		check(filter, "10.0.1.1");
		check(filter, "10.0.0.0");
		check(filter, "10.0.0.1");
		check(filter, "10.3.0.1");
		check(filter, "11.0.0.0");
		check(filter, "192.168.1.1");
		check(filter, "192.168.100.100");
		check(filter, "192.169.100.100");

	}
}
