package net.anotheria.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Performs html and xml encoding.
 */
public class CharacterEntityCoder {
	private static final Hashtable<Character, String> ENTITIES;
	private static final Vector<String[]> HTML_ENTITIES;
	private static final Hashtable<String,String> HTML_2_XML_ENTITIES;
	
	
	static {
		ENTITIES = new Hashtable<Character, String>();
		ENTITIES.put(new Character('\n'), "&#010;");
		ENTITIES.put(new Character('\r'), "&#013;");
		ENTITIES.put(new Character('\t'), "&#009;");
		ENTITIES.put(new Character('&'), "&#038;");
		ENTITIES.put(new Character('\"'), "&#034;");
		ENTITIES.put(new Character('§'), "&#167;");
		ENTITIES.put(new Character('ß'), "&#223;");
		ENTITIES.put(new Character('<'), "&#060;");
		ENTITIES.put(new Character('>'), "&#062;");
		ENTITIES.put(new Character('€'), "&#198;");
		ENTITIES.put(new Character('À'), "&#192;");
		ENTITIES.put(new Character('Á'), "&#193;");
		ENTITIES.put(new Character('Â'), "&#194;");
		ENTITIES.put(new Character('Ã'), "&#195;");
		ENTITIES.put(new Character('Ä'), "&#196;");
		ENTITIES.put(new Character('Å'), "&#197;");
		ENTITIES.put(new Character('Æ'), "&#198;");
		ENTITIES.put(new Character('Ç'), "&#199;");
		ENTITIES.put(new Character('È'), "&#200;");
		ENTITIES.put(new Character('É'), "&#201;");
		ENTITIES.put(new Character('Ê'), "&#202;");
		ENTITIES.put(new Character('Ë'), "&#203;");
		ENTITIES.put(new Character('Ì'), "&#204;");
		ENTITIES.put(new Character('Í'), "&#205;");
		ENTITIES.put(new Character('Î'), "&#206;");
		ENTITIES.put(new Character('Ï'), "&#207;");
		ENTITIES.put(new Character('Ñ'), "&#209;");
		ENTITIES.put(new Character('Ò'), "&#210;");
		ENTITIES.put(new Character('Ó'), "&#211;");
		ENTITIES.put(new Character('Ô'), "&#212;");
		ENTITIES.put(new Character('Õ'), "&#213;");
		ENTITIES.put(new Character('Ö'), "&#214;");
		ENTITIES.put(new Character('Ù'), "&#217;");
		ENTITIES.put(new Character('Ú'), "&#218;");
		ENTITIES.put(new Character('Û'), "&#219;");
		ENTITIES.put(new Character('Ü'), "&#220;");
		ENTITIES.put(new Character('Ý'), "&#221;");
		ENTITIES.put(new Character('à'), "&#224;");
		ENTITIES.put(new Character('á'), "&#225;");
		ENTITIES.put(new Character('â'), "&#226;");
		ENTITIES.put(new Character('ã'), "&#227;");
		ENTITIES.put(new Character('ä'), "&#228;");
		ENTITIES.put(new Character('æ'), "&#230;");
		ENTITIES.put(new Character('ç'), "&#231;");
		ENTITIES.put(new Character('è'), "&#232;");
		ENTITIES.put(new Character('é'), "&#233;");
		ENTITIES.put(new Character('ê'), "&#234;");
		ENTITIES.put(new Character('ë'), "&#235;");
		ENTITIES.put(new Character('ì'), "&#236;");
		ENTITIES.put(new Character('í'), "&#237;");
		ENTITIES.put(new Character('î'), "&#238;");
		ENTITIES.put(new Character('ï'), "&#239;");
		ENTITIES.put(new Character('ð'), "&#240;");
		ENTITIES.put(new Character('ñ'), "&#241;");
		ENTITIES.put(new Character('ò'), "&#242;");
		ENTITIES.put(new Character('ó'), "&#243;");
		ENTITIES.put(new Character('ô'), "&#244;");
		ENTITIES.put(new Character('õ'), "&#245;");
		ENTITIES.put(new Character('ö'), "&#246;");
		ENTITIES.put(new Character('ù'), "&#249;");
		ENTITIES.put(new Character('ú'), "&#250;");
		ENTITIES.put(new Character('û'), "&#251;");
		ENTITIES.put(new Character('ü'), "&#252;");
		ENTITIES.put(new Character('ý'), "&#253;");
		ENTITIES.put(new Character('ÿ'), "&#255;");
		ENTITIES.put(new Character('-'), "&#8212;");
/*		entities.put(new Character((char) 0x00), "&#000");
		entities.put(new Character((char) 0x01), "&#001");
		entities.put(new Character((char) 0x02), "&#002");
		entities.put(new Character((char) 0x03), "&#003");
		entities.put(new Character((char) 0x04), "&#004");
		entities.put(new Character((char) 0x05), "&#005");
		entities.put(new Character((char) 0x06), "&#006");
		entities.put(new Character((char) 0x07), "&#007");
		entities.put(new Character((char) 0x08), "&#008");
		entities.put(new Character((char) 0x0b), "&#011");
		entities.put(new Character((char) 0x0c), "&#012");
		entities.put(new Character((char) 0x0e), "&#014");
		entities.put(new Character((char) 0x0f), "&#015");
		entities.put(new Character((char) 0x10), "&#016");
		entities.put(new Character((char) 0x11), "&#017");
		entities.put(new Character((char) 0x12), "&#018");
		entities.put(new Character((char) 0x13), "&#019");
		entities.put(new Character((char) 0x14), "&#020");
		entities.put(new Character((char) 0x15), "&#021");
		entities.put(new Character((char) 0x16), "&#022");
		entities.put(new Character((char) 0x17), "&#023");
		entities.put(new Character((char) 0x18), "&#024");
		entities.put(new Character((char) 0x19), "&#025");
		entities.put(new Character((char) 0x1a), "&#026");
		entities.put(new Character((char) 0x1b), "&#027");
		entities.put(new Character((char) 0x1c), "&#028");
		entities.put(new Character((char) 0x1d), "&#029");
		entities.put(new Character((char) 0x1e), "&#030");
		entities.put(new Character((char) 0x1f), "&#031");
*/				
		
		HTML_ENTITIES = new Vector<String[]>();
		HTML_ENTITIES.addElement((new String[] {"&", "&amp;"}));
		HTML_ENTITIES.addElement((new String[] {"\"", "&quot;"}));
		HTML_ENTITIES.addElement((new String[] {"§", "&sect;"}));
		HTML_ENTITIES.addElement((new String[] {"ß", "&szlig;"}));
		HTML_ENTITIES.addElement((new String[] {"<", "&lt;"}));
		HTML_ENTITIES.addElement((new String[] {">", "&gt;"}));
		HTML_ENTITIES.addElement((new String[] {"€", "&euro;"}));
//		htmlEntities.addElement((new String[] {"\n", "<br>"}));
		HTML_ENTITIES.addElement((new String[] {"À", "&Agrave;"}));
		HTML_ENTITIES.addElement((new String[] {"Á", "&Aacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Â", "&Acirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Ã", "&Atilde;"}));
		HTML_ENTITIES.addElement((new String[] {"Ä", "&Auml;"}));
		HTML_ENTITIES.addElement((new String[] {"Å", "&Aring;"}));
		HTML_ENTITIES.addElement((new String[] {"Æ", "&AElig;"}));
		HTML_ENTITIES.addElement((new String[] {"Ç", "&Ccedil;"}));
		HTML_ENTITIES.addElement((new String[] {"È", "&Egrave;"}));
		HTML_ENTITIES.addElement((new String[] {"É", "&Eacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Ê", "&Ecirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Ë", "&Euml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ì", "&Igrave;"}));
		HTML_ENTITIES.addElement((new String[] {"Í", "&Iacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Î", "&Icirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Ï", "&Iuml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ñ", "&Ntilde;"}));
		HTML_ENTITIES.addElement((new String[] {"Ò", "&Ograve;"}));
		HTML_ENTITIES.addElement((new String[] {"Ó", "&Oacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Ô", "&Ocirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Õ", "&Otilde;"}));
		HTML_ENTITIES.addElement((new String[] {"Ö", "&Ouml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ù", "&Ugrave;"}));
		HTML_ENTITIES.addElement((new String[] {"Ú", "&Uacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Û", "&Ucirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Ü", "&Uuml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ý", "&Yacute;"}));
		HTML_ENTITIES.addElement((new String[] {"à", "&agrave;"}));
		HTML_ENTITIES.addElement((new String[] {"á", "&aacute;"}));
		HTML_ENTITIES.addElement((new String[] {"â", "&acirc;"}));
		HTML_ENTITIES.addElement((new String[] {"ã", "&atilde;"}));
		HTML_ENTITIES.addElement((new String[] {"ä", "&auml;"}));
		HTML_ENTITIES.addElement((new String[] {"æ", "&aelig;"}));
		HTML_ENTITIES.addElement((new String[] {"ç", "&ccedil;"}));
		HTML_ENTITIES.addElement((new String[] {"è", "&egrave;"}));
		HTML_ENTITIES.addElement((new String[] {"é", "&eacute;"}));
		HTML_ENTITIES.addElement((new String[] {"ê", "&ecirc;"}));
		HTML_ENTITIES.addElement((new String[] {"ë", "&euml;"}));
		HTML_ENTITIES.addElement((new String[] {"ì", "&igrave;"}));
		HTML_ENTITIES.addElement((new String[] {"í", "&iacute;"}));
		HTML_ENTITIES.addElement((new String[] {"î", "&icirc;"}));
		HTML_ENTITIES.addElement((new String[] {"ï", "&iuml;"}));
		HTML_ENTITIES.addElement((new String[] {"ð", "&eth;"}));
		HTML_ENTITIES.addElement((new String[] {"ñ", "&ntilde;"}));
		HTML_ENTITIES.addElement((new String[] {"ò", "&ograve;"}));
		HTML_ENTITIES.addElement((new String[] {"ó", "&oacute;"}));
		HTML_ENTITIES.addElement((new String[] {"ô", "&ocirc;"}));
		HTML_ENTITIES.addElement((new String[] {"õ", "&otilde;"}));
		HTML_ENTITIES.addElement((new String[] {"ö", "&ouml;"}));
		HTML_ENTITIES.addElement((new String[] {"ù", "&ugrave;"}));
		HTML_ENTITIES.addElement((new String[] {"ú", "&uacute;"}));
		HTML_ENTITIES.addElement((new String[] {"û", "&ucirc;"}));
		HTML_ENTITIES.addElement((new String[] {"ü", "&uuml;"}));
		HTML_ENTITIES.addElement((new String[] {"ý", "&yacute;"}));
		HTML_ENTITIES.addElement((new String[] {"ÿ", "&yuml;"}));
		HTML_ENTITIES.addElement((new String[] {"-", "&mdash;"}));

		HTML_2_XML_ENTITIES = new Hashtable<String,String>();
		HTML_2_XML_ENTITIES.put("&amp;",   "&#038;");
		HTML_2_XML_ENTITIES.put("&ouml;",  "&#246;");
		HTML_2_XML_ENTITIES.put("&auml;",  "&#228;");
		HTML_2_XML_ENTITIES.put("&uuml;",  "&#252;");
		HTML_2_XML_ENTITIES.put("&Uuml;",  "&#220;");
		HTML_2_XML_ENTITIES.put("&Ouml;",  "&#214;");
		HTML_2_XML_ENTITIES.put("&Auml;",  "&#196;");
		HTML_2_XML_ENTITIES.put("&quot;",  "&#034;");
		HTML_2_XML_ENTITIES.put("&sect;",  "&#167;");
		HTML_2_XML_ENTITIES.put("&szlig;", "&#223;");
		HTML_2_XML_ENTITIES.put("&lt;",    "&#060;");
		HTML_2_XML_ENTITIES.put("&gt;",    "&#062;");
		HTML_2_XML_ENTITIES.put("&euro;",  "&#198;");
		HTML_2_XML_ENTITIES.put("ç",		"&#135;");
		HTML_2_XML_ENTITIES.put("á",		"&#160;");
		HTML_2_XML_ENTITIES.put("&agrave;",	"&#133;");
		HTML_2_XML_ENTITIES.put("&acirc;",	"&#131;");
		HTML_2_XML_ENTITIES.put("&Agrave;",	"&#192;");
		HTML_2_XML_ENTITIES.put("&Aacute;",	"&#193;");
		HTML_2_XML_ENTITIES.put("&Acirc;",	"&#194;");
		HTML_2_XML_ENTITIES.put("&Atilde;",	"&#195;");
		HTML_2_XML_ENTITIES.put("&Auml;",	"&#196;");
		HTML_2_XML_ENTITIES.put("&Aring;",	"&#197;");
		HTML_2_XML_ENTITIES.put("&AElig;",	"&#198;");
		HTML_2_XML_ENTITIES.put("&Ccedil;",	"&#199;");
		HTML_2_XML_ENTITIES.put("&Egrave;",	"&#200;");
		HTML_2_XML_ENTITIES.put("&Eacute;",	"&#201;");
		HTML_2_XML_ENTITIES.put("&Ecirc;",	"&#202;");
		HTML_2_XML_ENTITIES.put("&Euml;",	"&#203;");
		HTML_2_XML_ENTITIES.put("&Igrave;",	"&#204;");
		HTML_2_XML_ENTITIES.put("&Iacute;",	"&#205;");
		HTML_2_XML_ENTITIES.put("&Icirc;",	"&#206;");
		HTML_2_XML_ENTITIES.put("&Iuml;",	"&#207;");
		HTML_2_XML_ENTITIES.put("&Ntilde;",	"&#209;");
		HTML_2_XML_ENTITIES.put("&Ograve;",	"&#210;");
		HTML_2_XML_ENTITIES.put("&Oacute;",	"&#211;");
		HTML_2_XML_ENTITIES.put("&Ocirc;",	"&#212;");
		HTML_2_XML_ENTITIES.put("&Otilde;",	"&#213;");
		HTML_2_XML_ENTITIES.put("&Ouml;",	"&#214;");
		HTML_2_XML_ENTITIES.put("&Ugrave;",	"&#217;");
		HTML_2_XML_ENTITIES.put("&Uacute;",	"&#218;");
		HTML_2_XML_ENTITIES.put("&Ucirc;",	"&#219;");
		HTML_2_XML_ENTITIES.put("&Uuml;",	"&#220;");
		HTML_2_XML_ENTITIES.put("&Yacute;",	"&#221;");
		HTML_2_XML_ENTITIES.put("&agrave;",	"&#224;");
		HTML_2_XML_ENTITIES.put("&aacute;",	"&#225;");
		HTML_2_XML_ENTITIES.put("&acirc;",	"&#226;");
		HTML_2_XML_ENTITIES.put("&atilde;",	"&#227;");
		HTML_2_XML_ENTITIES.put("&aelig;",	"&#230;");
		HTML_2_XML_ENTITIES.put("&ccedil;",	"&#231;");
		HTML_2_XML_ENTITIES.put("&egrave;",	"&#232;");
		HTML_2_XML_ENTITIES.put("&eacute;",	"&#233;");
		HTML_2_XML_ENTITIES.put("&ecirc;",	"&#234;");
		HTML_2_XML_ENTITIES.put("&euml;",	"&#235;");
		HTML_2_XML_ENTITIES.put("&igrave;",	"&#236;");
		HTML_2_XML_ENTITIES.put("&iacute;",	"&#237;");
		HTML_2_XML_ENTITIES.put("&icirc;",	"&#238;");
		HTML_2_XML_ENTITIES.put("&iuml;",	"&#239;");
		HTML_2_XML_ENTITIES.put("&eth;",	"&#240;");
		HTML_2_XML_ENTITIES.put("&ntilde;",	"&#241;");
		HTML_2_XML_ENTITIES.put("&ograve;",	"&#242;");
		HTML_2_XML_ENTITIES.put("&oacute;",	"&#243;");
		HTML_2_XML_ENTITIES.put("&ocirc;",	"&#244;");
		HTML_2_XML_ENTITIES.put("&otilde;",	"&#245;");
		HTML_2_XML_ENTITIES.put("&ugrave;",	"&#249;");
		HTML_2_XML_ENTITIES.put("&uacute;",	"&#250;");
		HTML_2_XML_ENTITIES.put("&ucirc;",	"&#251;");
		HTML_2_XML_ENTITIES.put("&uuml;",	"&#252;");
		HTML_2_XML_ENTITIES.put("&yacute;",	"&#253;");
		HTML_2_XML_ENTITIES.put("&yuml;",	"&#255;");
		HTML_2_XML_ENTITIES.put("&mdash;",	"&#8212;");
	}

