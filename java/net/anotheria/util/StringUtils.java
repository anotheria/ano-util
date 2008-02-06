package net.anotheria.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

/**
 * StringUtils -
 * some small but usefull utils for String handling.
 * @version 1.3
 * @author Leon Rosenberg
 */

public class StringUtils{

	/**
  	 *Return a Vector with tokens from the source string tokenized using the delimiter char.
     */
    public static final Vector<String> tokenize2vector(String source, char delimiter){
		Vector<String> v;
		v = new Vector<String>();
  		StringBuffer currentS = new StringBuffer();
		char c;
		for ( int i=0;i<source.length() ;i++ ){
			c = source.charAt(i);
			if ( c==delimiter ){
				if ( currentS.length()>0 ){
					v.addElement(currentS.toString());
				}else{
				    v.addElement(new String(""));
				}
				currentS=new StringBuffer();
			}else{
				currentS.append(c);
			}
		}
		if ( currentS!=null && currentS.length()>0 )
			v.addElement(currentS.toString());
   		return v;
    }
    
    public static final List<String> tokenize2list(String source, char delimiter){
    	return Arrays.asList(tokenize(source, delimiter));
    }
    
    /**
	  *Returns an array of stringtokens from the source string.
	  *The String "Leon Power Tools" with delimiter ' ' will return {"Leon","Power","Tools"}
	Last change:  LR   15 Aug 98    4:23 pm
	  */
	public static final String[] tokenize(String source, char delimiter){
		String[] ret;
		Vector v = tokenize2vector(source, delimiter);
		ret = new String[v.size()];
		for ( int i=0;i<v.size() ;i++ ){
			ret[i] = (String) v.elementAt(i);
		}
		return ret;
	}/*end fun tokenize()*/

	/**
	 *Returns a source String with all occurences of 'c' removed.
	 *removeChar("Leon's Power Tools", ' ') will return "Leon'sPowerTools".
	 */
	public static final String removeChar(String src, char c){
		StringBuffer ret=new StringBuffer();
		for ( int i=0;i<src.length() ;i++ )
			if (src.charAt(i)!=c)
				ret.append(src.charAt(i));
		return ret.toString();
	}/*end of fun removeChar()*/

	public static final String removeChars(String src, char c[]){
		StringBuffer ret=new StringBuffer();
		for ( int i=0;i<src.length() ;i++ ){
			char aC = src.charAt(i);
			boolean append = true;
			for (int t=0; t<c.length; t++)
				if (aC == c[t]){
					append = false;
					break;
				}
			if (append)
				ret.append(aC);
		}
		return ret.toString();
	}

	/**
	 *Returns first line of multilined String.
	 */
	public static String getFirstLine(String source){
		String line = new String("");
		int i=0;
		while( i<source.length() ){
			line += source.charAt(i);
		    if ( source.charAt(i++)=='\n' )
			    break;
		}
		return line;
	}

	/**
	  *Builds a hash table from the String like *(TYPE : MESSAGE + CR)
	  *useful for http requests etc.
	  */
	public static Hashtable buildHashTable(String source){
	    source = removeChar(source,'\r');
		Hashtable ret = new Hashtable(charCount(source,'\n'));
		String[] lines = tokenize(source,'\n');
		for ( int i=0;i<java.lang.reflect.Array.getLength(lines) ;i++ ){
		    /*String[] uline = tokenize(lines[i],':');
			if ( java.lang.reflect.Array.getLength(uline)==2 ){
			    ret.put(uline[0].trim(),uline[1].trim());
			}*/
			StringPair sp = splitString(lines[i],':');
			if ( sp.first!=null && sp.second!=null )
				ret.put(sp.first.trim(),sp.second.trim());
		}
		return ret;
	}

	/**
  	 * Returns the number of occurencies of the specified cha rin the given string.
     */
	public static int charCount(String source, char toCount){
	    int sum = 0;
		for ( int i=0;i<source.length() ;i++ )
			if ( source.charAt(i)==toCount )
			    sum++;
		return sum;
	}

	/**
	 *Returns a new String which equals source String
	 */
	public static String _replace2(String source, char replaceChar, char replaceWith){
	    String dest = new String("");
		int i = 0;
		while ( i<source.length() )
		    dest += source.charAt(i++)==replaceChar ? replaceWith : source.charAt(i-1);

		return dest;
	}

	public static String replace(String source, char replaceChar, char replaceWith){
	    StringBuffer dest = new StringBuffer(source.length());
		int i = 0;
		while ( i<source.length() )
		    dest.append(source.charAt(i++)==replaceChar ? replaceWith : source.charAt(i-1));

		return dest.toString();
	}

