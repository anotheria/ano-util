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
        System.out.println("Trying to check whether " + message + " is equal to " + decrypted);
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
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void simpleStringShouldBeEncrypted() {
        String toEncrypt = "Hello!";
        byte[] actual = crypt.encrypt(toEncrypt);
        byte[] expected = {53, 1, -64, -1, 46, 30, -70, 0};
        assertThat(actual, is(expected));
    }

    @Test
    public void simpleStringShouldBeEncryptedAndConvertedToHex() {
        String toEncrypt = "To be encrypted";
        String hex = crypt.encryptToHex(toEncrypt);
        String expected = "8792D7AE8B518173C493966D2B665517";
        assertThat(hex, is(expected));
    }

    @Test
    public void encryptedShouldBeDecrypted() {
        byte[] toDecrypt = {53, 1, -64, -1, 46, 30, -70, 0};
        byte[] decrypt = crypt.decrypt(toDecrypt);
        byte[] expected = {'H', 'e', 'l', 'l', 'o', '!', 32, 32};
        assertThat(decrypt, is(expected));
    }

    @Test
    public void encryptedHexShouldBeDecrypted() {
        String toDecrypt = "8792D7AE8B518173C493966D2B665517";
        String decrypt = crypt.decryptFromHex(toDecrypt);
        String expected = "To be encrypted ";
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

    @Test
    public void bufferShouldBeEncrypted() {
        byte[] buffer = { 'H', 'o', 'w', 'd', 'y', '!', '!', '!'};
        crypt.encryptBuffer(buffer);
        byte[] expected = {-115, -90, 6, -79, -62, 46, -78, -117};
        assertThat(buffer, is(expected));
    }

    @Test
    public void bufferShouldBeDecrypted() {
        byte[] buffer = {-115, -90, 6, -79, -62, 46, -78, -117};
        crypt.decryptBuffer(buffer);
        byte[] expected = { 'H', 'o', 'w', 'd', 'y', '!', '!', '!'};
        assertThat(buffer, is(expected));
    }

}
