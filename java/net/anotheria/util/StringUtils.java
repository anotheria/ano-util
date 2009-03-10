package net.anotheria.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * StringUtils -
 * some small but usefull utils for String handling.
 * @version 1.3
 * @author Leon Rosenberg
 */

public final class StringUtils{

	/**
  	 *Return a Vector with tokens from the source string tokenized using the delimiter char.
     */
    public static final Vector<String> tokenize2vector(String source, char delimiter){
		Vector<String> v;
		v = new Vector<String>();
  		StringBuilder currentS = new StringBuilder();
		char c;
		for ( int i=0;i<source.length() ;i++ ){
			c = source.charAt(i);
			if ( c==delimiter ){
				if ( currentS.length()>0 ){
					v.addElement(currentS.toString());
				}else{
				    v.addElement(new String(""));
				}
				currentS=new StringBuilder();
			}else{
				currentS.append(c);
			}
		}
		if ( currentS!=null && currentS.length()>0 )
			v.addElement(currentS.toString());
   		return v;
    }
    
	public static final List<String> tokenize2list(String source, char delimiter, char escapeChar){
		List<String> ret = new ArrayList<String>();
  		StringBuilder currentS = new StringBuilder();
		char c;
		boolean skipNext = false;
		for ( int i=0;i<source.length() ;i++ ){
			c = source.charAt(i);
			if(skipNext){
				if(c != delimiter)
					currentS.append(escapeChar);
				currentS.append(c);
				skipNext = false;
				continue;
			}
			if (c==escapeChar){
				skipNext = true;
				continue;
			}
			if (c==delimiter ){
				if ( currentS.length()>0 ){
					ret.add(currentS.toString());
				}else{
				    ret.add(new String(""));
				}
				currentS=new StringBuilder();
			}else{
				currentS.append(c);
			}
			skipNext = false;
		}
		if ( currentS!=null && currentS.length()>0 )
			ret.add(currentS.toString());
   		return ret;
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
		Vector<String> v = tokenize2vector(source, delimiter);
		ret = new String[v.size()];
		for ( int i=0;i<v.size() ;i++ ){
			ret[i] = (String) v.elementAt(i);
		}
		return ret;
	}/*end fun tokenize()*/
	
	public static final String[] tokenize(String source, char delimiter, char escapeChar){
		return tokenize2list(source, delimiter, escapeChar).toArray(new String[0]);
	}
	
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
	public static Map<String,String> buildHashTable(String source){
		return Collections.synchronizedMap(buildParameterMap(source, '\n', ':'));
	}