	public static String replace(String source, char replaceChar, String replaceWith){
  		String dest = new String("");
		int i = 0;
		while ( i<source.length() )
		    dest += source.charAt(i++)==replaceChar ? replaceWith : ""+source.charAt(i-1);

		return dest;
   	}


	/**
	 * - kle 14.06.2001
	 * Replace  a set of chars with a set of strings
	 * @param sorce the source string
	 * @param map replace map
	 * @return a source string where all chars that are members of map keys are replaced with the interlocking values
	 **/
	public static String replace(String source, Hashtable map){
  		String dest = new String("");
		int i = 0;
		while ( i<source.length() ){
			String replaceWith = (String)map.get(""+source.charAt(i++));
			if(replaceWith != null)
			    dest += replaceWith ;
			else
				dest += ""+source.charAt(i-1);
		}
		return dest;
   	}

 	public static StringPair splitString(String source,char delimiter){
		int del_pos = source.indexOf((int)delimiter);
		if ( del_pos==-1 )
		    return (new StringUtils()).new StringPair();
		return (new StringUtils()).new StringPair(source.substring(0,del_pos),source.substring(del_pos+1,source.length()));

	}


	public class StringPair{
	    public String first,second;
		StringPair(String first,String second){
			this.first = first;
			this.second = second;
		}
		StringPair(){
			this(null,null);
		}

	}

 	/**
     *Reverses the order in the Vector.
     */
 	public static Vector reverse(Vector v){
  		Vector ret = new Vector(v.size());
    	for (int i=v.size()-1; i>=0; i--)
     		ret.addElement(v.elementAt(i));
       	return ret;
   	}

 	public static final String concat(String a, String b){
 		return concat(a, b, " ");
 	}

 	public static final String concat(String a, String b, String delimiter){
 		return (a==null || a.length()==0) ? b : (b == null || b.length() == 0 )? a : a+delimiter+b;
 	}
 	
