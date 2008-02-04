package net.anotheria.util.xml;

public class XMLHelper {
	public static String quote(Object o){
		return o == null ? "\"\"" : "\""+o+"\"";
	}
	
	public static String entag(String tag){
		return "<"+tag+">";
	}

	public static String detag(String tag){
		return "</"+tag+">";
	}
}
  