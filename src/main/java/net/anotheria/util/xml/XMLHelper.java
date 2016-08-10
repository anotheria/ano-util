package net.anotheria.util.xml;

/**
 * <p>XMLHelper class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class XMLHelper {
	/**
	 * <p>quote.</p>
	 *
	 * @param o a {@link java.lang.Object} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String quote(Object o){
		return o == null ? "\"\"" : "\""+o+ '"';
	}
	
	/**
	 * <p>entag.</p>
	 *
	 * @param tag a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String entag(String tag){
		return '<' +tag+">\n";
	}

	/**
	 * <p>detag.</p>
	 *
	 * @param tag a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String detag(String tag){
		return "</"+tag+">\n";
	}

	/**
	 * <p>makeIdent.</p>
	 *
	 * @param count a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeIdent(int count){
		String r = "";
		for (int i=0; i< count; i++) 
			r += '\t';
		return r;
	}
	
}
  
