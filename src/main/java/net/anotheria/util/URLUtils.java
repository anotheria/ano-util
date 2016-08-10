package net.anotheria.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Small and simple utils to handle URLs
 *
 * @author denis
 * @version $Id: $Id
 */
public class URLUtils {
	
	/** Constant <code>ENCODING</code> */
	public static final Map<String,String> ENCODING;
	
	static {
		ENCODING = new HashMap<>();
		ENCODING.put(" ", "%20");
		ENCODING.put("<", "%3C");
		ENCODING.put(">", "%3E");
		ENCODING.put("#", "%23");
		ENCODING.put("%", "%25");
//		ENCODING.put("{", "%7B");
//		ENCODING.put("}", "%7D");
//		ENCODING.put("|", "%7C");
//		ENCODING.put("\\", "%5C");
//		ENCODING.put("^", "%5E");
//		ENCODING.put("~", "%7E");
//		ENCODING.put("[", "%5B");
//		ENCODING.put("]", "%5D");
//		ENCODING.put("`", "%60");
//		ENCODING.put(";", "%3B");
//		ENCODING.put("/", "%2F");
//		ENCODING.put("?", "%3F");
//		ENCODING.put(":", "%3A");
//		ENCODING.put("@", "%40");
//		ENCODING.put("=", "%3D");
//		ENCODING.put("&", "%26");
//		ENCODING.put("$", "%24");

	}
	
	/**
	 * Encodes unsafe characters in a URL
	 *
	 * @return mostly (in current implementation) safe URL
	 * @param url a {@link java.lang.String} object.
	 */
	public static String encode(String url){
		return StringUtils.replace(url, ENCODING);
	}
	
}
