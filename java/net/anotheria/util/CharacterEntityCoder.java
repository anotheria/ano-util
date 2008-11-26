package net.anotheria.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Performs html and xml encoding.
 */
public class CharacterEntityCoder {
	private static final Hashtable<Character, String> entities;
	private static final Vector<String[]> htmlEntities;
	private static final Hashtable<String,String> html2xmlEntities;
	
	
	static {
		entities = new Hashtable<Character, String>();
		entities.put(new Character('\n'), "&#010;");
		entities.put(new Character('\r'), "&#013;");
		entities.put(new Character('\t'), "&#009;");
		entities.put(new Character('&'), "&#038;");
		entities.put(new Character('\"'), "&#034;");
		entities.put(new Character('§'), "&#167;");
		entities.put(new Character('ß'), "&#223;");
		entities.put(new Character('<'), "&#060;");
		entities.put(new Character('>'), "&#062;");
		entities.put(new Character('€'), "&#198;");
		entities.put(new Character('À'), "&#192;");
		entities.put(new Character('Á'), "&#193;");
		entities.put(new Character('Â'), "&#194;");
		entities.put(new Character('Ã'), "&#195;");
		entities.put(new Character('Ä'), "&#196;");
		entities.put(new Character('Å'), "&#197;");
		entities.put(new Character('Æ'), "&#198;");
		entities.put(new Character('Ç'), "&#199;");
		entities.put(new Character('È'), "&#200;");
		entities.put(new Character('É'), "&#201;");
		entities.put(new Character('Ê'), "&#202;");
		entities.put(new Character('Ë'), "&#203;");
		entities.put(new Character('Ì'), "&#204;");
		entities.put(new Character('Í'), "&#205;");
		entities.put(new Character('Î'), "&#206;");
		entities.put(new Character('Ï'), "&#207;");
		entities.put(new Character('Ñ'), "&#209;");
		entities.put(new Character('Ò'), "&#210;");
		entities.put(new Character('Ó'), "&#211;");
		entities.put(new Character('Ô'), "&#212;");
		entities.put(new Character('Õ'), "&#213;");
		entities.put(new Character('Ö'), "&#214;");
		entities.put(new Character('Ù'), "&#217;");
		entities.put(new Character('Ú'), "&#218;");
		entities.put(new Character('Û'), "&#219;");
		entities.put(new Character('Ü'), "&#220;");
		entities.put(new Character('Ý'), "&#221;");
		entities.put(new Character('à'), "&#224;");
		entities.put(new Character('á'), "&#225;");
		entities.put(new Character('â'), "&#226;");
		entities.put(new Character('ã'), "&#227;");
		entities.put(new Character('ä'), "&#228;");
		entities.put(new Character('æ'), "&#230;");
		entities.put(new Character('ç'), "&#231;");
		entities.put(new Character('è'), "&#232;");
		entities.put(new Character('é'), "&#233;");
		entities.put(new Character('ê'), "&#234;");
		entities.put(new Character('ë'), "&#235;");
		entities.put(new Character('ì'), "&#236;");
		entities.put(new Character('í'), "&#237;");
		entities.put(new Character('î'), "&#238;");
		entities.put(new Character('ï'), "&#239;");
		entities.put(new Character('ð'), "&#240;");
		entities.put(new Character('ñ'), "&#241;");
		entities.put(new Character('ò'), "&#242;");
		entities.put(new Character('ó'), "&#243;");
		entities.put(new Character('ô'), "&#244;");
		entities.put(new Character('õ'), "&#245;");
		entities.put(new Character('ö'), "&#246;");
		entities.put(new Character('ù'), "&#249;");
		entities.put(new Character('ú'), "&#250;");
		entities.put(new Character('û'), "&#251;");
		entities.put(new Character('ü'), "&#252;");
		entities.put(new Character('ý'), "&#253;");
		entities.put(new Character('ÿ'), "&#255;");
		entities.put(new Character('-'), "&#8212;");
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
		
		htmlEntities = new Vector<String[]>();
		htmlEntities.addElement((new String[] {"&", "&amp;"}));
		htmlEntities.addElement((new String[] {"\"", "&quot;"}));
		htmlEntities.addElement((new String[] {"§", "&sect;"}));
		htmlEntities.addElement((new String[] {"ß", "&szlig;"}));
		htmlEntities.addElement((new String[] {"<", "&lt;"}));
		htmlEntities.addElement((new String[] {">", "&gt;"}));
		htmlEntities.addElement((new String[] {"€", "&euro;"}));
//		htmlEntities.addElement((new String[] {"\n", "<br>"}));
		htmlEntities.addElement((new String[] {"À", "&Agrave;"}));
		htmlEntities.addElement((new String[] {"Á", "&Aacute;"}));
		htmlEntities.addElement((new String[] {"Â", "&Acirc;"}));
		htmlEntities.addElement((new String[] {"Ã", "&Atilde;"}));
		htmlEntities.addElement((new String[] {"Ä", "&Auml;"}));
		htmlEntities.addElement((new String[] {"Å", "&Aring;"}));
		htmlEntities.addElement((new String[] {"Æ", "&AElig;"}));
		htmlEntities.addElement((new String[] {"Ç", "&Ccedil;"}));
		htmlEntities.addElement((new String[] {"È", "&Egrave;"}));
		htmlEntities.addElement((new String[] {"É", "&Eacute;"}));
		htmlEntities.addElement((new String[] {"Ê", "&Ecirc;"}));
		htmlEntities.addElement((new String[] {"Ë", "&Euml;"}));
		htmlEntities.addElement((new String[] {"Ì", "&Igrave;"}));
		htmlEntities.addElement((new String[] {"Í", "&Iacute;"}));
		htmlEntities.addElement((new String[] {"Î", "&Icirc;"}));
		htmlEntities.addElement((new String[] {"Ï", "&Iuml;"}));
		htmlEntities.addElement((new String[] {"Ñ", "&Ntilde;"}));
		htmlEntities.addElement((new String[] {"Ò", "&Ograve;"}));
		htmlEntities.addElement((new String[] {"Ó", "&Oacute;"}));
		htmlEntities.addElement((new String[] {"Ô", "&Ocirc;"}));
		htmlEntities.addElement((new String[] {"Õ", "&Otilde;"}));
		htmlEntities.addElement((new String[] {"Ö", "&Ouml;"}));
		htmlEntities.addElement((new String[] {"Ù", "&Ugrave;"}));
		htmlEntities.addElement((new String[] {"Ú", "&Uacute;"}));
		htmlEntities.addElement((new String[] {"Û", "&Ucirc;"}));
		htmlEntities.addElement((new String[] {"Ü", "&Uuml;"}));
		htmlEntities.addElement((new String[] {"Ý", "&Yacute;"}));
		htmlEntities.addElement((new String[] {"à", "&agrave;"}));
		htmlEntities.addElement((new String[] {"á", "&aacute;"}));
		htmlEntities.addElement((new String[] {"â", "&acirc;"}));
		htmlEntities.addElement((new String[] {"ã", "&atilde;"}));
		htmlEntities.addElement((new String[] {"ä", "&auml;"}));
		htmlEntities.addElement((new String[] {"æ", "&aelig;"}));
		htmlEntities.addElement((new String[] {"ç", "&ccedil;"}));
		htmlEntities.addElement((new String[] {"è", "&egrave;"}));
		htmlEntities.addElement((new String[] {"é", "&eacute;"}));
		htmlEntities.addElement((new String[] {"ê", "&ecirc;"}));
		htmlEntities.addElement((new String[] {"ë", "&euml;"}));
		htmlEntities.addElement((new String[] {"ì", "&igrave;"}));
		htmlEntities.addElement((new String[] {"í", "&iacute;"}));
		htmlEntities.addElement((new String[] {"î", "&icirc;"}));
		htmlEntities.addElement((new String[] {"ï", "&iuml;"}));
		htmlEntities.addElement((new String[] {"ð", "&eth;"}));
		htmlEntities.addElement((new String[] {"ñ", "&ntilde;"}));
		htmlEntities.addElement((new String[] {"ò", "&ograve;"}));
		htmlEntities.addElement((new String[] {"ó", "&oacute;"}));
		htmlEntities.addElement((new String[] {"ô", "&ocirc;"}));
		htmlEntities.addElement((new String[] {"õ", "&otilde;"}));
		htmlEntities.addElement((new String[] {"ö", "&ouml;"}));
		htmlEntities.addElement((new String[] {"ù", "&ugrave;"}));
		htmlEntities.addElement((new String[] {"ú", "&uacute;"}));
		htmlEntities.addElement((new String[] {"û", "&ucirc;"}));
		htmlEntities.addElement((new String[] {"ü", "&uuml;"}));
		htmlEntities.addElement((new String[] {"ý", "&yacute;"}));
		htmlEntities.addElement((new String[] {"ÿ", "&yuml;"}));
		htmlEntities.addElement((new String[] {"-", "&mdash;"}));

		html2xmlEntities = new Hashtable<String,String>();
		html2xmlEntities.put("&amp;",   "&#038;");
		html2xmlEntities.put("&ouml;",  "&#246;");
		html2xmlEntities.put("&auml;",  "&#228;");
		html2xmlEntities.put("&uuml;",  "&#252;");
		html2xmlEntities.put("&Uuml;",  "&#220;");
		html2xmlEntities.put("&Ouml;",  "&#214;");
		html2xmlEntities.put("&Auml;",  "&#196;");
		html2xmlEntities.put("&quot;",  "&#034;");
		html2xmlEntities.put("&sect;",  "&#167;");
		html2xmlEntities.put("&szlig;", "&#223;");
		html2xmlEntities.put("&lt;",    "&#060;");
		html2xmlEntities.put("&gt;",    "&#062;");
		html2xmlEntities.put("&euro;",  "&#198;");
		html2xmlEntities.put("ç",		"&#135;");
		html2xmlEntities.put("á",		"&#160;");
		html2xmlEntities.put("&agrave;",	"&#133;");
		html2xmlEntities.put("&acirc;",	"&#131;");
		html2xmlEntities.put("&Agrave;",	"&#192;");
		html2xmlEntities.put("&Aacute;",	"&#193;");
		html2xmlEntities.put("&Acirc;",	"&#194;");
		html2xmlEntities.put("&Atilde;",	"&#195;");
		html2xmlEntities.put("&Auml;",	"&#196;");
		html2xmlEntities.put("&Aring;",	"&#197;");
		html2xmlEntities.put("&AElig;",	"&#198;");
		html2xmlEntities.put("&Ccedil;",	"&#199;");
		html2xmlEntities.put("&Egrave;",	"&#200;");
		html2xmlEntities.put("&Eacute;",	"&#201;");
		html2xmlEntities.put("&Ecirc;",	"&#202;");
		html2xmlEntities.put("&Euml;",	"&#203;");
		html2xmlEntities.put("&Igrave;",	"&#204;");
		html2xmlEntities.put("&Iacute;",	"&#205;");
		html2xmlEntities.put("&Icirc;",	"&#206;");
		html2xmlEntities.put("&Iuml;",	"&#207;");
		html2xmlEntities.put("&Ntilde;",	"&#209;");
		html2xmlEntities.put("&Ograve;",	"&#210;");
		html2xmlEntities.put("&Oacute;",	"&#211;");
		html2xmlEntities.put("&Ocirc;",	"&#212;");
		html2xmlEntities.put("&Otilde;",	"&#213;");
		html2xmlEntities.put("&Ouml;",	"&#214;");
		html2xmlEntities.put("&Ugrave;",	"&#217;");
		html2xmlEntities.put("&Uacute;",	"&#218;");
		html2xmlEntities.put("&Ucirc;",	"&#219;");
		html2xmlEntities.put("&Uuml;",	"&#220;");
		html2xmlEntities.put("&Yacute;",	"&#221;");
		html2xmlEntities.put("&agrave;",	"&#224;");
		html2xmlEntities.put("&aacute;",	"&#225;");
		html2xmlEntities.put("&acirc;",	"&#226;");
		html2xmlEntities.put("&atilde;",	"&#227;");
		html2xmlEntities.put("&aelig;",	"&#230;");
		html2xmlEntities.put("&ccedil;",	"&#231;");
		html2xmlEntities.put("&egrave;",	"&#232;");
		html2xmlEntities.put("&eacute;",	"&#233;");
		html2xmlEntities.put("&ecirc;",	"&#234;");
		html2xmlEntities.put("&euml;",	"&#235;");
		html2xmlEntities.put("&igrave;",	"&#236;");
		html2xmlEntities.put("&iacute;",	"&#237;");
		html2xmlEntities.put("&icirc;",	"&#238;");
		html2xmlEntities.put("&iuml;",	"&#239;");
		html2xmlEntities.put("&eth;",	"&#240;");
		html2xmlEntities.put("&ntilde;",	"&#241;");
		html2xmlEntities.put("&ograve;",	"&#242;");
		html2xmlEntities.put("&oacute;",	"&#243;");
		html2xmlEntities.put("&ocirc;",	"&#244;");
		html2xmlEntities.put("&otilde;",	"&#245;");
		html2xmlEntities.put("&ugrave;",	"&#249;");
		html2xmlEntities.put("&uacute;",	"&#250;");
		html2xmlEntities.put("&ucirc;",	"&#251;");
		html2xmlEntities.put("&uuml;",	"&#252;");
		html2xmlEntities.put("&yacute;",	"&#253;");
		html2xmlEntities.put("&yuml;",	"&#255;");
		html2xmlEntities.put("&mdash;",	"&#8212;");
	}

