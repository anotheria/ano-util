package net.anotheria.util.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static final String getMD5Hash(String toHash){
		return getMD5Hash(toHash.getBytes());
	}

	public static final String getMD5Hash(StringBuilder toHash){
		return getMD5Hash(toHash.toString().getBytes());
	}
	
	public static final String getMD5Hash(byte[] bytes){
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			return HexDecoder.toHexString(md5.digest(bytes));
		}catch(NoSuchAlgorithmException e){
			throw new RuntimeException("NoSuchAlgorithmException: "+e);
		}
		
	}
}
