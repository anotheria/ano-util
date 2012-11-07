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
		ENTITIES.put(new Character('ß'), "&#167;");
		ENTITIES.put(new Character('ﬂ'), "&#223;");
		ENTITIES.put(new Character('<'), "&#060;");
		ENTITIES.put(new Character('>'), "&#062;");
		ENTITIES.put(new Character('Ä'), "&#198;");
		ENTITIES.put(new Character('¿'), "&#192;");
		ENTITIES.put(new Character('¡'), "&#193;");
		ENTITIES.put(new Character('¬'), "&#194;");
		ENTITIES.put(new Character('√'), "&#195;");
		ENTITIES.put(new Character('ƒ'), "&#196;");
		ENTITIES.put(new Character('≈'), "&#197;");
		ENTITIES.put(new Character('∆'), "&#198;");
		ENTITIES.put(new Character('«'), "&#199;");
		ENTITIES.put(new Character('»'), "&#200;");
		ENTITIES.put(new Character('…'), "&#201;");
		ENTITIES.put(new Character(' '), "&#202;");
		ENTITIES.put(new Character('À'), "&#203;");
		ENTITIES.put(new Character('Ã'), "&#204;");
		ENTITIES.put(new Character('Õ'), "&#205;");
		ENTITIES.put(new Character('Œ'), "&#206;");
		ENTITIES.put(new Character('œ'), "&#207;");
		ENTITIES.put(new Character('—'), "&#209;");
		ENTITIES.put(new Character('“'), "&#210;");
		ENTITIES.put(new Character('”'), "&#211;");
		ENTITIES.put(new Character('‘'), "&#212;");
		ENTITIES.put(new Character('’'), "&#213;");
		ENTITIES.put(new Character('÷'), "&#214;");
		ENTITIES.put(new Character('Ÿ'), "&#217;");
		ENTITIES.put(new Character('⁄'), "&#218;");
		ENTITIES.put(new Character('€'), "&#219;");
		ENTITIES.put(new Character('‹'), "&#220;");
		ENTITIES.put(new Character('›'), "&#221;");
		ENTITIES.put(new Character('‡'), "&#224;");
		ENTITIES.put(new Character('·'), "&#225;");
		ENTITIES.put(new Character('‚'), "&#226;");
		ENTITIES.put(new Character('„'), "&#227;");
		ENTITIES.put(new Character('‰'), "&#228;");
		ENTITIES.put(new Character('Ê'), "&#230;");
		ENTITIES.put(new Character('Á'), "&#231;");
		ENTITIES.put(new Character('Ë'), "&#232;");
		ENTITIES.put(new Character('È'), "&#233;");
		ENTITIES.put(new Character('Í'), "&#234;");
		ENTITIES.put(new Character('Î'), "&#235;");
		ENTITIES.put(new Character('Ï'), "&#236;");
		ENTITIES.put(new Character('Ì'), "&#237;");
		ENTITIES.put(new Character('Ó'), "&#238;");
		ENTITIES.put(new Character('Ô'), "&#239;");
		ENTITIES.put(new Character(''), "&#240;");
		ENTITIES.put(new Character('Ò'), "&#241;");
		ENTITIES.put(new Character('Ú'), "&#242;");
		ENTITIES.put(new Character('Û'), "&#243;");
		ENTITIES.put(new Character('Ù'), "&#244;");
		ENTITIES.put(new Character('ı'), "&#245;");
		ENTITIES.put(new Character('ˆ'), "&#246;");
		ENTITIES.put(new Character('˘'), "&#249;");
		ENTITIES.put(new Character('˙'), "&#250;");
		ENTITIES.put(new Character('˚'), "&#251;");
		ENTITIES.put(new Character('¸'), "&#252;");
		ENTITIES.put(new Character('˝'), "&#253;");
		ENTITIES.put(new Character('ˇ'), "&#255;");
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
		HTML_ENTITIES.addElement((new String[] {"ß", "&sect;"}));
		HTML_ENTITIES.addElement((new String[] {"ﬂ", "&szlig;"}));
		HTML_ENTITIES.addElement((new String[] {"<", "&lt;"}));
		HTML_ENTITIES.addElement((new String[] {">", "&gt;"}));
		HTML_ENTITIES.addElement((new String[] {"Ä", "&euro;"}));