	public static Map<String,String> buildParameterMap(String source, char lineDelimiter, char parameterDelimiter){
	    source = removeChar(source,'\r');
		String[] lines = tokenize(source, lineDelimiter);
		Map<String, String> ret = new HashMap<String, String>(lines.length);
		for ( int i=0;i<lines.length ;i++ ){
			StringPair sp = splitString(lines[i], parameterDelimiter);
			if ( sp.first!=null && sp.second!=null )
				ret.put(sp.first.trim(), sp.second.trim());
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
	public static String replace(String source, Map<String,String> map){
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
		    return new StringPair();
		return new StringPair(source.substring(0,del_pos),source.substring(del_pos+1,source.length()));

	}


	private static class StringPair{
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
 	public static <T> Vector<T> reverse(Vector<T> v){
  		Vector<T> ret = new Vector<T>(v.size());
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
		return (src.charAt(0)== starting )&& (src.charAt(src.length()-1) == ending);
	}
	
	public static String removeSurround(String src){
		return StringUtils.strip(src, 1, 1);
	}
	
	public static String surroundWith(String src, char starting, char ending){
		StringBuilder ret = new StringBuilder();
		ret.append(starting);
		ret.append(src);
		ret.append(ending);
		return ret.toString();
	}
	
	
	public static List<String> extractSuperTags(String source, char tagStart, char tagEnd, char escapeChar){
		
		List<String> index = indexSuperTags(source, tagStart, tagEnd);
		List<String> ret = new ArrayList<String>();
		for(String tag: index)
			if(tag.charAt(0) == tagStart)
				ret.add(tag);
		return ret;
	}
	
	public static List<String> indexSuperTags(String source, char tagStart, char tagEnd){
		List<String> ret = new ArrayList<String>();
		StringBuilder currentTag = new StringBuilder();
		
		int inTag = 0;
		char c;
		
		for (int i=0, l=source.length(); i<l; i++){
			c = source.charAt(i);
						
			if (c==tagStart){
				inTag++;
				if(inTag == 1){
					if(currentTag.length() > 0)
						ret.add(currentTag.toString());
					currentTag = new StringBuilder();
				}
			}
							
			currentTag.append(c);
			
			if (c==tagEnd){
				if(inTag == 1){
					ret.add(currentTag.toString());
					currentTag = new StringBuilder();
					inTag = 0;
					continue;
				}
				if(inTag>0)
					inTag--;
			}
		}
		if(currentTag.length() > 0)
			ret.add(currentTag.toString());
		return ret;
	}

	  public static final List<String> tokenize(String source, char escapeStart, char escapeEnd, char... delimiters) {
		  Set<Character> delimitersHash = new HashSet<Character>(delimiters.length);
		  for(char del: delimiters)
			  delimitersHash.add(del);
		  
		  List<String> ret = new ArrayList<String>();
		StringBuilder currentTag = new StringBuilder();
		
		int inEscape = 0;
		char c;

		for (int i = 0, l = source.length(); i < l; i++) {
			c = source.charAt(i);

			if (c == escapeStart)
				inEscape++;

			if(inEscape < 1 && delimitersHash.contains(c)) {
				//New token is starting...
				if (currentTag.length() > 0) {
					ret.add(currentTag.toString());
					currentTag = new StringBuilder();
					continue;
				}
			}

			currentTag.append(c);

			if (c == escapeEnd) {
				if (inEscape > 0)
					inEscape--;
			}
		}
		if (currentTag.length() > 0)
			ret.add(currentTag.toString());
		return ret;
	}
	
	 /*
	  Do the same as tokenize(String source, char escapeStart, char escapeEnd, char... delimiters) with small bug fix for the case when escapeStart equals to escapeEnd.
	  Original tokenize method is used in VariableProcessor so any changes in it must be well tested before go to life. That's why this duplication method was created.
	 */
	public static final List<String> _tokenize(String source, char escapeStart, char escapeEnd, char... delimiters) {
		boolean quotingMode = escapeStart == escapeEnd;  
		Set<Character> delimitersHash = new HashSet<Character>(delimiters.length);
		  for(char del: delimiters)
			  delimitersHash.add(del);
		  
		  List<String> ret = new ArrayList<String>();
		StringBuilder currentTag = new StringBuilder();
		
		int inEscape = 0;
		char c;

		for (int i = 0, l = source.length(); i < l; i++) {
			c = source.charAt(i);

			if (c == escapeStart)
				inEscape++;

			if(inEscape < 1 && delimitersHash.contains(c)) {
				//New token is starting...
				if (currentTag.length() > 0) {
					ret.add(currentTag.toString());
					currentTag = new StringBuilder();
					continue;
				}
			}

			currentTag.append(c);

			if (c == escapeEnd) {
				if(quotingMode && inEscape == 2)
					inEscape = 0;
				if(!quotingMode && inEscape > 0)
					inEscape--;
			}
		}
		if (currentTag.length() > 0)
			ret.add(currentTag.toString());
		return ret;
	}  
	  
	public static String substringFromEnd(String src, int indexFromEnd){
		if (src.length() <= indexFromEnd)
			return "";
		int end = src.length() - indexFromEnd;
		return src.substring(0, end);
	}
	
	public static<T> String concatenateTokens(Collection<T> tokens, char delimiter, char tokenStartingTag, char tokenEndingTag){
		StringBuilder ret = new StringBuilder();
		boolean begin = true;
		for(T _t:tokens){
			String t = _t instanceof String ? (String)_t : ""+_t; 
			t = t.trim();
			if(t.length() == 0)
				continue;
			if(!begin)
				ret.append(delimiter);
			ret.append(surroundWith(t, tokenStartingTag, tokenEndingTag));
			begin = false;
		}
		return ret.toString();
	}
	
	public static<T> String concatenateTokens(Collection<T> tokens, String delimiterSequence){
		StringBuilder ret = new StringBuilder();
		boolean begin = true;
		for(T _t:tokens){
			String t = _t instanceof String ? (String)_t : ""+_t; 
			t = t.trim();
			if(t.length() == 0)
				continue;
			if(!begin)
				ret.append(delimiterSequence);
			ret.append(t);
			begin = false;
		}
		return ret.toString();
	}	
	
	public static String concatenateTokens(String delimiterSequence, Object... tokens){
		return concatenateTokens(Arrays.asList(tokens), delimiterSequence);
	}
	
	public static String replaceUmlauts(String src){
		  StringBuilder ret = new StringBuilder();
		  for(int i=0; i<src.length(); i++){
		     char c = src.charAt(i);
		     switch(c){
		       case '\u00e4':
		        ret.append("ae");
		         break;
		       case '\u00f6':
		         ret.append("oe");
		         break;
		       case '\u00fc':
		         ret.append("ue");
		         break;
		       case '\u00c4':
		         ret.append("Ae");
		         break;
		       case '\u00d6':
		         ret.append("Oe");
		       break;
		       case '\u00dC':
		         ret.append("Ue");
		       break;
		       case '\u00df':
		         ret.append("ss");
		       break;
		       default:
		         ret.append(c);
		     }
		}
		return ret.toString();
	}
	
	public static String normalize(String s){
		if (s==null || s.length()==0)
			return s;
		StringBuilder ret = new StringBuilder(s.length());
		for (int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (Character.isLetter(c))
				ret.append(c);
			else
				ret.append('_');
		}
		return ret.toString();
	}

	private StringUtils(){
		//prevent from instantiation
	}
	

}

