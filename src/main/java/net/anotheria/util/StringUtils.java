package net.anotheria.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * StringUtils -
 * some small but usefull utils for String handling.
 *
 * @author Leon Rosenberg
 * @version 1.3
 */
public final class StringUtils {

    /**
     * Return a Vector with tokens from the source string tokenized using the delimiter char.
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> tokenize2vector(String source, char delimiter) {
        List<String> v = new ArrayList<>();
        StringBuilder currentS = new StringBuilder(source.length());
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == delimiter) {
                if (currentS.length() > 0) {
                    v.add(currentS.toString());
                } else {
                    v.add("");
                }
                currentS = new StringBuilder();
            } else {
                currentS.append(c);
            }
        }
        if (currentS != null && currentS.length() > 0)
            v.add(currentS.toString());
        return v;
    }

    /**
     * <p>tokenize2list.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @param escapeChar a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> tokenize2list(String source, char delimiter, char escapeChar) {
        List<String> ret = new ArrayList<>();
        StringBuilder currentS = new StringBuilder(source.length());
        boolean skipNext = false;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (skipNext) {
                if (c != delimiter)
                    currentS.append(escapeChar);
                currentS.append(c);
                skipNext = false;
                continue;
            }
            if (c == escapeChar) {
                skipNext = true;
                continue;
            }
            if (c == delimiter) {
                if (currentS.length() > 0) {
                    ret.add(currentS.toString());
                } else {
                    ret.add("");
                }
                currentS = new StringBuilder();
            } else {
                currentS.append(c);
            }
            skipNext = false;
        }
        if (currentS.length() > 0)
            ret.add(currentS.toString());
        return ret;
    }

    /**
     * <p>tokenize2list.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> tokenize2list(String source, char delimiter) {
        return Arrays.asList(tokenize(source, delimiter));
    }

    /**
     * Return a Map with entries from the source string tokenized on entries
     * using the entriesDelimiter and on key-value pair with keyValueDelimiter.
     *
     * @param source a {@link java.lang.String} object.
     * @param entriesDelimiter a char.
     * @param keyValueDelimiter a char.
     * @return a {@link java.util.Map} object.
     */
    public static final Map<String, String> tokenize2map(String source, char entriesDelimiter, char keyValueDelimiter) {
        String[] entryStrArray = tokenize(source, true, entriesDelimiter);
        Map<String, String> ret = new HashMap<>(entryStrArray.length);
        for (String entryStr : entryStrArray) {
            entryStr = entryStr.trim();
            String[] entry = tokenize(entryStr, true, '=');
            if (entry.length != 2)
                throw new IllegalArgumentException("Source [" + source + "] is not the valid string map with entries delimiter[" + entriesDelimiter + "] " +
                        "and key->value delimiter [" + keyValueDelimiter + "]!  Wrong entry: " + entryStr);
            ret.put(entry[0].trim(), entry[1].trim());
        }
        return ret;
    }

