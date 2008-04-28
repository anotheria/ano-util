package net.anotheria.util.crypt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.anotheria.util.StringUtils;
import BlowfishJ.BlowfishECB;

public class CryptTool {
	private BlowfishECB cipher;

	public CryptTool(String key) {
		byte[] raw = key.getBytes();
		cipher = new BlowfishECB(raw);
	}

	/* Returns a crypted byte array */
	public byte[] encrypt(String toEncrypt) {
		toEncrypt = padMod(toEncrypt, 8);		
		byte[] toEncryptB = toEncrypt.getBytes();	
		cipher.encrypt(toEncryptB);
		return toEncryptB;
	}

	/* Returns a crypted string in hex */
	public String encryptToHex(String toEncrypt) {
		byte[] encrypted = encrypt(toEncrypt);
		return HexDecoder.toHexString(encrypted);
	}

	public byte[] decrypt(byte[] toDecrypt) {
		cipher.decrypt(toDecrypt);
		return toDecrypt;
	}

	public String decryptFromHex(String toDecrypt) {
		byte[] decrypted = decrypt(HexDecoder.fromHexString(toDecrypt));
		return (new String(decrypted));
	}

	public String decryptFromHexTrim(String toDecrypt) {
		return decryptFromHex(toDecrypt).trim();
	}

	private static String padMod(String source, int length) {
		int adder = length - (source.length() % length);

		if (adder == length)
			return source;

		for (int i = 0; i < adder; i++) {
			source += " ";
		}
		return source;
	}
	
	public String encryptParameterMap(Map<String,String> parameters){
		Iterator<String> keys = parameters.keySet().iterator();
		String toEncode = ""; 
		while (keys.hasNext()){
			if (toEncode.length()>0)
				toEncode += "&";
			String key = keys.next();
			String value = parameters.get(key);
			toEncode += key+"="+value;
		}
		toEncode = toEncode.trim();
		
		return encryptToHex(toEncode);
	}
	
	public Map<String,String> decryptParameterMap(String str){
		String decrypted = decryptFromHex(str);
		decrypted = decrypted.trim();
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		String tokens[] = StringUtils.tokenize(decrypted, '&');
		for (int i=0; i<tokens.length; i++){
			String t[] = StringUtils.tokenize(tokens[i], '=');
			map.put(t[0], t[1]);
		}
		
		return map;
	}
}