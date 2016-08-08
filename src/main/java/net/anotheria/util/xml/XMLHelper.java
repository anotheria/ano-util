package net.anotheria.util.xml;

public class XMLHelper {
	public static String quote(Object o){
		return o == null ? "\"\"" : "\""+o+ '"';
	}
	
	public static String entag(String tag){
		return '<' +tag+">\n";
	}

	public static String detag(String tag){
		return "</"+tag+">\n";
	}

	public static String makeIdent(int count){
		String r = "";
		for (int i=0; i< count; i++) 
			r += '\t';
		return r;
	}
	
}
  