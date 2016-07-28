package net.anotheria.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Performs html and xml encoding.
 */
public class CharacterEntityCoder {
	private static final Map<Character, String> ENTITIES;
	private static final List<String[]> HTML_ENTITIES;
	private static final Map<String,String> HTML_2_XML_ENTITIES;
	
	
	static {
		ENTITIES = new HashMap<>();
		ENTITIES.put('\n', "&#010;");
		ENTITIES.put('\r', "&#013;");
		ENTITIES.put('\t', "&#009;");
		ENTITIES.put('&', "&#038;");
		ENTITIES.put('\"', "&#034;");
		ENTITIES.put('ß', "&#167;");
		ENTITIES.put('ﬂ', "&#223;");
		ENTITIES.put('<', "&#060;");
		ENTITIES.put('>', "&#062;");
		ENTITIES.put('Ä', "&#198;");
		ENTITIES.put('¿', "&#192;");
		ENTITIES.put('¡', "&#193;");
		ENTITIES.put('¬', "&#194;");
		ENTITIES.put('√', "&#195;");
		ENTITIES.put('ƒ', "&#196;");
		ENTITIES.put('≈', "&#197;");
		ENTITIES.put('∆', "&#198;");
		ENTITIES.put('«', "&#199;");
		ENTITIES.put('»', "&#200;");
		ENTITIES.put('…', "&#201;");
		ENTITIES.put(' ', "&#202;");
		ENTITIES.put('À', "&#203;");
		ENTITIES.put('Ã', "&#204;");
		ENTITIES.put('Õ', "&#205;");
		ENTITIES.put('Œ', "&#206;");
		ENTITIES.put('œ', "&#207;");
		ENTITIES.put('—', "&#209;");
		ENTITIES.put('“', "&#210;");
		ENTITIES.put('”', "&#211;");
		ENTITIES.put('‘', "&#212;");
		ENTITIES.put('’', "&#213;");
		ENTITIES.put('÷', "&#214;");
		ENTITIES.put('Ÿ', "&#217;");
		ENTITIES.put('⁄', "&#218;");
		ENTITIES.put('€', "&#219;");
		ENTITIES.put('‹', "&#220;");
		ENTITIES.put('›', "&#221;");
		ENTITIES.put('‡', "&#224;");
		ENTITIES.put('·', "&#225;");
		ENTITIES.put('‚', "&#226;");
		ENTITIES.put('„', "&#227;");
		ENTITIES.put('‰', "&#228;");
		ENTITIES.put('Ê', "&#230;");
		ENTITIES.put('Á', "&#231;");
		ENTITIES.put('Ë', "&#232;");
		ENTITIES.put('È', "&#233;");
		ENTITIES.put('Í', "&#234;");
		ENTITIES.put('Î', "&#235;");
		ENTITIES.put('Ï', "&#236;");
		ENTITIES.put('Ì', "&#237;");
		ENTITIES.put('Ó', "&#238;");
		ENTITIES.put('Ô', "&#239;");
		ENTITIES.put('', "&#240;");
		ENTITIES.put('Ò', "&#241;");
		ENTITIES.put('Ú', "&#242;");
		ENTITIES.put('Û', "&#243;");
		ENTITIES.put('Ù', "&#244;");
		ENTITIES.put('ı', "&#245;");
		ENTITIES.put('ˆ', "&#246;");
		ENTITIES.put('˘', "&#249;");
		ENTITIES.put('˙', "&#250;");
		ENTITIES.put('˚', "&#251;");
		ENTITIES.put('¸', "&#252;");
		ENTITIES.put('˝', "&#253;");
		ENTITIES.put('ˇ', "&#255;");
		ENTITIES.put('-', "&#8212;");
		
		HTML_ENTITIES = new ArrayList<>();
		HTML_ENTITIES.add(new String[] {"&", "&amp;"});
		HTML_ENTITIES.add(new String[] {"\"", "&quot;"});
		HTML_ENTITIES.add(new String[] {"ß", "&sect;"});
		HTML_ENTITIES.add(new String[] {"ﬂ", "&szlig;"});
		HTML_ENTITIES.add(new String[] {"<", "&lt;"});
		HTML_ENTITIES.add(new String[] {">", "&gt;"});
		HTML_ENTITIES.add(new String[] {"Ä", "&euro;"});
		HTML_ENTITIES.add(new String[] {"¿", "&Agrave;"});
		HTML_ENTITIES.add(new String[] {"¡", "&Aacute;"});
		HTML_ENTITIES.add(new String[] {"¬", "&Acirc;"});
		HTML_ENTITIES.add(new String[] {"√", "&Atilde;"});
		HTML_ENTITIES.add(new String[] {"ƒ", "&Auml;"});
		HTML_ENTITIES.add(new String[] {"≈", "&Aring;"});
		HTML_ENTITIES.add(new String[] {"∆", "&AElig;"});
		HTML_ENTITIES.add(new String[] {"«", "&Ccedil;"});
		HTML_ENTITIES.add(new String[] {"»", "&Egrave;"});
		HTML_ENTITIES.add(new String[] {"…", "&Eacute;"});
		HTML_ENTITIES.add(new String[] {" ", "&Ecirc;"});
		HTML_ENTITIES.add(new String[] {"À", "&Euml;"});
		HTML_ENTITIES.add(new String[] {"Ã", "&Igrave;"});
		HTML_ENTITIES.add(new String[] {"Õ", "&Iacute;"});
		HTML_ENTITIES.add(new String[] {"Œ", "&Icirc;"});
		HTML_ENTITIES.add(new String[] {"œ", "&Iuml;"});
		HTML_ENTITIES.add(new String[] {"—", "&Ntilde;"});
		HTML_ENTITIES.add(new String[] {"“", "&Ograve;"});
		HTML_ENTITIES.add(new String[] {"”", "&Oacute;"});
		HTML_ENTITIES.add(new String[] {"‘", "&Ocirc;"});
		HTML_ENTITIES.add(new String[] {"’", "&Otilde;"});
		HTML_ENTITIES.add(new String[] {"÷", "&Ouml;"});
		HTML_ENTITIES.add(new String[] {"Ÿ", "&Ugrave;"});
		HTML_ENTITIES.add(new String[] {"⁄", "&Uacute;"});
		HTML_ENTITIES.add(new String[] {"€", "&Ucirc;"});
		HTML_ENTITIES.add(new String[] {"‹", "&Uuml;"});
		HTML_ENTITIES.add(new String[] {"›", "&Yacute;"});
		HTML_ENTITIES.add(new String[] {"‡", "&agrave;"});
		HTML_ENTITIES.add(new String[] {"·", "&aacute;"});
		HTML_ENTITIES.add(new String[] {"‚", "&acirc;"});
		HTML_ENTITIES.add(new String[] {"„", "&atilde;"});
		HTML_ENTITIES.add(new String[] {"‰", "&auml;"});
		HTML_ENTITIES.add(new String[] {"Ê", "&aelig;"});
		HTML_ENTITIES.add(new String[] {"Á", "&ccedil;"});
		HTML_ENTITIES.add(new String[] {"Ë", "&egrave;"});
		HTML_ENTITIES.add(new String[] {"È", "&eacute;"});
		HTML_ENTITIES.add(new String[] {"Í", "&ecirc;"});
		HTML_ENTITIES.add(new String[] {"Î", "&euml;"});
		HTML_ENTITIES.add(new String[] {"Ï", "&igrave;"});
		HTML_ENTITIES.add(new String[] {"Ì", "&iacute;"});
		HTML_ENTITIES.add(new String[] {"Ó", "&icirc;"});
		HTML_ENTITIES.add(new String[] {"Ô", "&iuml;"});
		HTML_ENTITIES.add(new String[] {"", "&eth;"});
		HTML_ENTITIES.add(new String[] {"Ò", "&ntilde;"});
		HTML_ENTITIES.add(new String[] {"Ú", "&ograve;"});
		HTML_ENTITIES.add(new String[] {"Û", "&oacute;"});
		HTML_ENTITIES.add(new String[] {"Ù", "&ocirc;"});
		HTML_ENTITIES.add(new String[] {"ı", "&otilde;"});
		HTML_ENTITIES.add(new String[] {"ˆ", "&ouml;"});
		HTML_ENTITIES.add(new String[] {"˘", "&ugrave;"});
		HTML_ENTITIES.add(new String[] {"˙", "&uacute;"});
		HTML_ENTITIES.add(new String[] {"˚", "&ucirc;"});
		HTML_ENTITIES.add(new String[] {"¸", "&uuml;"});
		HTML_ENTITIES.add(new String[] {"˝", "&yacute;"});
		HTML_ENTITIES.add(new String[] {"ˇ", "&yuml;"});
		HTML_ENTITIES.add(new String[] {"-", "&mdash;"});

		HTML_2_XML_ENTITIES = new HashMap<>();
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
	public static String encodeString(String s) {
		StringBuilder result = new StringBuilder("");
		for (int i=0; i<s.length(); i++){
			Character c = s.charAt(i);
   			String temp = ENTITIES.get(c);
      		result.append(temp == null ? String.valueOf(s.charAt(i)) : temp);
       	}
		return result.toString();
	}
	/**
	* returns encoded String for HTML use
	**/
	public static String htmlEncodeString(String s) {
        for (String[] sa : HTML_ENTITIES) {
            char o = sa[0].charAt(0);
            String n = sa[1];
            s = StringUtils.replace(s, o, n);
        }
		return s;
	}

	/**
	* returns dencoded String from XML
	**/
	public static String decodeString(String s) {
		for (Entry<Character, String> characterStringEntry : ENTITIES.entrySet()) {
			String n = characterStringEntry.getValue();
			int j = n.length();
			String s1 = "";
			int lastI = 0;
			for (int i = 0; i < s.length() - j; i++) {
				if (s.substring(i, i + j).equals(n)) {
					s1 = s1 + s.substring(lastI, i) + characterStringEntry.getKey();
					lastI = i + j;
					i = lastI;
				}
			}
			if (!s1.isEmpty())
				s = s1;
		}

		return s;
	}

	/**
	* returns dencoded String from HTML
	**/
	public static String decodeHtmlString(String s) {
        for (String[] sa : HTML_ENTITIES) {
            char o = sa[0].charAt(0);
            String n = sa[1];
            int j = n.length();
            String s1 = "";
            int lastI = 0;
            for (int i = 0; i < s.length() - j; i++) {
                //System.out.println("##:"+s.substring(i,i+j)+"  >>> "+n);
                if (s.substring(i, i + j).equals(n)) {
                    s1 = s1 + s.substring(lastI, i) + o;
                    //System.out.println("###:"+s1);
                    lastI = i + j;
                    i = lastI;
                }
            }
            if (!s1.isEmpty())
                s = s1;
        }
		return s;
	}

	public static String htm2xml(String s){
		for (Map.Entry<String, String> stringStringEntry : HTML_2_XML_ENTITIES.entrySet()) {
			String xmlString = stringStringEntry.getValue();
			int j = stringStringEntry.getKey().length();
			int lastI = 0;
			String replaced = "";
			for (int i = 0; i < s.length() - j; i++) {
				if (s.substring(i, i + j).equals(stringStringEntry.getKey())) {
					//System.out.println("ON Replace "+s.substring(i,i+j)+" -> "+s.substring(lastI, i)+xmlString);
					replaced = replaced + s.substring(lastI, i) + xmlString;
					//System.out.println("###:"+replaced);
					lastI = i + j;
					i = lastI - 1;
				}
			}
			replaced = replaced + s.substring(lastI, s.length());
			if (!replaced.isEmpty())
				s = replaced;
		}
		return s;
	}
}
