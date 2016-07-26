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
		ranges = new ArrayList<>();
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
	public static boolean mayPass(String ipAddress, Iterable<IPRange> ranges) {
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

	@Override public String toString() {
		return "IPFilter with ranges: " + ranges;
	}
}
