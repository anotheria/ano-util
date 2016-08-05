package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;

public class CryptToolTest {

	private CryptTool crypt = new CryptTool("01234567890abcdef");

	@Test public void encryptAndDecrypt(){
		String message = "I Love You!";
		byte[] crypted = crypt.encrypt(message);

		System.out.println(Arrays.toString(crypted));

		assertFalse(message.equals(crypted));
		assertFalse(crypted==null);
		assertFalse(crypted.length==0);

		String decrypted = new String(crypt.decrypt(crypted)).trim();
		assertEquals(message, decrypted);
	}

	@Test public void encryptAndDecryptHex(){
		String message = "I Love You!";
		String crypted = new String(crypt.encryptToHex(message));

		assertFalse(message.equals(crypted));
		assertFalse(crypted==null);
		assertFalse(crypted.length()==0);

		String decrypted = new String(crypt.decryptFromHex(crypted)).trim();
		String decryptedTrim = new String(crypt.decryptFromHexTrim(crypted));
		assertEquals(message, decrypted);
		assertEquals(decrypted, decryptedTrim);
	}

	@Test public void testMap(){
		Map<String,String> parameters = new HashMap<String, String>();

		String k1="a", v1="1";
		String k2="b", v2="3";
		String k3="c";
		parameters.put(k1, v1);
		parameters.put(k2, v2);
		int size = parameters.size();

		String crypted = crypt.encryptParameterMap(parameters);
		assertFalse(crypted==null);
		assertFalse(crypted.length()==0);

		Map<String,String> decrypted = crypt.decryptParameterMap(crypted);
		assertFalse(decrypted==null);
		assertFalse(decrypted.size()==0);

		assertEquals(size, decrypted.size());
		assertEquals(v1, decrypted.get(k1));
		assertEquals(v2, decrypted.get(k2));
		assertEquals(null, decrypted.get(k3));

 	}

	/**
	 * Test specifically for 8-byte long messages
	 */
	@Test public void testPad(){
		testCrypt("12345678");
		testCrypt("1234567812345678");
		testCrypt("123456781234567812345678");
		testCrypt("12345678123456781234");
		testCrypt("12345678123");
		testCrypt("1234567812");
		testCrypt("123456781");
	}

	private void testCrypt(String message){
		byte[] crypted = crypt.encrypt(message);

		assertFalse(message.equals(crypted));
		assertFalse(crypted==null);
		assertFalse(crypted.length==0);

		String decrypted = new String(crypt.decrypt(crypted)).trim();
		assertEquals(message, decrypted);

	}

	@Test public void testHexDecoder(){
		assertEquals(null, HexDecoder.fromHexString(null));
		try{
			HexDecoder.fromHexString("1");
			fail("Hex String length must be a multiple of 2.");
		}catch(IllegalArgumentException e){}

		try{
			HexDecoder.fromHexString("XX");
			fail("Illegal chars are not tested");
		}catch(IllegalArgumentException e){}
	}
}
