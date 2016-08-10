package net.anotheria.util.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A utility to create MD5 hashes.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class MD5Util {
	/**
	 * Returns the MD5 hash of the given string.
	 *
	 * @param toHash the string to enhash.
	 * @return the hash of the string.
	 */
	public static final String getMD5Hash(String toHash){
		return getMD5Hash(toHash.getBytes());
	}

	/**
	 * Returns the MD5 hash of the given string.
	 *
	 * @param toHash the stringbuilder to enhash.
	 * @return the hash of the string in the stringbuilder.
	 */
	public static final String getMD5Hash(CharSequence toHash){
		return getMD5Hash(toHash.toString().getBytes());
	}

	/**
	 * Returns the MD5 hash of the given byte array. This method is synchronized.
	 *
	 * @param bytes the byte array containing the string to enhash.
	 * @return a {@link java.lang.String} object.
	 */
	public static final String getMD5Hash(byte... bytes) {
		synchronized (MD5Util.class) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.reset();
				return HexDecoder.toHexString(md5.digest(bytes));
			} catch (NoSuchAlgorithmException e) {
				throw new AssertionError("NoSuchAlgorithmException: " + e, e);
			}
		}
	}
}
