package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CryptToolTest {

    private CryptTool crypt = new CryptTool("01234567890abcdef");

//    @Test
//    public void encryptAndDecrypt() {
//        String message = "I Love You!";
//        byte[] crypted = crypt.encrypt(message);
//
//        assertFalse(message.equals(crypted));
//        assertFalse(crypted == null);
//        assertFalse(crypted.length == 0);
//
//        String decrypted = new String(crypt.decrypt(crypted)).trim();
//        assertEquals(message, decrypted);
//    }
//
//    @Test
//    public void encryptAndDecryptHex() {
//        String message = "I Love You!";
//        String crypted = new String(crypt.encryptToHex(message));
//
//        assertFalse(message.equals(crypted));
//        assertFalse(crypted == null);
//        assertFalse(crypted.length() == 0);
//
//        String decrypted = new String(crypt.decryptFromHex(crypted)).trim();
//        String decryptedTrim = new String(crypt.decryptFromHexTrim(crypted));
//        assertEquals(message, decrypted);
//        assertEquals(decrypted, decryptedTrim);
//    }
//
//    @Test
//    public void testMap() {
//        Map<String, String> parameters = new HashMap<String, String>();
//
//        String k1 = "a", v1 = "1";
//        String k2 = "b", v2 = "3";
//        String k3 = "c";
//        parameters.put(k1, v1);
//        parameters.put(k2, v2);
//        int size = parameters.size();
//
//        String crypted = crypt.encryptParameterMap(parameters);
//        assertFalse(crypted == null);
//        assertFalse(crypted.length() == 0);
//
//        Map<String, String> decrypted = crypt.decryptParameterMap(crypted);
//        assertFalse(decrypted == null);
//        assertFalse(decrypted.size() == 0);
//
//        assertEquals(size, decrypted.size());
//        assertEquals(v1, decrypted.get(k1));
//        assertEquals(v2, decrypted.get(k2));
//        assertEquals(null, decrypted.get(k3));
//
//    }
//
//    /**
//     * Test specifically for 8-byte long messages
//     */
//    @Test
//    public void testPad() {
//        testCrypt("12345678");
//        testCrypt("1234567812345678");
//        testCrypt("123456781234567812345678");
//        testCrypt("12345678123456781234");
//        testCrypt("12345678123");
//        testCrypt("1234567812");
//        testCrypt("123456781");
//    }
//
//    private void testCrypt(String message) {
//        byte[] crypted = crypt.encrypt(message);
//
//        assertFalse(message.equals(crypted));
//        assertFalse(crypted == null);
//        assertFalse(crypted.length == 0);
//
//        String decrypted = new String(crypt.decrypt(crypted)).trim();
//        System.out.println("Trying to check whether " + message + " is equal to " + decrypted);
//        assertEquals(message, decrypted);
//
//    }
//
//    @Test
//    public void testHexDecoder() {
//        assertEquals(null, HexDecoder.fromHexString(null));
//        try {
//            HexDecoder.fromHexString("1");
//            fail("Hex String length must be a multiple of 2.");
//        } catch (IllegalArgumentException e) {
//        }
//
//        try {
//            HexDecoder.fromHexString("XX");
//            fail("Illegal chars are not tested");
//        } catch (IllegalArgumentException e) {
//        }
//    }

    @Test
    public void simpleStringShouldBeEncrypted() {
        byte[] actual = crypt.encrypt("hello");
        byte[] expected = {-50, 88, -98, 38, -36, -64, -16, 49};
        assertThat(actual, is(expected));
    }

    @Test
    public void complexStringShouldBeEncrypted() {
        String complexString = "\u00e4\u00fc\u00f6\u00df\u0021\u0024\u0025\u0026\u002f\u0028\u0029\u003d\u003f\u0060\u0060\u005e\u00b0\u0023\u002b\u002e\u005f\u003b\u002e\u00b5\u0040\u0142\u20ac\u00b6\u0167\u2190\u2193\u2192\u00f8\u00fe\u00e6\u017f\u00f0\u0111\u0111\u014b\u0127\u0142\u02dd\u007c\u00bb\u00ab\u00a2\u201e\u201c\u201d\u00b7\u2026\u2013";
        byte[] actual = crypt.encrypt(complexString);
        System.out.println(Arrays.toString(actual));
        byte[] expected = {-50, 88, -98, 38, -36, -64, -16, 49};
        assertThat(actual, is(expected));
    }

    @Test
    public void simpleStringShouldBeEncryptedAndConvertedToHex() {
        crypt.encryptToHex("");
    }

}
