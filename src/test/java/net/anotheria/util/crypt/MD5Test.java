package net.anotheria.util.crypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MD5Test {
	@Test public void testMd5(){
		String fromTool = MD5Util.getMD5Hash("thisisjustatest");
		String fromShellMD5 = "378041508fcb2574e1724f8917369be9";
		assertEquals(fromShellMD5, fromTool.toLowerCase());
		
	}
}
