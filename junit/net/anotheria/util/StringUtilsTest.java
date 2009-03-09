package net.anotheria.util;

import static junit.framework.Assert.*;

import org.junit.Test;

public class StringUtilsTest {
	@Test public void normalize(){
		String test = "abc123efg";
		assertEquals("abc___efg", StringUtils.normalize(test));
	}
}
 