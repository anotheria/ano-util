package net.anotheria.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import static junit.framework.Assert.*;

public class IdCodeGeneratorTest {
	
	@Test public void generate20Chars(){
		String code = IdCodeGenerator.generateCode(20);
		assertTrue("Code is not 20 chars long", code.length()==20);
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
		List<Character> pattern = new ArrayList<Character>(); 
		for (char c : new char[]{'a','b','c'})
			pattern.add(c);
		for (int i = 0; i<code.length(); i++){
			assertFalse(pattern.indexOf(code.charAt(i))==-1);
		}
		
	}
}
