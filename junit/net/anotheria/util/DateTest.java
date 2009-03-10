package net.anotheria.util;

import org.junit.Test;
import static junit.framework.Assert.*;

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
}
