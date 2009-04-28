package net.anotheria.util;

import org.junit.Test;
import static junit.framework.Assert.*;

public class NumberUtilsTest {
	@Test public void testItoa(){
		for (int i=1; i<10; i++)
			testAnItoa(i);
		
		assertEquals(NumberUtils.itoa(1), "01");
		assertEquals(NumberUtils.itoa(0), "00");
	}
	
	private void testAnItoa(int number){
		String a = NumberUtils.itoa(number, number);
		assertNotNull(a);
		assertEquals(a.length(), number);
		
	}
	
	@Test public void testDot(){
		assertEquals("1", NumberUtils.getDotedNumber(1));
		assertEquals("21", NumberUtils.getDotedNumber(21));
		assertEquals("321", NumberUtils.getDotedNumber(321));
		assertEquals("4.321", NumberUtils.getDotedNumber(4321));
		assertEquals("54.321", NumberUtils.getDotedNumber(54321));
		assertEquals("654.321", NumberUtils.getDotedNumber(654321));
		assertEquals("7.654.321", NumberUtils.getDotedNumber(7654321));
		assertEquals("87.654.321", NumberUtils.getDotedNumber(87654321));
		assertEquals("987.654.321", NumberUtils.getDotedNumber(987654321));
		assertEquals("1.987.654.321", NumberUtils.getDotedNumber(1987654321));
	}
	
	@Test public void testDate(){
		long reference = new Date(10,10,2004, 12,12).toMill();
		long reference2 = new Date(29,02,2004, 23, 12).toMill();
		assertEquals("10.10.2004", NumberUtils.makeDigitalDateStringLong(reference));
		//assertEquals("10.10.04", NumberUtils.makeDigitalDateString(reference.toMill()));

		assertEquals("12:12", NumberUtils.makeTimeString(reference));
		assertEquals("23:12", NumberUtils.makeTimeString(reference2));
		
		assertEquals("12:12 am", NumberUtils.makeTimeString12H(reference));
		assertEquals("11:12 pm", NumberUtils.makeTimeString12H(reference2));
	}
}
