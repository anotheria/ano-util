package net.anotheria.util;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {
	@Test public void normalize(){
		String test = "abc123efg";
		assertEquals("abc___efg", StringUtils.normalize(test));
	}

	@Test public void concatenate(){
		System.out.println("TESTING concatenateTokens():");
		List<String> hellos = new ArrayList<String>(4);
		hellos.add("Hello, World!");
		hellos.add("Hello, People!");
		hellos.add("Hello, Aliens!");
		System.out.println("Concatenate Hellos Tokens with delimiter , and surround with <>:" + StringUtils.concatenateTokens(hellos, ',', '<', '>'));
	
		assertEquals("1.2.3", StringUtils.concatenateTokens(".", "1","2","3"));
	}
	
	@Test public void tokenize(){
		String src = "if:true:mumu|:bubu";
		String[] ts = StringUtils.tokenize(src, ':', '|');
		assertEquals("Array length is not 3 (escape doesn't work?)", 3, ts.length);
		assertEquals("Array length is not 4 (tokenize doesn't work?)", 4, StringUtils.tokenize("if:true:mumu:bubu", ':').length);
	}

}
 