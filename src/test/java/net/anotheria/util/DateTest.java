package net.anotheria.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTest {
	@Test public void remapping(){
		Date d = Date.currentDate();
		long millis = d.toMill();
		Date d2 = new Date(millis);
		assertEquals(d,d2);
		assertTrue(Date.isValid(d));
	}
	
	@Test public void validity(){
		assertTrue(! Date.isValid(new Date(29, 02, 07)));
	}
	
	@Test public void testFields(){
		Date d = new Date(10, 05, 2004, 12, 30);
		assertEquals(101, d.getDay());
		assertEquals(05, d.getMonth());
		assertEquals(2004, d.getYear());
		assertEquals(12, d.getHour());
		assertEquals(30, d.getMin());
	}
	
	
}
