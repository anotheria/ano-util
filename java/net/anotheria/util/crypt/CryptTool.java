package net.anotheria.util.crypt;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import BlowfishJ.BlowfishECB;

/***
 * A tool to encrypt and decrypt strings using Blowfish algorithm.
 * @author lrosenberg
 *
 */
public class CryptTool {
	/**
	 * The underlying blowfish implementation.
	 */
	private final BlowfishECB cipher;

	/**
	 * Create a new crypttool with the given key.
	 * @param key the key to use for en- and decode.
	 */
	public CryptTool(String key) {
		this(key.getBytes());
	}

	/**
	 * Create a new crypttool with the binary given key.
	 * @param key the key to use for en- and decode.
	 */
	public CryptTool(byte[] key) {
		cipher = new BlowfishECB(key);
	}

	/**
	 * Returns a byte array containing the encrypted version of the string.
	 * @param toEncrypt
	 * @return
	 */
	public byte[] encrypt(String toEncrypt) {
		toEncrypt = padMod(toEncrypt, 8);
		byte[] toEncryptB;
		try{
			toEncryptB = toEncrypt.getBytes("UTF-8");
		}catch(UnsupportedEncodingException e){
			toEncryptB = toEncrypt.getBytes();
		}
		cipher.encrypt(toEncryptB);
		return toEncryptB;
	}

	/**
	 * Returns a HEX version of the encrypted string.
	 * @param toEncrypt the string to encrypt.
	 * @return
	 */
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
		return new String(decrypted);
	}

	public String decryptFromHexTrim(String toDecrypt) {
		return decryptFromHex(toDecrypt).trim();
	}

	private static String padMod(String source, int length) {
		int adder = length - (source.length() % length);

		if (adder == length)
			return source;

		StringBuilder ret = new StringBuilder(source);

		for (int i = 0; i < adder; i++) {
			ret.append(' ');
		}
		return ret.toString();
	}

	/**
	 * Encrypts a map of key value pairs to a single string to use as html parameter or similar.
	 * @param parameters a map with parameters.
	 * @return a string version of the map.
	 */
	public String encryptParameterMap(Map<String,String> parameters){
		Iterator<String> keys = parameters.keySet().iterator();
		StringBuilder toEncode = new StringBuilder();
		while (keys.hasNext()){
			if (toEncode.length()>0)
				toEncode.append('&');
			String key = keys.next();
			String value = parameters.get(key);
			toEncode.append(key).append('=').append(value);
		}
		return encryptToHex(toEncode.toString().trim());
	}

	/**
	 * Decrypts a previously encoded parameter map and returns it as map of key-value pairs.
	 * @param str the string to decode.
	 * @return a map with decrypted parameters.
	 */
	public Map<String,String> decryptParameterMap(String str){
		String decrypted = decryptFromHex(str);
		decrypted = decrypted.trim();
		HashMap<String,String> map = new HashMap<String,String>();
		String tokens[] = StringUtils.tokenize(decrypted, '&');
		for (int i=0; i<tokens.length; i++){
			String t[] = StringUtils.tokenize(tokens[i], '=');
			if (t.length == 2) {
				map.put(t[0], t[1]);
			} else {
				map.put(t[0], "");
			}
		}
		return map;
	}


	private static final int NUMERATION_BASE_NUMBER = 13212358;

	/**
	 * Converts Numerical ID to Chiffre: string from letters and digits 8 symbols length.
	 * @param id to convert
	 * @return
	 */
	public static String idToChiffre(String id) {
		int normalizedId = Integer.parseInt(id) + NUMERATION_BASE_NUMBER;
		if(normalizedId > 99999999)
			throw new AssertionError("Id["+id+"] is to big for the current implementation");

		int lastDigit = normalizedId%10;

		StringBuilder ret = new StringBuilder();
		String retIncrement = NumberUtils.itoa(normalizedId%100);

		while(normalizedId > 0){
			int n = normalizedId & 31 ^ lastDigit;
			normalizedId = normalizedId >> 5;
			n = n <= 25? n + 65: n + 24;
			ret.append((char)n);
		}

		ret.append(retIncrement);
		return ret.toString();
	}

	/**
	 * Restore Numerical ID from Chiffre.
	 * @param chiffre
	 * @return
	 */
	public static String chiffreToId(String chiffre) {

		StringBuilder toDenum = new StringBuilder(chiffre);
		int lastDigit = Character.getNumericValue(toDenum.charAt(toDenum.length() - 1));

		toDenum.delete(toDenum.length() - 2, toDenum.length());

		int ret = 0;
		int position = 0;

		for(char c: toDenum.toString().toCharArray()){
			int n = (c >= 65? c-65: c-24) ^ lastDigit;
			n = n << 5*(position++);
			ret += n;
		}
		ret -= NUMERATION_BASE_NUMBER;
		return ret + "";
	}

	/**
	 * Encrypts specified byte array. Size must be aligned to 8-bytes boundary.
	 * @param buffer byte array to encrypt.
	 */
	public void encryptBuffer(byte[] buffer) {
		if (buffer.length % (Long.SIZE / Byte.SIZE) != 0) {
			throw new IllegalArgumentException("Buffer size is not alligned to 8-bytes boundary");
		}
		cipher.encrypt(buffer);
	}

	/**
	 * Decrypts specified byte array. Size must be aligned to 8-bytes boundary.
	 * @param buffer byte array to decrypt.
	 */
	public void decryptBuffer(byte[] buffer) {
		if (buffer.length % (Long.SIZE / Byte.SIZE) != 0) {
			throw new IllegalArgumentException("Buffer size is not alligned to 8-bytes boundary");
		}
		cipher.decrypt(buffer);
	}
}