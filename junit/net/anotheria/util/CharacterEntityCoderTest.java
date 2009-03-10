package net.anotheria.util;

import static junit.framework.Assert.*;
import org.junit.Test;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&ouml;", CharacterEntityCoder.htmlEncodeString("š"));
		assertEquals("&Šuml;", CharacterEntityCoder.htmlEncodeString("Š"));
		assertEquals("&Ÿuml;", CharacterEntityCoder.htmlEncodeString("Ÿ"));
		assertEquals("&tzlig;", CharacterEntityCoder.htmlEncodeString("§"));
	}
}
