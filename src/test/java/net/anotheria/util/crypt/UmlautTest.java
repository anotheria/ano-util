package net.anotheria.util.crypt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 2020-02-05 09:05
 */
public class UmlautTest {
	@Test public void testEncodeDecodeUmlauts() throws Exception{
		CryptTool tool = new CryptTool("3CBAAC08769023063897E8E4B9CAEC61");
		String message = "ÄÖÜÖÄÜüöäöüä";
		byte[] encrypted = tool.encrypt(message);
		System.out.println(new String(encrypted, "UTF-8"));
		System.out.println(tool.encryptToHex(message));
		byte[] decrypted = tool.decrypt(encrypted);
		String restoredMessage = new String(decrypted, "UTF-8").trim();
		System.out.println("DEC."+restoredMessage+".");
		assertEquals(message, restoredMessage);
	}

	/**
	 * This is a test for a string containing umlauts and being 8 chars long as string.
	 * @throws Exception
	 */
	@Test public void testEncodeDecodeUmlautsUTF8With8Characters() throws Exception{
		//00D6 00FC etc
		//System.out.println("\u00FC");
		//System.out.println(Arrays.toString("\u00D6".getBytes("UTF-8")));
		byte[] m = {-61, -92, -61, -68, -61, -106,25, 66, -61, -92, -61, -68, -61, -106};
		String message = new String(m, "UTF-8");
		System.out.println(message);


		CryptTool tool = new CryptTool("3CBAAC08769023063897E8E4B9CAEC61");
		byte[] encrypted = tool.encrypt(message);
		byte[] decrypted = tool.decrypt(encrypted);
		String decryptedMessage  = new String(decrypted, "UTF-8").trim();

		assertEquals(decryptedMessage, message);


	}

}
