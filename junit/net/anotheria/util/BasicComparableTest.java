package net.anotheria.util;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class BasicComparableTest {
	@Test public void testString(){
		String a = "aaa";
		String b = "bbb";
		
		assertTrue(BasicComparable.compareString(a, b)<0);
		assertTrue(BasicComparable.compareString(a, a)==0);
		assertTrue(BasicComparable.compareString(b, b)==0);
		assertTrue(BasicComparable.compareString(b, a)>0);
		
		assertTrue(BasicComparable.compareString(null, null)==0);
		assertTrue(BasicComparable.compareString("", "")==0);
		assertTrue(BasicComparable.compareString("", a)<0);
		assertTrue(BasicComparable.compareString("", b)<0);
		assertTrue(BasicComparable.compareString(a, "")>0);
		assertTrue(BasicComparable.compareString(b, "")>0);
		assertTrue(BasicComparable.compareString(null, a)<0);
		assertTrue(BasicComparable.compareString(null, b)<0);
		assertTrue(BasicComparable.compareString(a, null)>0);
		assertTrue(BasicComparable.compareString(b, null)>0);
		
		assertTrue(BasicComparable.compareString(a, b.toUpperCase())<0);
		assertTrue(BasicComparable.compareString(a, a)==0);
		assertTrue(BasicComparable.compareString(b, b.toUpperCase())==0);
		assertTrue(BasicComparable.compareString(b.toUpperCase(), a)>0);
	}
	
	@Test public void testInt(){
		int a = 10;
		int b = 15;
		
		assertTrue(BasicComparable.compareInt(a, b)<0);
		assertTrue(BasicComparable.compareInt(a, a)==0);
		assertTrue(BasicComparable.compareInt(b, b)==0);
		assertTrue(BasicComparable.compareInt(b, a)>0);
		
	}
	
	@Test public void testLong(){
		long a = 10;
		long b = 15;
		
		assertTrue(BasicComparable.compareLong(a, b)<0);
		assertTrue(BasicComparable.compareLong(a, a)==0);
		assertTrue(BasicComparable.compareLong(b, b)==0);
		assertTrue(BasicComparable.compareLong(b, a)>0);
		
	}

	@Test public void testBoolean(){
		boolean a = true;
		boolean b = false;
		
		assertTrue(BasicComparable.compareBoolean(a, b)<0);
		assertTrue(BasicComparable.compareBoolean(a, a)==0);
		assertTrue(BasicComparable.compareBoolean(b, b)==0);
		assertTrue(BasicComparable.compareBoolean(b, a)>0);
		
	}

	@Test public void testFloat(){
		float a = 10.1f;
		float b = 15.2f;
		
		assertTrue(BasicComparable.compareFloat(a, b)<0);
		assertTrue(BasicComparable.compareFloat(a, a)==0);
		assertTrue(BasicComparable.compareFloat(b, b)==0);
		assertTrue(BasicComparable.compareFloat(b, a)>0);
	}

	@Test public void testDouble(){
		double a = 10.1;
		double b = 15.2;
		
		assertTrue(BasicComparable.compareDouble(a, b)<0);
		assertTrue(BasicComparable.compareDouble(a, a)==0);
		assertTrue(BasicComparable.compareDouble(b, b)==0);
		assertTrue(BasicComparable.compareDouble(b, a)>0);
	}
}
