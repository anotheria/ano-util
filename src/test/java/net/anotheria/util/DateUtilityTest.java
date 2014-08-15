package net.anotheria.util;

import org.junit.Test;

import static junit.framework.Assert.*;


public class DateUtilityTest {
	@Test public void dateOrder(){
		Date now = Date.currentDate();
		Date next = DateUtility.nextDate(now);
		Date prev = DateUtility.previousDate(now);
		Date same1 = DateUtility.nextDate(prev);
		Date same2 = DateUtility.previousDate(next);
		
		assertTrue(DateUtility.isSame(now, same1));
		assertTrue(DateUtility.isSame(now, same2));
		assertTrue(DateUtility.isSame(same2, same1));

		assertFalse(DateUtility.isSame(next, same1));
		assertFalse(DateUtility.isSame(prev, same1));
		assertFalse(DateUtility.isSame(same2, next));
		assertFalse(DateUtility.isSame(same2, prev));
		
		assertTrue(DateUtility.isAfter(next, prev));
		assertTrue(DateUtility.isAfter(next, same1));
		assertTrue(DateUtility.isAfter(next, same2));
		assertTrue(DateUtility.isAfter(next, now));

		assertFalse(DateUtility.isAfter(prev, next));
		assertFalse(DateUtility.isAfter(same1, next));
		assertFalse(DateUtility.isAfter(same2, next));
		assertFalse(DateUtility.isAfter(now, next));
		
		assertFalse(DateUtility.isAfter(prev, prev));
		assertFalse(DateUtility.isAfter(next, next));
		assertFalse(DateUtility.isAfter(same1, same1));
		assertFalse(DateUtility.isAfter(same2, same2));
		assertFalse(DateUtility.isAfter(now, now));

		assertFalse(DateUtility.isBefore(prev, prev));
		assertFalse(DateUtility.isBefore(next, next));
		assertFalse(DateUtility.isBefore(same1, same1));
		assertFalse(DateUtility.isBefore(same2, same2));
		assertFalse(DateUtility.isBefore(now, now));

		assertFalse(DateUtility.isBefore(next, prev));
		assertFalse(DateUtility.isBefore(next, same1));
		assertFalse(DateUtility.isBefore(next, same2));
		assertFalse(DateUtility.isBefore(next, now));

		assertTrue(DateUtility.isBefore(prev, next));
		assertTrue(DateUtility.isBefore(same1, next));
		assertTrue(DateUtility.isBefore(same2, next));
		assertTrue(DateUtility.isBefore(now, next));
	}
	
	@Test public void ageTest(){
		Date fromDate = new Date(10,5,1984);
		
		//full 24 years
		Date toDate = new Date(9,4,2009);
		assertEquals(24,DateUtility.getAge(fromDate, toDate));
		assertEquals(24,DateUtility.getAge(fromDate.toMill(), toDate.toMill()));
		
		//exactly 25 years
		toDate = new Date(10,5,2009);
		assertEquals(25,DateUtility.getAge(fromDate, toDate));
		assertEquals(25,DateUtility.getAge(fromDate.toMill(), toDate.toMill()));
		
		//full 25 years
		toDate = new Date(11,5,2009);
		assertEquals(25,DateUtility.getAge(fromDate, toDate));
		assertEquals(25,DateUtility.getAge(fromDate.toMill(), toDate.toMill()));
		
		//age till now
		toDate = new Date(System.currentTimeMillis());
		assertEquals(DateUtility.getAge(fromDate, toDate),DateUtility.getAge(fromDate));
		assertEquals(DateUtility.getAge(fromDate.toMill(), toDate.toMill()),DateUtility.getAge(fromDate.toMill()));
	}
}
