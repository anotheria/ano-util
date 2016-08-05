package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 04.08.16 21:44
 */
public class CryptToolCompatibilityTest {

	private CryptTool crypt = new CryptTool("01234567890abcdef");

	@Test
	public void testILoveYou(){
		byte[] crypted = new byte[]{-108, -112, -41, 59, 1, 124, -53, -122, -41, -109, -56, -127, -122, -54, 72, 69};
		String message = "I Love You!";

		String decrypted = new String(crypt.decrypt(crypted)).trim();
		assertEquals(message, decrypted);
	}

	@Test public void testPadding(){
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45}, "12345678");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, 120, 39, 106, 44, -71, 23, 122, -45}, "1234567812345678");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, 120, 39, 106, 44, -71, 23, 122, -45, 120, 39, 106, 44, -71, 23, 122, -45}, "123456781234567812345678");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, 120, 39, 106, 44, -71, 23, 122, -45, 107, -60, -68, -125, -33, 58, 0, 16}, "12345678123456781234");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, 64, -1, 34, -39, 102, -111, 40, -96}, "12345678123");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, 65, -122, -116, -96, 47, -98, 14, -94}, "1234567812");
		_testPadding(new byte[]{120, 39, 106, 44, -71, 23, 122, -45, -114, 70, -33, 44, 23, 93, 71, 77}, "123456781");

	}

	private void _testPadding(byte[] crypted, String message){
		String decrypted = new String(crypt.decrypt(crypted)).trim();
		assertEquals(message, decrypted);

	}

	@Test public void testMap(){
		String crypted = "C8625359A63A15A0";
		String k1="a", v1="1";
		String k2="b", v2="3";
		String k3="c";
		Map<String,String> decrypted = crypt.decryptParameterMap(crypted);

		assertFalse(decrypted==null);
		assertFalse(decrypted.size()==0);

		assertEquals(2, decrypted.size());
		assertEquals(v1, decrypted.get(k1));
		assertEquals(v2, decrypted.get(k2));
		assertEquals(null, decrypted.get(k3));

	}
}
