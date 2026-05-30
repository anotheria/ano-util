package net.anotheria.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdCodeGeneratorTest {

	@Test public void generate20Chars(){
		String code = IdCodeGenerator.generateCode(20);
		assertTrue(code.length()==20, "Code is not 20 chars long");
	}

	@Test public void generate30Chars(){
		String code = IdCodeGenerator.generateCode(30);
		System.out.println(code);
		assertTrue(code.length()==30, "Code is not 30 chars long");
	}

	@Test public void randomLength(){
		Random rnd = new Random(System.currentTimeMillis());
		for (int i=0; i<100; i++){
			int l = rnd.nextInt(100);
			String code = IdCodeGenerator.generateCode(l);
			assertEquals(l, code.length());
		}

	}

	@Test public void testZeroAndNegative(){
		String code = IdCodeGenerator.generateCode(-1);
		assertEquals("", code);

		code = IdCodeGenerator.generateCode(0);
		assertEquals("", code);
	}

	@Test public void aaaaaaaaaa(){
		String code = IdCodeGenerator.generateCustomCode(new char[]{'a'}, 10);
		assertEquals(10, code.length());
		assertEquals("aaaaaaaaaa", code);
	}

	@Test public void abc(){
		String code = IdCodeGenerator.generateCustomCode(new char[]{'a','b','c'}, 10);
		assertEquals(10, code.length());
		List<Character> pattern = new ArrayList<>();
		for (char c : new char[]{'a','b','c'})
			pattern.add(c);
		for (int i = 0; i<code.length(); i++){
			assertFalse(pattern.indexOf(code.charAt(i))==-1);
		}
	}

	@Test public void defaultLength(){
		String code = IdCodeGenerator.generateCode();
		assertEquals(IdCodeGenerator.CODE_LENGTH, code.length(), "Default length is broken");
		assertTrue(code!=null, "Default length is null");
		assertTrue(!code.isEmpty(), "Default length is zero");
	}
}
