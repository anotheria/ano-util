package net.anotheria.util.network;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PlainIPFilterTest {

	@Test
	public void test() {
		PlainIPFilter filter = new PlainIPFilter();
		filter.addRange("10.0.0.0", 8);
		filter.addRange("172.16.0.0", 12);
		filter.addRange("192.168.0.0", 16);

		assertTrue(filter.mayPass("10.0.1.1"));
		assertTrue(filter.mayPass("10.0.0.0"));
		assertTrue(filter.mayPass("10.0.0.1"));
		assertTrue(filter.mayPass("10.3.0.1"));
		assertFalse(filter.mayPass("11.0.0.0"));
		assertTrue(filter.mayPass("192.168.1.1"));
		assertTrue(filter.mayPass("192.168.100.100"));
		assertFalse(filter.mayPass("192.169.100.100"));
		
		List<IPRange> ranges = new ArrayList<IPRange>();
		ranges.add(new IPRange("10.0.0.0", 8));
		ranges.add(new IPRange("172.16.0.0", 12));
		ranges.add(new IPRange("192.168.0.0", 16));
		
		assertTrue(PlainIPFilter.mayPass("10.0.1.1",ranges));
		assertTrue(PlainIPFilter.mayPass("10.0.0.0",ranges));
		assertTrue(PlainIPFilter.mayPass("10.0.0.1",ranges));
		assertTrue(PlainIPFilter.mayPass("10.3.0.1",ranges));
		assertFalse(PlainIPFilter.mayPass("11.0.0.0",ranges));
		assertTrue(PlainIPFilter.mayPass("192.168.1.1",ranges));
		assertTrue(PlainIPFilter.mayPass("192.168.100.100",ranges));
		assertFalse(PlainIPFilter.mayPass("192.169.100.100",ranges));
	}
}