	/**
	* returns encoded String for XML use
	**/
	public static final String encodeString(String s) {
		StringBuffer result = new StringBuffer("");
		for (int i=0; i<s.length(); i++){
			Character c = new Character(s.charAt(i));
   			String temp = (String)ENTITIES.get(c);
      		result.append(temp == null ? ""+s.charAt(i) : temp);
       	}
		return result.toString();
	}
	/**
	* returns encoded String for HTML use
	**/
	public static final String htmlEncodeString(String s) {
		for (int i=0; i<HTML_ENTITIES.size(); i++) {
			String[] sa = (String[]) HTML_ENTITIES.elementAt(i);
			char o = sa[0].charAt(0);
			String n = sa[1];
			s = StringUtils.replace(s, o, n);
		}
		return s;
	}

	/**
	* returns dencoded String from XML
	**/
	public static final String decodeString(String s) {
		Enumeration<Character> e=ENTITIES.keys();
		while (e.hasMoreElements()) {
			Character o = e.nextElement();
			String n = (String) ENTITIES.get(o);
			int j = n.length();
			String s1 ="";
			int lastI=0;
			for (int i=0; i<s.length()-j; i++) {
				//System.out.println("##:"+s.substring(i,i+j)+"  >>> "+n);
				if (s.substring(i,i+j).equals(n)) {
					s1=s1+s.substring(lastI, i)+o;
					//System.out.println("###:"+s1);
					lastI = i+j;
					i=lastI;
				}
			}
			if(!s1.equals(""))
				s=s1;
		}

		return s;
	}

