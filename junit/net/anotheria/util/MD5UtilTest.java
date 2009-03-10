package net.anotheria.util;

import net.anotheria.util.crypt.MD5Util;

import org.junit.Test;
import static junit.framework.Assert.*;

public class MD5UtilTest {
	@Test public void test(){
		String code = "1234567890";
		
		String hash1 = MD5Util.getMD5Hash(code);
		String hash2 = MD5Util.getMD5Hash(code.getBytes());
		String hash3 = MD5Util.getMD5Hash(new StringBuilder().append(code));
		
		assertEquals(hash1, hash2);
		assertEquals(hash1, hash3);
		assertFalse(hash1==null);

		
	}
}