	/**
	* returns encoded String for XML use
	**/
	public static final String encodeString(String s) {
		StringBuffer result = new StringBuffer("");
		for (int i=0; i<s.length(); i++){
			Character c = new Character(s.charAt(i));
   			String temp = (String)entities.get(c);
      		result.append(temp == null ? ""+s.charAt(i) : temp);
       	}
		return result.toString();
	}
	/**
	* returns encoded String for HTML use
	**/
	public static final String htmlEncodeString(String s) {
		for (int i=0; i<htmlEntities.size(); i++) {
			String[] sa = (String[]) htmlEntities.elementAt(i);
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
		Enumeration<Character> e=entities.keys();
		while (e.hasMoreElements()) {
			Character o = e.nextElement();
			String n = (String) entities.get(o);
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
		for (int k=0; k<htmlEntities.size(); k++) {
			String[] sa = (String[]) htmlEntities.elementAt(k);
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
		Enumeration<String> e = html2xmlEntities.keys();
		while(e.hasMoreElements()){
			String htmString = e.nextElement();
			String xmlString = html2xmlEntities.get(htmString);
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
	
	public static void main(String a[]){
		Enumeration<Character> e = entities.keys();
		while(e.hasMoreElements()){
			Character c = e.nextElement();
			System.out.println(c+" "+ (int)c.charValue());
		}
	}

}
