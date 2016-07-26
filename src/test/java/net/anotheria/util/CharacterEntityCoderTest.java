package net.anotheria.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&quot;", CharacterEntityCoder.htmlEncodeString("\""));
		assertEquals("&amp;", CharacterEntityCoder.htmlEncodeString("&"));
	}
}
