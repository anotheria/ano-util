package net.anotheria.util.network;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IPRangeTest {

	@Test
	public void mayPassTest() {
		assertTrue(new IPRange("192.168.0.1", 32).mayPass("192.168.0.1"));
		assertFalse(new IPRange("192.168.0.1", 32).mayPass("192.168.0.0"));
		assertTrue(new IPRange("192.168.0.1", 31).mayPass("192.168.0.0"));
		
		assertFalse(new IPRange("192.168.1.1", 32).mayPass("192.168.0.1"));
		assertFalse(new IPRange("192.168.1.1", 24).mayPass("192.168.0.1"));
		assertTrue(new IPRange("192.168.0.1", 23).mayPass("192.168.0.1"));
		assertTrue(new IPRange("192.168.1.1", 23).mayPass("192.168.0.1"));
		assertFalse(new IPRange("192.168.2.0", 23).mayPass("192.168.0.1"));
		assertTrue(new IPRange("192.168.2.0", 22).mayPass("192.168.0.1"));
		assertFalse(new IPRange("192.168.2.1", 23).mayPass("192.168.0.1"));
		assertTrue(new IPRange("192.168.2.1", 22).mayPass("192.168.0.1"));
		
	}

	@Test
	public void testWrongRange() {

		try {
			new IPRange("asdfasd", 10);
			fail("Must be thrown an exception: is not IP");
		} catch (Exception error) {}

		try {
			new IPRange("192.168.a.1", 10);
			fail("Must be thrown an exception: IP must be only from digits");
		} catch (Exception error) {}

		try {
			new IPRange("192.168.0.1.1", 10);
			fail("Must be thrown an exception: IP from 5 octaves is wrong");
		} catch (Exception error) {}

		try {
			new IPRange("192.168.0", 10);
			fail("Must be thrown an exception: IP from 3 octaves is wrong");
		} catch (Exception error) {}

//		try {
//			new IPRange("192.168.0.1.", 10);
//			fail("Must be thrown an exception: IP with dot at end is wrong");
//		} catch (Exception error) {}

//		try {
//			new IPRange("192.168.256.1", 10);
//			fail("Must be thrown an exception: max IP octave value is 255");
//		} catch (Exception error) {}

//		try {
//			new IPRange("192.168.0.1", 33);
//			fail("Must be thrown an exception: max Mask is 32");
//		} catch (Exception error) {}
//
//		try {
//			new IPRange("192.168.0.1", -1);
//			fail("Must be thrown an exception: min Mask is 0");
//		} catch (Exception error) {}

	}
}