	public static String combineStrings(String[] strings, char delimiter){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<strings.length; i++){
			ret.append(strings[i]);
			if (i<strings.length-1)
				ret.append(delimiter);
		}
		return ret.toString();
	}

 	
 	public static final String capitalize(String s){
 		char c = s.charAt(0);
 		return Character.toUpperCase(c)+s.substring(1);
 	}
	public static String getStringAfter(String src, String toSearch, int start){
		int ind = src.indexOf(toSearch, start);
//		lastIndex = ind;
		if (ind==-1)
			return "";
		return src.substring(ind+toSearch.length());
	}

	public static String getStringAfter(String src, String toSearch){
		return getStringAfter(src, toSearch, 0);
	}
	
	public static String getStringAfterIncl(String src, String toSearch, int start){
		int ind = src.indexOf(toSearch, start);
//		lastIndex = ind;
		if (ind==-1)
			return "";
		return src.substring(ind);
	}
	
	public static String getStringAfterIncl(String src, String toSearch){
		return getStringAfterIncl(src, toSearch, 0);
	}

	public static String getStringWith(String src, String toSearch, int start){
		//System.out.println("search:"+toSearch+"in:"+src);
		int ind = src.indexOf(toSearch, start);
		//lastIndex = ind;
		if (ind==-1)
			return "";
		return src.substring(ind);
	}

	public static String getStringWith(String src, String toSearch){
		return getStringWith(src, toSearch, 0);
	}

	public static String getStringBefore(String src, String toSearch, int start){
		//System.out.println("search:"+toSearch+"in:"+src);
		int ind = src.indexOf(toSearch, start);
		//lastIndex = ind;
		if (ind==-1)
			return "";
		return src.substring(start, ind);
	}

	public static String getStringBefore(String src, String toSearch){
		return getStringBefore(src, toSearch, 0);
	}

	public static String getStringBeforeIncl(String src, String toSearch, int start){
		//System.out.println("search:"+toSearch+"in:"+src);
		int ind = src.indexOf(toSearch, start);
		//lastIndex = ind;
		if (ind==-1)
			return "";
		return src.substring(start, ind+toSearch.length());
	}

	public static String getStringBeforeIncl(String src, String toSearch){
		return getStringBeforeIncl(src, toSearch, 0);
	} 
	
	public static String removeImgTag(String s){
		return removeTag(s,"img");
	}

	public static String removeAnchorTag(String s){
		return removeTag(s,"a");
	}

	public static String removeAnchorEndTag(String s){
		return removeTag(s,"/a");
	}

	public static String removeTag(String s, String tag){
		tag = "<"+tag;
		int index;
		while ( (index=s.indexOf(tag))!=-1){
			String r = getStringBefore(s, tag);
			r += getStringAfter(s, ">", index);
			s = r;
		}
		return s;
	}


	public static String removeString(String s, String toRemove) {
		int index;
		while( (index=s.indexOf(toRemove)) !=-1){
			String r = getStringBefore(s, toRemove);
			r += getStringAfter(s, toRemove, index);
			s = r;
		}
		return s;
	}
	
	
 	
 	public static final String replaceOnce(String src, String toReplace, String with){
 		int index = src.indexOf(toReplace);
 		if (index==-1)
 		    return src;
 		
		String s = src.substring(0, index);
		s+= with;
		s+= src.substring(index+toReplace.length(), src.length());
		src = s;
 		return src;   
 	}

 	public static final String replace(String src, String toReplace, String with){
 		int index = 0;
 		while ( (index=src.indexOf(toReplace))>-1){
 			String s = src.substring(0, index);
 			s+= with;
 			s+= src.substring(index+toReplace.length(), src.length());
 			src = s;
 		}
 		return src;   
 	}
 	
	public static final String replace(String src, String toReplace, char with){
		int index = 0;
		while ( (index=src.indexOf(toReplace))>-1){
			StringBuffer s = new StringBuffer(src.substring(0, index));
			s.append(with);
			s.append( src.substring(index+toReplace.length(), src.length()));
			src = s.toString();
		}
		return src;   
	}

 	
 	public static final String removeCComments(String src){
 		StringBuffer ret = new StringBuffer();
 		
 		boolean inComments = false;
 		
 		for (int i=0; i<src.length(); i++){
 			char c = src.charAt(i);
 			if (inComments){
 				if (c=='*')
 					if (src.charAt(i+1)=='/'){
 						inComments = false;
 						i++;
 					}
 			}else{
 				if (c=='/'){
 					if (src.charAt(i+1)=='*'){
 						inComments = true;
 						i++;
 					}else{
						ret.append(c);
 					}
 				}else
 					ret.append(c);
 			}
 		}
 		
 		return ret.toString();
 	}
 	
 	public static final String removeCPPComments(String src){
		StringBuffer ret = new StringBuffer();
 		
		boolean inComments = false;
 		
		for (int i=0; i<src.length(); i++){
			char c = src.charAt(i);
			if (inComments){
				if (c=='\n')
						inComments = false;
			}else{
				if (c=='/'){
					if (src.charAt(i+1)=='/'){
						inComments = true;
						i++;
					}else{
						ret.append(c);
					}
				}else
					ret.append(c);
			}
		} 
 		
		return ret.toString();
 	}
 	
	public static String reverseString(String src){
		String ret = "";
		for (int i=src.length()-1; i>=0; i--)
			ret += src.charAt(i);
		return ret;
	}
	
	public static String makeDelimitedString(String src, String delimiter, int interval){
		String ret = "";
		int t = 0;
		for (int i=src.length()-1; i>=0; i--){
			ret += src.charAt(i);
			t++;
			if (t % interval == 0 && i!=0)
				ret += delimiter;
		}
		return reverseString(ret);
	}


	/**
	 * Prefills the given string with the string as long as the size of the resulting string is less then desiredLength;
	 * Example: prefill("1", 4, "0") -> 0001.
	 */
	public static String prefill(String s, int desiredLength, String fillString){
		while (s.length()<desiredLength)
			s = fillString+s;
		return s;			
	}
	
	/**
	 * Postfills (appends) the given string with the string as long as the size of the resulting string is less then desiredLength;
	 * Example: prefill("1", 4, "0") -> 0001.
	 */
	public static String postfill(String s, int desiredLength, String fillString){
		while (s.length()<desiredLength)
			s = s+fillString;
		return s;			
	}

	
	public static List<String> extractTags(String source, char tagStart, char tagEnd){
		ArrayList<String> ret = new ArrayList<String>();
		
		String currentTag = null;
		boolean inTag = false;
		char c;
		for (int i=0, l=source.length(); i<l; i++){
			c = source.charAt(i);
			if (!inTag){
				if (c==tagStart){
					currentTag = ""+c;
					inTag = true;
				}
			}else{
				currentTag += c;
				if (c==tagEnd){
					inTag = false;
					ret.add(currentTag);
					currentTag = null;
				}
			}
		}
		
		return ret;
	}
	
	public static List<String> extractTagsWithEscapeChar(String source, char tagStart, char tagEnd, char escapeChar){
		ArrayList<String> ret = new ArrayList<String>();
		
		String currentTag = null;
		boolean inTag = false;
		boolean skipNext = false;
		boolean skipNow = false;
		char c;
		for (int i=0, l=source.length(); i<l; i++){
			c = source.charAt(i);
			
			if (skipNext)
				skipNow = true;
			else
				skipNow = false;
			skipNext = false;
			
			if (c==escapeChar){
				skipNext = true;
				continue;
			}
			
			if (!inTag){
				if (c==tagStart && !skipNow){
					currentTag = ""+c;
					inTag = true;
				}
			}else{
				currentTag += c;
				if (c==tagEnd && !skipNow){
					inTag = false;
					ret.add(currentTag);
					currentTag = null;
				}
			}
		}
		
		return ret;
	}

	public static String strip(String src, int fromBeginn, int fromEnd){
		return src.substring(fromBeginn, src.length()-fromEnd);
	}

	//Added 6.02.08
	
	public static boolean isSurroundedWith(String src, char starting, char ending){
		return src.startsWith("" + starting) && src.endsWith("" + ending);
	}
	
	public static String removeSurround(String src){
		return StringUtils.strip(src, 1, 1);
	}
	
	public static String surroundWith(String src, char starting, char ending){
		src = src.trim();
		return starting + src + ending;
	}
	
	
	public static List<String> extractSuperTags(String source, char tagStart, char tagEnd, char escapeChar){
		ArrayList<String> ret = new ArrayList<String>();
		
		String currentTag = "";
		int inTag = 0;
		boolean skipNext = false;
		char c;
		for (int i=0, l=source.length(); i<l; i++){
			c = source.charAt(i);
			
			if (skipNext){
				skipNext = false;
				continue;
			}

			if (c==escapeChar){
				skipNext = true;
				continue;
			}
			
			if (c==tagStart)
				inTag++;
			
			if(inTag >= 1)
				currentTag += c;
			
			if (c==tagEnd){
				if(inTag == 1){
					ret.add(currentTag);
					currentTag = "";
					inTag = 0;
					continue;
				}
				if(inTag>0)
					inTag--;
			}
		}
		
		return ret;
	}
	

	public static String substringFromEnd(String src, int indexFromEnd){
		if (src.length() <= indexFromEnd)
			return "";
		int end = src.length() - indexFromEnd;
		return src.substring(0, end);
	}
	
	public static String concatenateTokens(List<String> tokens, char delimiter, char tokenStartingTag, char tokenEndingTag){
		String ret = "";
		boolean begin = true;
		for(String t:tokens){
			t = t.trim();
			if(t.length() == 0)
				continue;
			if(!begin)
				ret += delimiter;
			ret += surroundWith(t, tokenStartingTag, tokenEndingTag);
			begin = false;
		}
		return ret;
	}
	
	public static void main(String a[]){
		String testString = "Hi, hallo bla {xyz} und{abc}\n{21344}{erer}erere\\{bla{\\r}";
		List<String> tags = extractTagsWithEscapeChar(testString, '{', '}', '\\'); 
		System.out.println("Tags: "+tags);
		for (String t: tags){
			System.out.println(t+" -> "+strip(t,1,1));
		}
		test2();
		test3();
		test4();
	}
	
	public static void test3(){
		System.out.println("TESTING SURROUNDING:");
		String hello = "{Hello!}";
		System.out.println("Is surrounded "+hello+" with {}:"+ isSurroundedWith(hello, '{', '}'));
		System.out.println("Remove surround {}:" +(hello = removeSurround(hello)));
		System.out.println("Srround with []:" + surroundWith(hello, '[', ']'));
	}
	public static void test4(){
		System.out.println("TESTING concatenateTokens():");
	List<String> hellos = new ArrayList<String>(4);
	hellos.add("Hello, World!");
	hellos.add("Hello, People!");
	hellos.add("Hello, Aliens!");
	System.out.println("Concatenate Hellos Tokens with delimiter , and surround with <>:" + concatenateTokens(hellos, ',', '<', '>'));
	}
	
	public static void test2(){
		System.out.println("TESTING excractSuperTags()");
		String source = "ads{f{asd{2}fasd}a{qqq}sd}f";
		char tagStart = '{';
		char tagEnd = '}';
		char escapeChar = '|';
		List<String> tags = extractSuperTags(source, tagStart, tagEnd, escapeChar);
		for(String t: tags)
			System.out.println(t);
	}

}