    /**
     * Returns an array of stringtokens from the source string.
     * The String "Leon Power Tools" with delimiter ' ' will return {"Leon","Power","Tools"}
     * Last change:  LR   15 Aug 98    4:23 pm
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @return an array of {@link java.lang.String} objects.
     */
    public static final String[] tokenize(String source, char delimiter) {
        List<String> v = tokenize2vector(source, delimiter);
        String[] ret = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            ret[i] = v.get(i);
        }
        return ret;
    }/*end fun tokenize()*/

    /**
     * <p>tokenize.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param ignoreEmptyTokens a boolean.
     * @param delimiter a char.
     * @return an array of {@link java.lang.String} objects.
     */
    public static final String[] tokenize(String source, boolean ignoreEmptyTokens, char delimiter) {
        List<String> v = new ArrayList<>();
        StringBuilder currentS = new StringBuilder();
        char[] charArray = source.toCharArray();
        for (char c : charArray) {
            if (c == delimiter) {
                if (currentS.length() > 0 || !ignoreEmptyTokens)
                    v.add(currentS.toString());
                currentS = new StringBuilder();
                continue;
            }
            currentS.append(c);
        }
        if (currentS.length() > 0 || !ignoreEmptyTokens)
            v.add(currentS.toString());
        return v.toArray(new String[1]);
    }


    /**
     * <p>tokenize.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @param escapeChar a char.
     * @return an array of {@link java.lang.String} objects.
     */
    public static String[] tokenize(String source, char delimiter, char escapeChar) {
        List<String> var = tokenize2list(source, delimiter, escapeChar);
        return var.toArray(new String[var.size()]);
    }

    /**
     * <p>removeCharAt.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param position a int.
     * @return a {@link java.lang.String} object.
     */
    public static String removeCharAt(String src, int position) {
        return new StringBuilder(src).deleteCharAt(position).toString();
    }

    /**
     * Returns a source String with all occurences of 'c' removed.
     * removeChar("Leon's Power Tools", ' ') will return "Leon'sPowerTools".
     *
     * @param src a {@link java.lang.String} object.
     * @param c a char.
     * @return a {@link java.lang.String} object.
     */
    public static String removeChar(String src, char c) {
        StringBuilder ret = new StringBuilder(src.length());
        for (int i = 0; i < src.length(); i++)
            if (src.charAt(i) != c)
                ret.append(src.charAt(i));
        return ret.toString();
    }

    /**
     * Removes all chars from the char array from the string.
     *
     * @param src a {@link java.lang.String} object.
     * @param c a char.
     * @return a {@link java.lang.String} object.
     */
    public static String removeChars(String src, char... c) {
        StringBuilder ret = new StringBuilder(src.length());
        for (int i = 0; i < src.length(); i++) {
            char aC = src.charAt(i);
            boolean append = true;
            for (char aC1 : c)
                if (aC == aC1) {
                    append = false;
                    break;
                }
            if (append)
                ret.append(aC);
        }
        return ret.toString();
    }

	/**
	 * Removes line feeds (\r, \n) from string.
	 *
	 * @param src string to remove lines from.
	 * @return a {@link java.lang.String} object.
	 */
	public static String removeLines(String src){
		return replace(removeChar(src, '\r'), '\n', ' ');
		
	}

    /**
     * Returns first line of multi-lined String.
     *
     * @param source a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getFirstLine(String source) {
        StringBuilder line = new StringBuilder(source.length());
        int i = 0;
        while (i < source.length()) {
            line.append(source.charAt(i));
            if (source.charAt(i++) == '\n')
                break;
        }
        return line.toString();
    }

    /**
     * Builds a hash table from the String like *(TYPE : MESSAGE + CR)
     * useful for http requests etc.
     *
     * @param source a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     */
    public static Map<String, String> buildHashTable(String source) {
        return Collections.synchronizedMap(buildParameterMap(source, '\n', ':'));
    }

    /**
     * <p>buildParameterMap.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     */
    public static Map<String, String> buildParameterMap(String source) {
        return buildParameterMap(source, '\n', '=');
    }

    /**
     * <p>buildParameterMap.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param lineDelimiter a char.
     * @param parameterDelimiter a char.
     * @return a {@link java.util.Map} object.
     */
    public static Map<String, String> buildParameterMap(String source, char lineDelimiter, char parameterDelimiter) {
        source = removeChar(source, '\r');
        String[] lines = tokenize(source, lineDelimiter);
        Map<String, String> ret = new HashMap<>(lines.length);
        for (String line : lines) {
            StringPair sp = splitString(line, parameterDelimiter);
            if (sp.first != null && sp.second != null)
                ret.put(sp.first.trim(), sp.second.trim());
        }
        return ret;
    }

    /**
     * Returns the number of occurencies of the specified cha rin the given string.
     *
     * @param source a {@link java.lang.String} object.
     * @param toCount a char.
     * @return a int.
     */
    public static int charCount(String source, char toCount) {
        int sum = 0;
        for (int i = 0; i < source.length(); i++)
            if (source.charAt(i) == toCount)
                sum++;
        return sum;
    }


    /**
     * Inserts the string at the specified position in the source string.
     *
     * @param source    source string
     * @param insertion string to be inserted at the specified position
     * @param position  position in the source string to insert
     * @return the resulting string
     * @throws java.lang.IndexOutOfBoundsException if the
     *                                   {@code position} is negative, or
     *                                   {@code position} is larger than the length of
     *                                   source string
     */
    public static String insert(String source, String insertion, int position) {
        return new StringBuilder(source).insert(position, insertion).toString();
    }

    /**
     * Returns a new String which equals source String
     *
     * @param source a {@link java.lang.String} object.
     * @param replaceChar a char.
     * @param replaceWith a char.
     * @return a {@link java.lang.String} object.
     */
    public static String _replace2(String source, char replaceChar, char replaceWith) {
        String dest = "";
        int i = 0;
        while (i < source.length())
            dest += source.charAt(i++) == replaceChar ? replaceWith : source.charAt(i - 1);

        return dest;
    }

    /**
     * <p>replace.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param replaceChar a char.
     * @param replaceWith a char.
     * @return a {@link java.lang.String} object.
     */
    public static String replace(String source, char replaceChar, char replaceWith) {
        StringBuilder dest = new StringBuilder(source.length());
        int i = 0;
        while (i < source.length())
            dest.append(source.charAt(i++) == replaceChar ? replaceWith : source.charAt(i - 1));

        return dest.toString();
    }

    /**
     * <p>replace.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param replaceChar a char.
     * @param replaceWith a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String replace(String source, char replaceChar, String replaceWith) {
        String dest = "";
        int i = 0;
        while (i < source.length())
            dest += source.charAt(i++) == replaceChar ? replaceWith : String.valueOf(source.charAt(i - 1));

        return dest;
    }


    /**
     * - kle 14.06.2001
     * Replace  a set of chars with a set of strings
     *
     * @param source the source string
     * @param map    replace map
     * @return a source string where all chars that are members of map keys are replaced with the interlocking values
     */
    public static String replace(String source, Map<String, String> map) {
        String dest = "";
        int i = 0;
        while (i < source.length()) {
            String replaceWith = map.get(String.valueOf(source.charAt(i++)));
            if (replaceWith != null)
                dest += replaceWith;
            else
                dest += String.valueOf(source.charAt(i - 1));
        }
        return dest;
    }

    /**
     * <p>splitString.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param delimiter a char.
     * @return a {@link net.anotheria.util.StringUtils.StringPair} object.
     */
    public static StringPair splitString(String source, char delimiter) {
        int pDelPos = source.indexOf((int) delimiter);
        if (pDelPos == -1)
            return new StringPair();
        return new StringPair(source.substring(0, pDelPos), source.substring(pDelPos + 1, source.length()));

    }


    private static class StringPair {
        private String first, second;

        StringPair(String aFirst, String aSecond) {
            this.first = aFirst;
            this.second = aSecond;
        }

        StringPair() {
            this(null, null);
        }

        public String getFirst() {
            return first;
        }

        public String getSecond() {
            return second;
        }


    }

    /**
     * Reverses the order in the Vector.
     *
     * @param v a {@link java.util.List} object.
	 * @param <T> pattern for list objects.
	 * @return a {@link java.util.List} object.
     */
    public static <T> List<T> reverse(List<T> v) {
        List<T> ret = new ArrayList<>(v.size());
        for (int i = v.size() - 1; i >= 0; i--)
            ret.add(v.get(i));
        return ret;
    }

    /**
     * <p>concat.</p>
     *
     * @param a a {@link java.lang.String} object.
     * @param b a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String concat(String a, String b) {
        return concat(a, b, " ");
    }

    /**
     * <p>concat.</p>
     *
     * @param a a {@link java.lang.String} object.
     * @param b a {@link java.lang.String} object.
     * @param delimiter a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String concat(String a, String b, String delimiter) {
        return (a == null || a.isEmpty()) ? b : (b == null || b.isEmpty()) ? a : a + delimiter + b;
    }

    /**
     * <p>combineStrings.</p>
     *
     * @param strings an array of {@link java.lang.String} objects.
     * @param delimiter a char.
     * @return a {@link java.lang.String} object.
     */
    public static String combineStrings(String[] strings, char delimiter) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            ret.append(strings[i]);
            if (i < strings.length - 1)
                ret.append(delimiter);
        }
        return ret.toString();
    }


    /**
     * <p>capitalize.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String capitalize(String s) {
        char c = s.charAt(0);
        return Character.toUpperCase(c) + s.substring(1);
    }

    /**
     * <p>getStringAfter.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @param start a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringAfter(String src, String toSearch, int start) {
        int ind = src.indexOf(toSearch, start);
//		lastIndex = ind;
        if (ind == -1)
            return "";
        return src.substring(ind + toSearch.length());
    }

    /**
     * <p>getStringAfter.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringAfter(String src, String toSearch) {
        return getStringAfter(src, toSearch, 0);
    }

    /**
     * <p>getStringAfterIncl.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @param start a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringAfterIncl(String src, String toSearch, int start) {
        int ind = src.indexOf(toSearch, start);
//		lastIndex = ind;
        if (ind == -1)
            return "";
        return src.substring(ind);
    }

    /**
     * <p>getStringAfterIncl.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringAfterIncl(String src, String toSearch) {
        return getStringAfterIncl(src, toSearch, 0);
    }

    /**
     * <p>getStringWith.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @param start a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringWith(String src, String toSearch, int start) {
        //System.out.println("search:"+toSearch+"in:"+src);
        int ind = src.indexOf(toSearch, start);
        //lastIndex = ind;
        if (ind == -1)
            return "";
        return src.substring(ind);
    }

    /**
     * <p>getStringWith.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringWith(String src, String toSearch) {
        return getStringWith(src, toSearch, 0);
    }

    /**
     * <p>getStringBefore.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @param start a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringBefore(String src, String toSearch, int start) {
        //System.out.println("search:"+toSearch+"in:"+src);
        int ind = src.indexOf(toSearch, start);
        //lastIndex = ind;
        if (ind == -1)
            return "";
        return src.substring(start, ind);
    }

    /**
     * <p>getStringBefore.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringBefore(String src, String toSearch) {
        return getStringBefore(src, toSearch, 0);
    }

    /**
     * <p>getStringBeforeIncl.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @param start a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringBeforeIncl(String src, String toSearch, int start) {
        //System.out.println("search:"+toSearch+"in:"+src);
        int ind = src.indexOf(toSearch, start);
        //lastIndex = ind;
        if (ind == -1)
            return "";
        return src.substring(start, ind + toSearch.length());
    }

    /**
     * <p>getStringBeforeIncl.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toSearch a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getStringBeforeIncl(String src, String toSearch) {
        return getStringBeforeIncl(src, toSearch, 0);
    }

    /**
     * <p>removeImgTag.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeImgTag(String s) {
        return removeTag(s, "img");
    }

    /**
     * <p>removeAnchorTag.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeAnchorTag(String s) {
        return removeTag(s, "a");
    }

    /**
     * <p>removeAnchorEndTag.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeAnchorEndTag(String s) {
        return removeTag(s, "/a");
    }

    /**
     * <p>removeTag.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @param tag a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeTag(String s, String tag) {
        tag = '<' + tag;
        int index;
        while ((index = s.indexOf(tag)) != -1) {
            String r = getStringBefore(s, tag);
            r += getStringAfter(s, ">", index);
            s = r;
        }
        return s;
    }


    /**
     * <p>removeString.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @param toRemove a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeString(String s, String toRemove) {
        int index;
        while ((index = s.indexOf(toRemove)) != -1) {
            String r = getStringBefore(s, toRemove);
            r += getStringAfter(s, toRemove, index);
            s = r;
        }
        return s;
    }


    /**
     * Removes from the source string the characters sequence pointed by {@code beginIndex} and
     * length.
     *
     * @param source     source string
     * @param beginIndex the beginning index, inclusive
     * @param length     the length of characters sequence to remove
     * @return the resulting string
     * @throws java.lang.IndexOutOfBoundsException if the
     *                                   {@code beginIndex} or <code>length</code> are negative, or
     *                                   {@code beginIndex} or <code>beginIndex</code> + <code>length</code>
     *                                   are larger than the length of source string
     */
    public static String remove(String source, int beginIndex, int length) {
        return new StringBuilder(source).delete(beginIndex, beginIndex + length).toString();
    }

    /**
     * <p>replaceOnce.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toReplace a {@link java.lang.String} object.
     * @param with a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String replaceOnce(String src, String toReplace, String with) {
        int index = src.indexOf(toReplace);
        if (index == -1)
            return src;

        String s = src.substring(0, index);
        s += with;
        s += src.substring(index + toReplace.length(), src.length());
        src = s;
        return src;
    }

    /**
     * Replaces all occurences of the toReplace string in the source string with the 'with' string.
     *
     * @param src       source text.
     * @param toReplace string to replace.
     * @param with      string to replace with.
     * @return a {@link java.lang.String} object.
     */
    public static String replace(String src, String toReplace, String with) {
        int index;
        while ((index = src.indexOf(toReplace)) > -1) {
            String s = src.substring(0, index);
            s += with;
            s += src.substring(index + toReplace.length(), src.length());
            src = s;
        }
        return src;
    }

    /**
     * <p>replace.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param toReplace a {@link java.lang.String} object.
     * @param with a char.
     * @return a {@link java.lang.String} object.
     */
    public static String replace(String src, String toReplace, char with) {
        int index;
        while ((index = src.indexOf(toReplace)) > -1) {
            String s = src.substring(0, index) + with +
                    src.substring(index + toReplace.length(), src.length());
            src = s;
        }
        return src;
    }


    /**
     * <p>removeCComments.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeCComments(String src) {
        StringBuilder ret = new StringBuilder();

        boolean inComments = false;

        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (inComments) {
                if (c == '*' && src.charAt(i + 1) == '/') {
                    inComments = false;
                    i++;
                }
            } else {
                if (c == '/') {
                    if (src.charAt(i + 1) == '*') {
                        inComments = true;
                        i++;
                    } else {
                        ret.append('/');
                    }
                } else
                    ret.append(c);
            }
        }

        return ret.toString();
    }

    /**
     * <p>removeCPPComments.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeCPPComments(String src) {
        StringBuilder ret = new StringBuilder();

        boolean inComments = false;
        boolean inQuotes = false;

        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (inComments) {
                if (c == '\n') {
                    inComments = false;
                    inQuotes = false;
                }
            } else {
                if (c == '"')
                    inQuotes = !inQuotes;
                if (!inQuotes && c == '/') {
                    if (src.charAt(i + 1) == '/') {
                        inComments = true;
                        i++;
                    } else {
                        ret.append('/');
                    }
                } else
                    ret.append(c);
            }
        }

        return ret.toString();
    }

    /**
     * <p>removeBashComments.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeBashComments(String src) {
        StringBuilder ret = new StringBuilder();

        boolean inComments = false;
        boolean inQuotes = false;

        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (inComments) {
                if (c == '\n') {
                    inComments = false;
                    inQuotes = false;
                }
            } else {
                if (c == '"')
                    inQuotes = !inQuotes;
                if (!inQuotes && c == '#') {
                    inComments = true;
                } else {
                    ret.append(c);
                }
            }
        }

        return ret.toString();
    }

    /**
     * <p>reverseString.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String reverseString(String src) {
        String ret = "";
        for (int i = src.length() - 1; i >= 0; i--)
            ret += src.charAt(i);
        return ret;
    }

    /**
     * <p>makeDelimitedString.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param delimiter a {@link java.lang.String} object.
     * @param interval a int.
     * @return a {@link java.lang.String} object.
     */
    public static String makeDelimitedString(String src, String delimiter, int interval) {
        String ret = "";
        int t = 0;
        for (int i = src.length() - 1; i >= 0; i--) {
            ret += src.charAt(i);
            t++;
            if (t % interval == 0 && i != 0)
                ret += delimiter;
        }
        return reverseString(ret);
    }


    /**
     * Prefills the given string with the string as long as the size of the resulting string is less then desiredLength;
     * Example: prefill("1", 4, "0") -&gt; 0001.
     *
     * @param s a {@link java.lang.String} object.
     * @param desiredLength a int.
     * @param fillString a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String prefill(String s, int desiredLength, String fillString) {
        while (s.length() < desiredLength)
            s = fillString + s;
        return s;
    }

    /**
     * Postfills (appends) the given string with the string as long as the size of the resulting string is less then desiredLength;
     * Example: prefill("1", 4, "0") -&gt; 0001.
     *
     * @param s a {@link java.lang.String} object.
     * @param desiredLength a int.
     * @param fillString a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String postfill(String s, int desiredLength, String fillString) {
        while (s.length() < desiredLength)
            s = s + fillString;
        return s;
    }


    /**
     * <p>extractTags.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param tagStart a char.
     * @param tagEnd a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> extractTags(String source, char tagStart, char tagEnd) {
        List<String> ret = new ArrayList<>();

        String currentTag = null;
        boolean inTag = false;
        for (int i = 0, l = source.length(); i < l; i++) {
            char c = source.charAt(i);
            if (!inTag) {
                if (c == tagStart) {
                    currentTag = String.valueOf(c);
                    inTag = true;
                }
            } else {
                currentTag += c;
                if (c == tagEnd) {
                    inTag = false;
                    ret.add(currentTag);
                    currentTag = null;
                }
            }
        }

        return ret;
    }

    /**
     * <p>extractTagsWithEscapeChar.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param tagStart a char.
     * @param tagEnd a char.
     * @param escapeChar a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> extractTagsWithEscapeChar(String source, char tagStart, char tagEnd, char escapeChar) {
        List<String> ret = new ArrayList<>();

        String currentTag = null;
        boolean inTag = false;
        boolean skipNext = false;
        boolean skipNow;
        for (int i = 0, l = source.length(); i < l; i++) {
            char c = source.charAt(i);

            skipNow = skipNext;
            skipNext = false;

            if (c == escapeChar) {
                skipNext = true;
                continue;
            }

            if (!inTag) {
                if (c == tagStart && !skipNow) {
                    currentTag = String.valueOf(c);
                    inTag = true;
                }
            } else {
                currentTag += c;
                if (c == tagEnd && !skipNow) {
                    inTag = false;
                    ret.add(currentTag);
                    currentTag = null;
                }
            }
        }

        return ret;
    }

    /**
     * <p>strip.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param fromBeginn a int.
     * @param fromEnd a int.
     * @return a {@link java.lang.String} object.
     */
    public static String strip(String src, int fromBeginn, int fromEnd) {
        return src.substring(fromBeginn, src.length() - fromEnd);
    }

    //Added 6.02.08

    /**
     * <p>isSurroundedWith.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param starting a char.
     * @param ending a char.
     * @return a boolean.
     */
    public static boolean isSurroundedWith(String src, char starting, char ending) {
        return !src.isEmpty() && (src.charAt(0) == starting) && (src.charAt(src.length() - 1) == ending);
    }

    /**
     * <p>removeSurround.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String removeSurround(String src) {
        return StringUtils.strip(src, 1, 1);
    }

    /**
     * <p>surroundWith.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param starting a char.
     * @param ending a char.
     * @return a {@link java.lang.String} object.
     */
    public static String surroundWith(String src, char starting, char ending) {
        String ret = starting +
                src +
                ending;
        return ret;
    }

    /**
     * <p>extractSuperTags.</p>
     *
     * @deprecated escapeChar is ignored, use {@link #extractSuperTags(String, char, char)} instead
     * @param source a {@link java.lang.String} object.
     * @param tagStart a char.
     * @param tagEnd a char.
     * @param escapeChar a char.
     * @return a {@link java.util.List} object.
     */
    @Deprecated
    public static List<String> extractSuperTags(String source, char tagStart, char tagEnd, char escapeChar) {
        return extractSuperTags(source, tagStart, tagEnd);
    }

    /**
     * <p>extractSuperTags.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param tagStart a char.
     * @param tagEnd a char.
     * @return a {@link java.util.List} object.
     * @since 2.1.6
     */
    public static List<String> extractSuperTags(String source, char tagStart, char tagEnd) {
        List<String> index = indexSuperTags(source, tagStart, tagEnd);
        List<String> ret = new ArrayList<>();
        for (String tag : index)
            if (tag.charAt(0) == tagStart)
                ret.add(tag);
        return ret;
    }

    /**
     * <p>indexSuperTags.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param tagStart a char.
     * @param tagEnd a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> indexSuperTags(String source, char tagStart, char tagEnd) {
        List<String> ret = new ArrayList<>();
        StringBuilder currentTag = new StringBuilder();

        int inTag = 0;

        for (int i = 0, l = source.length(); i < l; i++) {
            char c = source.charAt(i);

            if (c == tagStart) {
                inTag++;
                if (inTag == 1) {
                    if (currentTag.length() > 0)
                        ret.add(currentTag.toString());
                    currentTag = new StringBuilder();
                }
            }

            currentTag.append(c);

            if (c == tagEnd) {
                if (inTag == 1) {
                    ret.add(currentTag.toString());
                    currentTag = new StringBuilder();
                    inTag = 0;
                    continue;
                }
                if (inTag > 0)
                    inTag--;
            }
        }
        if (currentTag.length() > 0)
            ret.add(currentTag.toString());
        return ret;
    }

    /**
     * <p>tokenize.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param escapeStart a char.
     * @param escapeEnd a char.
     * @param delimiters a char.
     * @return a {@link java.util.List} object.
     */
    public static final List<String> tokenize(String source, char escapeStart, char escapeEnd, char... delimiters) {
        Collection<Character> delimitersHash = new HashSet<>(delimiters.length);
        for (char del : delimiters)
            delimitersHash.add(del);

        List<String> ret = new ArrayList<>();
        StringBuilder currentTag = new StringBuilder();

        int inEscape = 0;

        for (int i = 0, l = source.length(); i < l; i++) {
            char c = source.charAt(i);

            if (c == escapeStart)
                inEscape++;

            if (inEscape < 1 && delimitersHash.contains(c)) {
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

    /**
     * <p>_tokenize.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param escapeStart a char.
     * @param escapeEnd a char.
     * @param delimiters a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> _tokenize(String source, char escapeStart, char escapeEnd, char... delimiters) {
        return _tokenize(source, escapeStart, escapeEnd, true, delimiters);
    }

    /**
     * <p>_tokenize.</p>
     *
     * @param source a {@link java.lang.String} object.
     * @param escapeStart a char.
     * @param escapeEnd a char.
     * @param skipEmptyTokens a boolean.
     * @param delimiters a char.
     * @return a {@link java.util.List} object.
     */
    public static List<String> _tokenize(String source, char escapeStart, char escapeEnd, boolean skipEmptyTokens, char... delimiters) {
        Collection<Character> delimitersHash = new HashSet<>(delimiters.length);

        for (char del : delimiters)
            delimitersHash.add(del);
        List<String> ret = new ArrayList<>();
        StringBuilder currentTag = new StringBuilder();

        int inEscape = 0;

        boolean quotingMode = escapeStart == escapeEnd;
        for (int i = 0, l = source.length(); i < l; i++) {
            char c = source.charAt(i);

            if (c == escapeStart)
                inEscape++;

            if (inEscape < 1 && delimitersHash.contains(c)) {
//				New token is starting...
                if (!skipEmptyTokens || currentTag.length() > 0)
                    ret.add(currentTag.toString());
                currentTag.setLength(0);
//				currentTag = new StringBuilder();
                continue;
            }

            currentTag.append(c);

            if (c == escapeEnd) {
                if (quotingMode && inEscape == 2)
                    inEscape = 0;
                if (!quotingMode && inEscape > 0)
                    inEscape--;
            }
        }
        if (currentTag.length() > 0)
            ret.add(currentTag.toString());
        return ret;
    }

    /**
     * <p>substringFromEnd.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @param indexFromEnd a int.
     * @return a {@link java.lang.String} object.
     */
    public static String substringFromEnd(String src, int indexFromEnd) {
        if (src.length() <= indexFromEnd)
            return "";
        int end = src.length() - indexFromEnd;
        return src.substring(0, end);
    }

    /**
     * <p>concatenateTokens.</p>
     *
     * @param tokens a {@link java.lang.Iterable} object.
     * @param delimiter a char.
     * @param tokenStartingTag a char.
     * @param tokenEndingTag a char.
     * @param <T> a T object.
     * @return a {@link java.lang.String} object.
     */
    public static <T> String concatenateTokens(Iterable<T> tokens, char delimiter, char tokenStartingTag, char tokenEndingTag) {
        StringBuilder ret = new StringBuilder();
        boolean begin = true;
        for (T pT : tokens) {
            String t = pT instanceof String ? (String) pT : String.valueOf(pT);
            t = t.trim();
            if (t.isEmpty())
                continue;
            if (!begin)
                ret.append(delimiter);
            ret.append(surroundWith(t, tokenStartingTag, tokenEndingTag));
            begin = false;
        }
        return ret.toString();
    }

    /**
     * <p>concatenateTokens.</p>
     *
     * @param tokens a {@link java.lang.Iterable} object.
     * @param delimiterSequence a {@link java.lang.String} object.
     * @param <T> a T object.
     * @return a {@link java.lang.String} object.
     */
    public static <T> String concatenateTokens(Iterable<T> tokens, String delimiterSequence) {
        StringBuilder ret = new StringBuilder();
        boolean begin = true;
        for (T pT : tokens) {
            String t = pT instanceof String ? (String) pT : String.valueOf(pT);
            t = t.trim();
            if (t.isEmpty())
                continue;
            if (!begin)
                ret.append(delimiterSequence);
            ret.append(t);
            begin = false;
        }
        return ret.toString();
    }

    /**
     * Concatenates tokens to String where tokens are separated from each other by delimiterSequence.
     * <p>
     * NOTE: vararg of parameterized types (here @param array) for primitive arrays doesn't work properly.
     * This utility have to be accomplished with concatenateTokens methods for each primitive arrays!!!
     *
     * @param delimiterSequence a {@link java.lang.String} object.
     * @param tokens a T object.
	 * @param <T> pattern for tokens.
     * @return a {@link java.lang.String} object.
     */
    public static <T> String concatenateTokens(String delimiterSequence, T... tokens) {
        return concatenateTokens(ArrayUtils.asList(tokens), delimiterSequence);
    }

    /**
     * <p>concatenateTokens.</p>
     *
     * @param delimiterSequence a {@link java.lang.String} object.
     * @param tokens an array of int.
     * @return a {@link java.lang.String} object.
     */
    public static String concatenateTokens(String delimiterSequence, int[] tokens) {
        return concatenateTokens(ArrayUtils.asList(tokens), delimiterSequence);
    }

    /**
     * <p>concatenateTokens.</p>
     *
     * @param delimiterSequence a {@link java.lang.String} object.
     * @param tokens an array of long.
     * @return a {@link java.lang.String} object.
     */
    public static String concatenateTokens(String delimiterSequence, long[] tokens) {
        return concatenateTokens(ArrayUtils.asList(tokens), delimiterSequence);
    }

    /**
     * <p>concatenateTokens.</p>
     *
     * @param delimiterSequence a {@link java.lang.String} object.
     * @param tokens an array of float.
     * @param <T> a T object.
     * @return a {@link java.lang.String} object.
     */
    public static <T> String concatenateTokens(String delimiterSequence, float[] tokens) {
        return concatenateTokens(ArrayUtils.asList(tokens), delimiterSequence);
    }

    /**
     * <p>replaceUmlauts.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String replaceUmlauts(String src) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            switch (c) {
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

    /**
     * Escapes all occurrences of escapedChars in a source string with where escaping char is '\'.
     * Note: Source string is not checked whether character from escapedChars is already escaped or not.
     *
     * @param src          source string to escape
     * @param escapedChars sequence of characters to escape
     * @return escaped string
     */
    public static String escape(String src, char... escapedChars) {
        char escape = '\\';

        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            for (char eC : escapedChars)
                if (eC == c) {
                    ret.append(escape);
                    break;
                }
            ret.append(c);
        }
        return ret.toString();
    }

    /**
     * <p>normalize.</p>
     *
     * @param s a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String normalize(String s) {
        if (s == null || s.trim().isEmpty())
            return s;

        s = s.trim();
        StringBuilder ret = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (/*(65 <= c  && c <= 90) || (97 <= c  && c <= 122)*/ Character.isLetter(c) || Character.isDigit(c))
                ret.append(c);
            else
                ret.append('_');
        }
        return ret.toString();
    }

    private StringUtils() {
        //prevent from instantiation
    }

    /**
     * <p>isEmpty.</p>
     *
     * @param src a {@link java.lang.String} object.
     * @return a boolean.
     */
    public static boolean isEmpty(String src) {
        return src == null || src.trim().isEmpty();
    }

    /**
     * Converts an Objects list to the list of Strings by calling toString method of each Object from incoming list.
     *
     * @param list incoming list of Objects
     * @return list where each incoming object converted to string.
     */
    public static List<String> toStringList(Collection<?> list) {
        List<String> ret = new ArrayList<>(list.size());
        for (Object el : list)
            ret.add(el.toString());
        return ret;
    }

    /**
     * Trims string to last index of delimiter which is situated before max size index and concatenates it with "...".
     * If maxSize &gt; value.length than value will be returned.
     *
     * @param value     string to trim
     * @param delimiter delimiter for trimming
     * @param maxSize   max size of trimmed string
     * @return trimmed and concatenated with "..." string
     */
    public static String trimString(String value, String delimiter, int maxSize) {
        String rez = value;
        if (rez.length() > maxSize) {
            int i = rez.lastIndexOf(delimiter, maxSize);
            if (i != -1) {
                rez = rez.substring(0, i).concat("...");
            }
        }
        return rez;
    }

}
