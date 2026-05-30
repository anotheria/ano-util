package net.anotheria.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&quot;", CharacterEntityCoder.htmlEncodeString("\""));
		assertEquals("&amp;", CharacterEntityCoder.htmlEncodeString("&"));
	}
}