//		htmlEntities.addElement((new String[] {"\n", "<br>"}));
		HTML_ENTITIES.addElement((new String[] {"¿", "&Agrave;"}));
		HTML_ENTITIES.addElement((new String[] {"¡", "&Aacute;"}));
		HTML_ENTITIES.addElement((new String[] {"¬", "&Acirc;"}));
		HTML_ENTITIES.addElement((new String[] {"√", "&Atilde;"}));
		HTML_ENTITIES.addElement((new String[] {"ƒ", "&Auml;"}));
		HTML_ENTITIES.addElement((new String[] {"≈", "&Aring;"}));
		HTML_ENTITIES.addElement((new String[] {"∆", "&AElig;"}));
		HTML_ENTITIES.addElement((new String[] {"«", "&Ccedil;"}));
		HTML_ENTITIES.addElement((new String[] {"»", "&Egrave;"}));
		HTML_ENTITIES.addElement((new String[] {"…", "&Eacute;"}));
		HTML_ENTITIES.addElement((new String[] {" ", "&Ecirc;"}));
		HTML_ENTITIES.addElement((new String[] {"À", "&Euml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ã", "&Igrave;"}));
		HTML_ENTITIES.addElement((new String[] {"Õ", "&Iacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Œ", "&Icirc;"}));
		HTML_ENTITIES.addElement((new String[] {"œ", "&Iuml;"}));
		HTML_ENTITIES.addElement((new String[] {"—", "&Ntilde;"}));
		HTML_ENTITIES.addElement((new String[] {"“", "&Ograve;"}));
		HTML_ENTITIES.addElement((new String[] {"”", "&Oacute;"}));
		HTML_ENTITIES.addElement((new String[] {"‘", "&Ocirc;"}));
		HTML_ENTITIES.addElement((new String[] {"’", "&Otilde;"}));
		HTML_ENTITIES.addElement((new String[] {"÷", "&Ouml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ÿ", "&Ugrave;"}));
		HTML_ENTITIES.addElement((new String[] {"⁄", "&Uacute;"}));
		HTML_ENTITIES.addElement((new String[] {"€", "&Ucirc;"}));
		HTML_ENTITIES.addElement((new String[] {"‹", "&Uuml;"}));
		HTML_ENTITIES.addElement((new String[] {"›", "&Yacute;"}));
		HTML_ENTITIES.addElement((new String[] {"‡", "&agrave;"}));
		HTML_ENTITIES.addElement((new String[] {"·", "&aacute;"}));
		HTML_ENTITIES.addElement((new String[] {"‚", "&acirc;"}));
		HTML_ENTITIES.addElement((new String[] {"„", "&atilde;"}));
		HTML_ENTITIES.addElement((new String[] {"‰", "&auml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ê", "&aelig;"}));
		HTML_ENTITIES.addElement((new String[] {"Á", "&ccedil;"}));
		HTML_ENTITIES.addElement((new String[] {"Ë", "&egrave;"}));
		HTML_ENTITIES.addElement((new String[] {"È", "&eacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Í", "&ecirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Î", "&euml;"}));
		HTML_ENTITIES.addElement((new String[] {"Ï", "&igrave;"}));
		HTML_ENTITIES.addElement((new String[] {"Ì", "&iacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Ó", "&icirc;"}));
		HTML_ENTITIES.addElement((new String[] {"Ô", "&iuml;"}));
		HTML_ENTITIES.addElement((new String[] {"", "&eth;"}));
		HTML_ENTITIES.addElement((new String[] {"Ò", "&ntilde;"}));
		HTML_ENTITIES.addElement((new String[] {"Ú", "&ograve;"}));
		HTML_ENTITIES.addElement((new String[] {"Û", "&oacute;"}));
		HTML_ENTITIES.addElement((new String[] {"Ù", "&ocirc;"}));
		HTML_ENTITIES.addElement((new String[] {"ı", "&otilde;"}));
		HTML_ENTITIES.addElement((new String[] {"ˆ", "&ouml;"}));
		HTML_ENTITIES.addElement((new String[] {"˘", "&ugrave;"}));
		HTML_ENTITIES.addElement((new String[] {"˙", "&uacute;"}));
		HTML_ENTITIES.addElement((new String[] {"˚", "&ucirc;"}));
		HTML_ENTITIES.addElement((new String[] {"¸", "&uuml;"}));
		HTML_ENTITIES.addElement((new String[] {"˝", "&yacute;"}));
		HTML_ENTITIES.addElement((new String[] {"ˇ", "&yuml;"}));
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
		HTML_2_XML_ENTITIES.put("Á",		"&#135;");
		HTML_2_XML_ENTITIES.put("·",		"&#160;");
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
