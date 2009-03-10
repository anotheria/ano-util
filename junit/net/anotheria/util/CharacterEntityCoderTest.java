package net.anotheria.util;

import static junit.framework.Assert.*;
import org.junit.Test;

public class CharacterEntityCoderTest {
	@Test public void testHtmlEncodeString(){
		assertEquals("&ouml;", CharacterEntityCoder.htmlEncodeString("�"));
		assertEquals("&�uml;", CharacterEntityCoder.htmlEncodeString("�"));
		assertEquals("&�uml;", CharacterEntityCoder.htmlEncodeString("�"));
		assertEquals("&tzlig;", CharacterEntityCoder.htmlEncodeString("�"));
	}
}
