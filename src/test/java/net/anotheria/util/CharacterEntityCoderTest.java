package net.anotheria.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&quot;", CharacterEntityCoder.htmlEncodeString("\""));
		assertEquals("&amp;", CharacterEntityCoder.htmlEncodeString("&"));
	}
}
