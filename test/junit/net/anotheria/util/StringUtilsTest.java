package net.anotheria.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class StringUtilsTest {
	@Test public void normalize(){
//		String test = "abc123efg mümüßz MA*^&ZM_";
//		assertEquals("abc123efg_m_m__z_MA___ZM_", StringUtils.normalize(test));
//		assertEquals("abc123efg_mümüßz_MA___ZM_", StringUtils.normalize(test));
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
		String[] expectedTokens = new String[]{"if", "true", "mumu:bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu|:bubu", ':', '|'));
	
		expectedTokens = new String[]{"if","true","mumu","bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu", ':'));
		
		expectedTokens = new String[]{"if","true","mumu", "","bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu::bubu", ':'));
		
		expectedTokens = new String[]{"","if","true","mumu", "bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize(":if:true:mumu:bubu", ':'));
		
//		expectedTokens = new String[]{"if","true","mumu", "bubu", ""};
//		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu:", ':'));
		
		//tokenize(String source, boolean ignoreEmptyTokens, char delimiter)
		expectedTokens = new String[]{"if","true","mumu","bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu", true, ':'));
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu", false, ':'));
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu::bubu", true, ':'));
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu:", true, ':'));
		assertArrayEquals(expectedTokens, StringUtils.tokenize(":if:true:mumu:bubu", true, ':'));
		
		expectedTokens = new String[]{"if","true","mumu", "","bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu::bubu", false, ':'));
		
		expectedTokens = new String[]{"","if","true","mumu", "bubu"};
		assertArrayEquals(expectedTokens, StringUtils.tokenize(":if:true:mumu:bubu", false, ':'));
		
		expectedTokens = new String[]{"if","true","mumu", "bubu", ""};
		assertArrayEquals(expectedTokens, StringUtils.tokenize("if:true:mumu:bubu:", false, ':'));
	}
	
	@Test public void _tokenize(){
		String srcSimple = "token0:token1:token2";		
		List<String> ts = StringUtils._tokenize(srcSimple, '"','"',':');
		assertEquals("Array length is not 3 (tokenize doesn't work?)", 3, ts.size());
		assertEquals("Wrong token 0 (tokenize doesn't work?)", "token0", ts.get(0));
		assertEquals("Wrong token 1 (tokenize doesn't work?)", "token1", ts.get(1));
		assertEquals("Wrong token 2 (tokenize doesn't work?)", "token2", ts.get(2));

		String srcWithEscape = "token0:\"token1:escaped\":token2";
		ts = StringUtils._tokenize(srcWithEscape, '"','"',':');
		assertEquals("Array length is not 3 (escape doesn't work?)", 3, ts.size());
		assertEquals("Wrong token 0 (tokenize doesn't work?)", "token0", ts.get(0));
		assertEquals("Wrong token 1 (tokenize doesn't work?)", "\"token1:escaped\"", ts.get(1));
		assertEquals("Wrong token 2 (tokenize doesn't work?)", "token2", ts.get(2));
		
		
		String srcWithEmptyToken = "token0::token2";
		ts = StringUtils._tokenize(srcWithEmptyToken, '"','"',':');
		assertEquals("Array length is not 3 (skipping empty tokens doesn't work?)", 2, ts.size());
		assertEquals("Wrong token 0 (tokenize doesn't work?)", "token0", ts.get(0));
//		assertEquals("Wrong token 1 (tokenize doesn't work?)", "token1:escaped", ts.get(1));
		assertEquals("Wrong token 2 (tokenize doesn't work?)", "token2", ts.get(1));
		
		ts = StringUtils._tokenize(srcWithEmptyToken, '"','"',false,':');
		assertEquals("Array length is not 3 (empty tokens is skipped?)", 3, ts.size());
		assertEquals("Wrong token 0 (tokenize doesn't work?)", "token0", ts.get(0));
		assertEquals("Wrong token 1 (tokenize doesn't work?)", "", ts.get(1));
		assertEquals("Wrong token 2 (tokenize doesn't work?)", "token2", ts.get(2));
		
		String srcComplex = "token0:\"token1:escaped\"::token3:";
		ts = StringUtils._tokenize(srcComplex, '"','"',false,':');
		assertEquals("Array length is not 3 (tokenize doesn't work?)", 4, ts.size());
		assertEquals("Wrong token 0 (tokenize doesn't work?)", "token0", ts.get(0));
		assertEquals("Wrong token 1 (tokenize doesn't work?)", "\"token1:escaped\"", ts.get(1));
		assertEquals("Wrong token 1 (tokenize doesn't work?)", "", ts.get(2));
		assertEquals("Wrong token 2 (tokenize doesn't work?)", "token3", ts.get(3));
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
	
	@SuppressWarnings("unchecked")
	@Test public void concatenateTokens(){
		assertEquals("t1||t2||t3", StringUtils.concatenateTokens("||", "t1","t2","t3"));
		assertEquals("t1||t2||t3", StringUtils.concatenateTokens("||", new String[]{"t1","t2","t3"}));
		
		assertEquals("1||2||3", StringUtils.concatenateTokens("||", 1,2,3));
		assertEquals("1||2||3", StringUtils.concatenateTokens("||", new int[]{1,2,3}));
		assertEquals("1||2||3", StringUtils.concatenateTokens("||", new Integer[]{1,2,3}));
		
		assertEquals("1||2||3", StringUtils.concatenateTokens("||", 1,"2",3));
	}

	@Test public void removeImgTag(){
		assertEquals("blabla", StringUtils.removeImgTag("bla<img src=\"xxx\">bla"));
	}
	
	@Test public void escape(){
		String src = "Mumu \"nono\" and 'c' and \\";
		assertEquals("Mumu \\\"nono\\\" and \\'c\\' and \\\\", StringUtils.escape(src, '\\','\'','"'));
	}
	
	@Test public void replace(){
		String src = "fuubar";
		assertEquals("foobar", StringUtils.replace(src, 'u', 'o'));
		Map<String,String> replacement = new HashMap<String, String>();
		replacement.put("u", "o");
		replacement.put("o", "u");
		replacement.put("b", "B");
		replacement.put("f", "F");
		assertEquals("FooBar", StringUtils.replace(src, replacement));
	}
	
	@Test public void insertTest(){
		final String srcBefore = "beforeInsertion";
		final String srcAfter = "AfterInsertion";
		final String src = srcBefore + srcAfter;
		final String insertion = "_INSERTION_";
		
		String result = StringUtils.insert(src, insertion, srcBefore.length());
		assertEquals(srcBefore + insertion + srcAfter, result);
		
		result = StringUtils.insert(srcAfter, insertion, 0);
		assertEquals(insertion + srcAfter, result);
		
		result = StringUtils.insert(srcBefore, insertion, srcBefore.length());
		assertEquals(srcBefore + insertion, result);
		
		result = StringUtils.insert(src, "", srcBefore.length());
		assertEquals(src, result);
		
		try{
			StringUtils.insert(src, insertion, src.length() + 1);
			fail("Must be throwed Exception exception");
		}catch(IndexOutOfBoundsException ignored){
		}
		
		try{
			StringUtils.insert(src, insertion, -1);
			fail("Must be throwed Exception exception");
		}catch(IndexOutOfBoundsException ignored){
		}
	}
	
	@Test public void removeTest(){
		final String src_1 = "beforeInsertion";
		final String src_3 = "AfterInsertion";
		
		final String src_2 = "_ROMOVING_";
		final String src = src_1 + src_2 + src_3;
		
		String result = StringUtils.remove(src, 0, src_1.length());
		assertEquals(src_2 + src_3, result);

		result = StringUtils.remove(src, src_1.length(), src_2.length());
		assertEquals(src_1 + src_3, result);
		
		
		result = StringUtils.remove(src, 0, src_1.length());
		assertEquals(src_2 + src_3, result);
		
		result = StringUtils.remove(src, src_1.length() + src_2.length(), src_3.length());
		assertEquals(src_1 + src_2, result);
		
		result = StringUtils.remove(src, 0, 0);
		assertEquals(src, result);
		
		try{
			StringUtils.remove(src, -1, 1);
			fail("Must be throwed Exception exception");
		}catch(IndexOutOfBoundsException ignored){
		}
		
		try{
			StringUtils.remove(src, 1000, 10001);
			fail("Must be throwed Exception exception");
		}catch(IndexOutOfBoundsException ignored){
		}
		
		try{
			StringUtils.remove(src, 0, -1);
			fail("Must be throwed Exception exception");
		}catch(IndexOutOfBoundsException ignored){
		}
		
		result = StringUtils.remove(src, src_1.length(), src_2.length());
		result = StringUtils.insert(result, src_2, src_1.length());
		assertEquals(src, result);
	}
	
	
	@Test public void isEmptyTest(){
		String notEmptySrc = "not empty";
		assertFalse("Is not empty: " + notEmptySrc, StringUtils.isEmpty(notEmptySrc));
		notEmptySrc = "n";
		assertFalse("Is not empty: " + notEmptySrc, StringUtils.isEmpty(notEmptySrc));
		notEmptySrc = "_";
		assertFalse("Is not empty: " + notEmptySrc, StringUtils.isEmpty(notEmptySrc));
		notEmptySrc = " s ";
		assertFalse("Is not empty: " + notEmptySrc, StringUtils.isEmpty(notEmptySrc));
		assertTrue("Null string is empty!", StringUtils.isEmpty(null));
		assertTrue("Zirol string is empty!", StringUtils.isEmpty(""));
		assertTrue("Whitespace String is empty!", StringUtils.isEmpty(" "));
		assertTrue("Whitespaces String is empty!", StringUtils.isEmpty("    "));
		assertTrue("Tabulation is empty!", StringUtils.isEmpty("\t"));
		assertTrue("Tabulations an whitespaces mix String is empty!", StringUtils.isEmpty("\t    "));
		assertTrue("New Line symbol String is empty!", StringUtils.isEmpty("\n"));
		assertTrue("New Line (Windows) symbol String is empty!", StringUtils.isEmpty("\r\n"));
		assertTrue("Mix from new lines, tabulations and whitespaces is empty!", StringUtils.isEmpty("\t  \n\t  \n"));
		assertTrue("Mix from new lines, tabulations and whitespaces is empty!", StringUtils.isEmpty("\t  \r\n\t  \n\r\t"));
		
//		//Magic sequence that is not cleaned by String.trim function. At least in Java(TM) SE Runtime Environment (build 1.6.0_17-b04-248-10M3025)
//		if(" \r\n".equals(src))
//			return true;
	}
}
 