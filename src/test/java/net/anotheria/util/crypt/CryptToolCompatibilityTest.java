package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Test utility for {@link CryptTool}.
 *
 * @author lrosenberg
 * @since 04.08.16 21:44
 */
public class CryptToolCompatibilityTest {

	private CryptTool cryptShort = new CryptTool("01234567890abcdef");
    private CryptTool cryptLong = new CryptTool("749283fhkjdlhfksdjh974535843yfdkhgjdsgfjhsdgf78456395683475fghdjkgdsf");


	@Test
	public void testILoveYou(){
        String expected = "I Love You!";

        byte[] encryptedShort = cryptShort.encrypt(expected);
        byte[] encryptedLong = cryptLong.encrypt(expected);

        String actualShort = new String(cryptShort.decrypt(encryptedShort)).trim();
        String actualLong = new String(cryptLong.decrypt(encryptedLong)).trim();

        assertEquals(expected, actualShort);
        assertEquals(expected, actualLong);
	}

    @Test
    public void testILoveYouOnlyDecrypt(){
        String expected = "I Love You!";

        byte[] encryptedShort = new byte[]{93, -30, -114, -59, -92, -33, -55, 63, -3, -71, 43, 41, -49, -101, -35, 47, -47, 8, 120, 113, 74, -123, -28, -43};
        byte[] encryptedLong = new byte[]{62, -109, -93, -96, 94, -103, -25, 79, -54, -128, -22, 14, 46, -109, -115, 117, 10, -116, 87, 18, 67, -55, 102, -12};

        String actualShort = new String(cryptShort.decrypt(encryptedShort)).trim();
        String actualLong = new String(cryptLong.decrypt(encryptedLong)).trim();

        assertEquals(expected, actualShort);
        assertEquals(expected, actualLong);
    }

	@Test
    public void testPadding(){
		testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, -63, 53, 122, 2, 4, -52, 114, -127}, "12345678", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, -51, 74, 19, 1, -22, 2, -127, -49}, "12345678", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, 9, -127, 80, -78, 108, 60, 48, -10, 115, -7, -81, 3, 93, -40, -27, 97}, "1234567812345678", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, 86, -58, 119, 111, -120, 67, 44, -19, -18, -4, -63, -115, 34, -11, -13, -18}, "1234567812345678", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, 9, -127, 80, -78, 108, 60, 48, -10, -46, 77, -66, -3, -84, 104, 78, -125, 52, 86, -16, -8, -25, -24, -117, 2}, "123456781234567812345678", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, 86, -58, 119, 111, -120, 67, 44, -19, 30, -35, -104, 60, -5, 97, 80, -4, -74, -42, 40, 110, -53, 58, 55, 19}, "123456781234567812345678", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, 9, -127, 80, -78, 108, 60, 48, -10, -64, 5, 20, 8, -101, -55, -29, -111, -8, 29, -15, 119, 21, 12, 126, 25}, "12345678123456781234", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, 86, -58, 119, 111, -120, 67, 44, -19, -123, -109, 25, -49, 19, 88, 99, 83, -18, -27, 120, 24, -4, -84, -102, -114}, "12345678123456781234", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, 54, 64, 108, 20, -77, -83, 51, -22, 37, -62, -41, 54, -42, -124, -18, 14}, "12345678123", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, -38, -116, -51, 113, -23, 47, -121, -52, 122, -61, -45, 79, -101, -98, 63, -112}, "12345678123", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, -77, -9, -93, -20, 82, 46, -107, -31, 55, -9, 53, 24, -4, -51, -39, 125}, "1234567812", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, 58, -54, -108, -23, -2, -12, -53, 56, 34, -125, 16, 87, 121, 66, -125, 96}, "1234567812", cryptLong);
        testPadding(new byte[]{-95, 118, -60, 47, 12, 86, -49, 9, 1, -120, -75, 43, -6, 23, -11, 109, 52, 5, -66, -37, -69, -59, -24, 100}, "123456781", cryptShort);
        testPadding(new byte[]{-21, 51, -55, 96, 72, 66, 25, -81, 51, 6, 102, 65, 51, 86, 15, 67, 89, 11, -27, 20, -23, -9, -31, -26}, "123456781", cryptLong);
	}

	private void testPadding(byte[] crypted, String message, CryptTool cryptTool){
		String decrypted = new String(cryptTool.decrypt(crypted)).trim();
		assertEquals(message, decrypted);
	}


	@Test
    public void testMap(){

        String cryptedShort = "2EB5F10BBF24C454BC21B655C2924E5549D8150341202D03";
        String cryptedLong = "E49F2E1BEFAF6F96D02443642D3752CD61FFA77F777E5A85";

        Map<String, String> expectedMap = Map.of(
                "a", "1",
                "b", "3",
                "c", "88"
                );

        Map<String,String> decryptedShort = cryptShort.decryptParameterMap(cryptedShort);
        Map<String,String> decryptedLong = cryptLong.decryptParameterMap(cryptedLong);

        testMap(expectedMap, decryptedShort);
        testMap(expectedMap, decryptedLong);
	}

    private void testMap(Map<String, String> expected, Map<String, String> actual){
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(expected.size(), actual.size());
        for(String key : expected.keySet()){
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    @Test
    public void testConfigurationReadKey(){
        char[] configurationReadKey = {'z', 'F', 'P', (char) 121, 'T', 'b', (char) 97, 'a', (char) 5, (char) 71, 'W', 'n'};

        CryptTool cryptTool = new CryptTool(new String(configurationReadKey));
        String what = cryptTool.decryptFromHexTrim("C94EEB3F6F6C5DE25899EFEB3B76338E1D5EF34F288AA3ED");
        String appSecret = cryptTool.encryptToHex("C94EEB3F6F6C5DE25899EFEB3B76338E1D5EF34F288AA3ED");
        CryptTool cryptTool2 = new CryptTool(appSecret + "4136a701-7f5b-486e-9505-13613d940fde");

        String message = "I love you";

        String encrypted = cryptTool2.encryptToHex(message);
        String decrypted = cryptTool2.decryptFromHexTrim(encrypted);
        assertEquals(message, decrypted);
    }

    @Test
    public void testPass(){
        CryptTool cryptTool = new CryptTool("PORTALKITFOREVER");
        String message = cryptTool.encryptToHex("qwerty123A!");
        System.out.println(message);
    }
}
