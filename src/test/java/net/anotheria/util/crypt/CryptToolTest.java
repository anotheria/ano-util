package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class CryptToolTest {

    private CryptTool crypt = new CryptTool("01234567890abcdef");

    @Test
    public void encryptAndDecrypt() {
        String message = "I Love You!";
        byte[] crypted = crypt.encrypt(message);

        assertFalse(message.equals(crypted));
        assertFalse(crypted == null);
        assertFalse(crypted.length == 0);

        String decrypted = new String(crypt.decrypt(crypted)).trim();
        assertEquals(message, decrypted);
    }

    @Test
    public void encryptAndDecryptHex() {
        String message = "I Love You!";
        String crypted = new String(crypt.encryptToHex(message));

        assertFalse(message.equals(crypted));
        assertFalse(crypted == null);
        assertFalse(crypted.isEmpty());

        String decrypted = new String(crypt.decryptFromHex(crypted)).trim();
        String decryptedTrim = new String(crypt.decryptFromHexTrim(crypted));
        assertEquals(message, decrypted);
        assertEquals(decrypted, decryptedTrim);
    }

    @Test
    public void testMap() {
        Map<String, String> parameters = new HashMap<>();

        String k1 = "a", v1 = "1";
        parameters.put(k1, v1);
        String v2 = "3";
        String k2 = "b";
        parameters.put(k2, v2);
        int size = parameters.size();

        String crypted = crypt.encryptParameterMap(parameters);
        assertNotNull(crypted);
        assertFalse(crypted.isEmpty());

        Map<String, String> decrypted = crypt.decryptParameterMap(crypted);
        assertNotNull(decrypted);
        assertFalse(decrypted.isEmpty());

        assertEquals(size, decrypted.size());
        assertEquals(v1, decrypted.get(k1));
        assertEquals(v2, decrypted.get(k2));
        String k3 = "c";
        assertEquals(null, decrypted.get(k3));

    }

    /**
     * Test specifically for 8-byte long messages
     */
    @Test
    public void testPad() {
        testCrypt("12345678");
        testCrypt("1234567812345678");
        testCrypt("123456781234567812345678");
        testCrypt("12345678123456781234");
        testCrypt("12345678123");
        testCrypt("1234567812");
        testCrypt("123456781");
    }

    private void testCrypt(String message) {
        byte[] crypted = crypt.encrypt(message);

        assertFalse(message.equals(crypted));
        assertFalse(crypted == null);
        assertFalse(crypted.length == 0);

        String decrypted = new String(crypt.decrypt(crypted)).trim();
        assertEquals(message, decrypted);

    }

    @Test
    public void testHexDecoder() {
        assertEquals(null, HexDecoder.fromHexString(null));
        try {
            HexDecoder.fromHexString("1");
            fail("Hex String length must be a multiple of 2.");
        } catch (IllegalArgumentException e) {
        }

        try {
            HexDecoder.fromHexString("XX");
            fail("Illegal chars are not tested");
		}catch(IllegalArgumentException e){}
    }

    @Test
    public void simpleStringShouldBeEncrypted() {
        String toEncrypt = "Hello!";
        byte[] actual = crypt.encrypt(toEncrypt);
        byte[] expected = {47, 54, 33, 8, -13, -37, -72, 42, -2, -101, 89, 13, -56, -62, -9, -26};
        assertThat(actual, is(expected));
    }

    @Test
    public void simpleStringShouldBeEncryptedAndConvertedToHex() {
        String toEncrypt = "To be encrypted";
        String hex = crypt.encryptToHex(toEncrypt);
        String expected = "D27BFFB393A78E4F35ECEA6179D9E609BCF80E2AC959D5B5";
        assertThat(hex, is(expected));
    }

    @Test
    public void encryptedHexShouldBeDecrypted() {
        String toDecrypt = "D27BFFB393A78E4F35ECEA6179D9E609BCF80E2AC959D5B5";
        String decrypt = crypt.decryptFromHex(toDecrypt).trim();
        String expected = "To be encrypted";
        assertThat(decrypt, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failIfBufferToSmallInEncryption() {
        crypt.encryptBuffer(new byte[2]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failIfBufferToSmallInDecryption() {
        crypt.decryptBuffer(new byte[2]);
    }

}
