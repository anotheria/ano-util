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
	
	@Test public void testSurrounding(){
		String hello = "{Hello!}";
		assertTrue(StringUtils.isSurroundedWith(hello, '{', '}'));
		assertTrue(!StringUtils.isSurroundedWith(hello, '{', ']'));
		assertTrue(!StringUtils.isSurroundedWith(hello, '[', '}'));
		assertEquals("Hello!", StringUtils.removeSurround(hello));
		assertEquals("[Hello!]", StringUtils.surroundWith(StringUtils.removeSurround(hello), '[', ']'));
	}
	

	@Test public void test6(){
		String source = "Hello! You have {mc:count:0} new matching. Details {if:{equals:{mp:persons_size}:2}:Gender{mp:gender2}Age{mp:age2}}";
		List<String> index = StringUtils.indexSuperTags(source, '{', '}');
		System.out.println(StringUtils.concatenateTokens(index, ',', '<', '>'));
	}
	
	@Test public void test2(){
		System.out.println("TESTING excractSuperTags()");
		String source = "{if:{equals:{mp:persons_size}:2}:Gender{mp:gender2}Age{mp:age2}}";
		char tagStart = '{';
		char tagEnd = '}';
		char escapeChar = '\\';
		List<String> tags = StringUtils.extractSuperTags(source, tagStart, tagEnd, escapeChar);
		for(String t: tags)
			System.out.println(t);
		
//		String src = "{if:true:mumu|:bubu}";
//		MatchedProfilesProcessor p = new MatchedProfilesProcessor();
//		p.setAccountId("267");
//		VariablesUtility.addProcessor(MatchedProfilesProcessor.PREFIX, p);
//		System.out.println(VariablesUtility.replaceVariables(null, src));
//		List<String> t = StringUtils.extractSuperTags(src,'{','}','|');
//		for(String s:t)
//			System.out.println(s);
	}
	

	@Test public void removeImgTag(){
		assertEquals("blabla", StringUtils.removeImgTag("bla<img src=\"xxx\">bla"));
	}
}
 