	/**
	* returns dencoded String from HTML
	**/
	public static final String decodeHtmlString(String s) {
		for (int k=0; k<HTML_ENTITIES.size(); k++) {
			String[] sa = (String[]) HTML_ENTITIES.elementAt(k);
			char o = sa[0].charAt(0);
			String n = sa[1];
			int j = n.length();
			String s1 ="";
			int lastI=0;
			for (int i=0; i<s.length()-j; i++) {
				//System.out.println("##:"+s.substring(i,i+j)+"  >>> "+n);
				if (s.substring(i,i+j).equals(n)) {
					s1=s1+s.substring(lastI, i)+o;
					//System.out.println("###:"+s1);
					lastI = i+j;
					i=lastI;
				}
			}
			if(!s1.equals(""))
				s=s1;
		}
		return s;
	}

	public static final String htm2xml(String s){
		//System.out.println("HTML: "+s);
		Enumeration<String> e = HTML_2_XML_ENTITIES.keys();
		while(e.hasMoreElements()){
			String htmString = e.nextElement();
			String xmlString = HTML_2_XML_ENTITIES.get(htmString);
			int j = htmString.length();
			int lastI=0;
			String replaced="";
			for(int i=0; i<s.length()-j; i++){
				if(s.substring(i,i+j).equals(htmString)){
					//System.out.println("ON Replace "+s.substring(i,i+j)+" -> "+s.substring(lastI, i)+xmlString);
					replaced = replaced +s.substring(lastI, i)+xmlString;
					//System.out.println("###:"+replaced);
					lastI = i+j;
					i=lastI-1;
				}
			}
			replaced = replaced + s.substring(lastI, s.length());
			if(!replaced.equals(""))
				s=replaced;
		}
		//System.out.println("XML: "+s);
		return s;
	}
}
