package net.anotheria.util;

import static junit.framework.Assert.*;
import org.junit.Test;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&quot;", CharacterEntityCoder.htmlEncodeString("\""));
		assertEquals("&amp;", CharacterEntityCoder.htmlEncodeString("&"));
	}
}
