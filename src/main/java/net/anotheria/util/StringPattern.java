package net.anotheria.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>StringPattern class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class StringPattern {

	/** Constant <code>DEFAULT_WILDCARD='*'</code> */
	public static final char DEFAULT_WILDCARD = '*';
	
	private char wildCard;
	
	private String equals;
	private String startsWith;
	private String endsWith;
	private List<String> indexof;
	
	/**
	 * <p>Constructor for StringPattern.</p>
	 *
	 * @param pattern a {@link java.lang.String} object.
	 */
	public StringPattern(String pattern) {
		this(pattern, DEFAULT_WILDCARD);
	}
	
	/**
	 * <p>Constructor for StringPattern.</p>
	 *
	 * @param pattern a {@link java.lang.String} object.
	 * @param wildCard a char.
	 */
	public StringPattern(String pattern, char wildCard) {
		this.wildCard = wildCard;
		List<Integer> wildCards = searchWildcards(pattern);
		if(wildCards.isEmpty()) {
			equals = pattern;
			return;
		}
		if(wildCards.get(0) > 0) {
			startsWith = pattern.substring(0, wildCards.get(0));
		}
		if(wildCards.get(wildCards.size()-1) < pattern.length() -1) {
			endsWith = pattern.substring(wildCards.get(wildCards.size()-1)+1);
		}
		if(wildCards.size() == 1) {
			return;
		}
		indexof = new ArrayList<>();
		for(int i=0; i<wildCards.size()-1; i++) {
			int start = wildCards.get(i);
			int end = wildCards.get(i+1);
			indexof.add(pattern.substring(start+1, end));
		}
	}
	
	private List<Integer> searchWildcards(CharSequence pattern) {
		List<Integer> result = new ArrayList<>();
		for(int i=0; i<pattern.length(); i++) {
			if(pattern.charAt(i) == wildCard) {
				result.add(i);
			}
		}
		return result;
	}
	
	/**
	 * <p>match.</p>
	 *
	 * @param aString a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean match(String aString) {
		return match(aString, new ArrayList<String>());
	}
	
	/**
	 * <p>match.</p>
	 *
	 * @param aString a {@link java.lang.String} object.
	 * @param matches a {@link java.util.Collection} object.
	 * @return a boolean.
	 */
	public boolean match(String aString, Collection<String> matches) {
		
		if(equals != null) {
			return aString.equals(equals);
		}

		int leftMatch = 0;
		int rightMatch = aString.length();
		
		if(startsWith != null) {
			if(aString.startsWith(startsWith)) {
				leftMatch = startsWith.length();
			} else {
				return false;
			}
		}
		if(endsWith != null) {
			if(aString.endsWith(endsWith)) {
				rightMatch = aString.length() - endsWith.length();
			} else {
				return false;
			}
		}
		if(indexof != null) {
			int lastIndex = 0;
			for(String io: indexof) {
				int index = aString.indexOf(io, lastIndex);
				if(index == -1) {
					return false;
				}
				if(matches != null) {
					matches.add(aString.substring(leftMatch, index));
					leftMatch = index + io.length();
				}
				lastIndex = index;
			}
		}
		matches.add(aString.substring(leftMatch, rightMatch));
		return true;
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		if(equals != null) {
			result.append("equals=").append(equals);
		}
		if(startsWith != null) {
			result.append(", startsWith=").append(startsWith);
		}
		if(indexof != null) {
			for(String io: indexof) {
				result.append(", indexof=").append(io);
			}
		}
		if(endsWith != null) {
			result.append(", endsWith=").append(endsWith);
		}
		return result.toString();
	}
}
