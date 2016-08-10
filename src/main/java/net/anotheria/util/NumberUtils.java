package net.anotheria.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * A collection of useful utility methods for handling numbers, dates and currencies.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public final class NumberUtils {

	/**
	 * Constants for month names.
	 */
	public static final String[] MONTH = {
			"",
			"JAN",
			"FEB",
			"MAR",
			"APR",
			"MAI",
			"JUN",
			"JUL",
			"AUG",
			"SEP",
			"OCT",
			"NOV",
			"DEC"
	};

	/**
	 * Converts an integer number in a String with given number of chars;
	 * fills in zeros if needed from the left side.
	 * Example: itoa(23, 4) -&gt; 0023.
	 *
	 * @param i a int.
	 * @param limit a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String itoa(int i, int limit){
		String a = String.valueOf(i);
		while (a.length()<limit)
			a = '0' +a;
		return a;			
	}
	
	/**
	 * Calls itoa(i,2);
	 *
	 * @param i a int.
	 * @return a {@link java.lang.String} object.
	 */
	public static String itoa(int i){
		return itoa(i,2);
	}

	
	/**
	 * Often needed by different packages
	 * Return a time string in form of DD MMM YYYY (23 FEB 2002).
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeDateStringLong(long time){
		Date date = new Date(time);
		return itoa(date.day)+ ' ' +
				MONTH[date.month]+ ' ' +
				date.year;
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of DD MMM YY (23 FEB 02).
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeDateString(long time){
		Date date = new Date(time);
		int minus = date.year >2000 ? 2000 : 1900;
		return itoa(date.day)+ ' ' +
				MONTH[date.month]+ ' ' +
				itoa(date.year - minus);
	}
	
	/**
	 * Returns a digital time string (23.02.2002).
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeDigitalDateString(long time){
		Date date = new Date(time);
		return itoa(date.day)+ '.' +
				itoa(date.month)+ '.' +
				itoa(date.year);
	}

	/**
	 * <p>makeDigitalDateStringLong.</p>
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeDigitalDateStringLong(long time){
		Date date = new Date(time);
		return itoa(date.day)+ '.' +
				itoa(date.month)+ '.' +
				itoa(date.year,4);
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of hh:mm.
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeTimeString(long time){
		Date date = new Date(time);
		return itoa(date.hour)+ ':' + itoa(date.min);
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of hh:mm am/pm.
	 *
	 * @param time a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeTimeString12H(long time){
		Date date = new Date(time);
		int hour = date.hour;
		String t = "am";
		if (hour>12){
			hour-=12;
			t = "pm";
		}
		return itoa(hour)+ ':' + itoa(date.min)+ ' ' +t;
	}

	/**
	 * Returns the given size in bytes as short string (Kb, Mb, B)...
	 *
	 * @param size the size in bytes
	 * @return the corresponding string
	 */
	public static String makeSizeString(long size){
		String bytes = "B";
		if (size>1024){
			size/=1024;
			bytes = "kB";
		}
		
		if (size>1024){
			size/=1024;
			bytes = "Mb";
		}
		
		if (size>1024){
			size/=1024;
			bytes = "Gb";
		}

		return size + " " + bytes;
			
	}

	/**
	 * Returns a short date string in form of "YY.MM.DD" or "YYYY.MM.DD" depending on the year parameter.
	 *
	 * @param day day parameter.
	 * @param month month parameter.
	 * @param year year parameter.
	 * @return a string of form: "year.month.day", at least digits for each value.
	 */
	public static String getDateString(int day, int month, int year){
		return itoa(day,2)+ '.' +itoa(month,2)+ '.' +itoa(year,2);
	}

	/**
	 * Returns the ISO8601 confirmant date string for the given time.
	 *
	 * @param millis the time in millis since 01.01.1970.
	 * @return string in form of YYYY-MM-DD.
	 */
	public static String makeISO8601DateString(long millis){
		Date d = new Date(millis);
		return itoa(d.year,4)+ '-' +itoa(d.month)+ '-' +itoa(d.day);
	}

	/**
	 * Returns the ISO8601 date of now.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeISO8601DateString(){
		return makeISO8601DateString(System.currentTimeMillis());
	}
	
	/**
	 * Returns the ISO8601 timestamp of now.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String makeISO8601TimestampString(){
		return makeISO8601TimestampString(System.currentTimeMillis());
	}
	
	/**
	 * Creates an ISO8601 confirm timestamp string in form of YYYY-MM-DDTHH:MM:SS,zzz.
	 *
	 * @param millis time in millis.
	 * @return string that represents the time parameter as iso8601 timestamp.
	 */
	public static String makeISO8601TimestampString(long millis){
		String ret = makeISO8601DateString(millis);
		Date d = new Date(millis);
		ret += 'T' +itoa(d.hour)+ ':' +itoa(d.min)+ ':' +itoa(d.sec);
		ret += ',' +itoa((int)(millis-millis/1000*1000),3);
		return ret; 
	}

	/**
	 * <p>parseDateString.</p>
	 *
	 * @param str a {@link java.lang.String} object.
	 * @return a long.
	 */
	public static long parseDateString(String str){
		if (str.isEmpty())
			return 0;
		String[] t = StringUtils.tokenize(str, '.');
		int d = Integer.parseInt(t[0]);
		int m = Integer.parseInt(t[1]);
		int y = Integer.parseInt(t[2]);
		if (y<100){
			if (y<20)
				y+=2000;
			else
				y+=1900;
		}
		return new Date(d, m, y).toMill();
	}

	/**
	 * <p>getCurrencyValue.</p>
	 *
	 * @param aValue a float.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getCurrencyValue(float aValue){
		return getCurrencyValue((double)aValue);
	}

	/**
	 * Returns rounded value as currency value (2 digits precision). This method uses
	 * dot as separator regardless of the locale.
	 *
	 * @param aValue the value to convert.
	 * @return a currency value like 20.02.
	 */
	public static String getCurrencyValue(double aValue){
		aValue += 0.005;
		int tmp = (int)(aValue*100);
		aValue = (double)tmp/100;
		String ret = String.valueOf(aValue);
		int ind = ret.indexOf('.');
		if (ind==-1)
			return ret + ".00";
		if (ret.length()-ind-1<2)
			ret += "0";
		return ret;
	}
	
	/**
	 * Returns true if the number parameter can pass a luhn check.
	 * see http://en.wikipedia.org/wiki/Luhn_check for details on luhn algorithm.
	 *
	 * @param aNumber the number to check.
	 * @return true if the number passes the check or false otherwise.
	 */
	public static boolean luhnCheckNumber(String aNumber){
		int sum = 0;
		int l = aNumber.length()-1;
		boolean doDouble = false;
		for (int i=0; i<aNumber.length(); i++){
			int n = aNumber.charAt(l-i)-'0';
			if (n<0 || n>10)
				throw new IllegalArgumentException("Not a digit: "+aNumber.charAt(l-i)+" in "+aNumber+" at "+(l-i));
			if (doDouble){
				int tmp = n*2;
				sum += tmp%10;
				sum += tmp/10;
				
			}else{
				sum += n;
			}
			doDouble = !doDouble;
		}
		return sum % 10 == 0;
	}
	
	/**
	 * Returns a string representation of the parameter in which each three digit part of the number is separated by a '.'.
	 * For example 123456 -&gt; 123.456. 1234567 -&gt; 1.234.567 and so on.
	 *
	 * @param number the number to transform.
	 * @return transfored (doted) version of the parameter.
	 */
	public static String getDotedNumber(long number){
		return getDotedNumber(number, '.');
	}
	
	/**
	 * <p>getDotedNumber.</p>
	 *
	 * @param number a long.
	 * @param separatorChar a char.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDotedNumber(long number, char separatorChar){
		if (number<0)
			return '-' +getDotedNumber(-1*number, separatorChar);
		String n = StringUtils.reverseString(String.valueOf(number));
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<n.length(); i++){
			ret.insert(0, n.charAt(i));
			if (i<n.length()-1 && (i+1)%3==0)
				ret.insert(0, separatorChar);
		}
		return ret.toString();
	}
	
	/**
	 * <p>getDotedNumber.</p>
	 *
	 * @param number a long.
	 * @param locale a {@link java.util.Locale} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDotedNumber(long number, Locale locale){
		if (locale.getLanguage()==null)
			return getDotedNumber(number);
		if (locale.getLanguage().equals(Locale.GERMAN.getLanguage()))
			return getDotedNumberDE(number);
		return getDotedNumberUS(number);
	}
	
	/**
	 * <p>getDotedNumberUS.</p>
	 *
	 * @param number a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDotedNumberUS(long number){
		return getDotedNumber(number, ',');
	}

	/**
	 * <p>getDotedNumberDE.</p>
	 *
	 * @param number a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDotedNumberDE(long number){
		return getDotedNumber(number, '.');
	}
	
	/**
	 * <p>fractionRound.</p>
	 *
	 * @param value a double.
	 * @param fraction a int.
	 * @return a double.
	 */
	public static double fractionRound(double value, int fraction){
		int shift = (int) StrictMath.pow(10, fraction);
		return 1d * Math.round(value * shift)/shift;
	}
	
	/**
	 * <p>format.</p>
	 *
	 * @param value a double.
	 * @param integral a int.
	 * @param fraction a int.
	 * @param delimiter a char.
	 * @return a {@link java.lang.String} object.
	 */
	public static String format(double value, int integral, int fraction, char delimiter){
		String integralPattern = "#0";
		if(integral > 0){
			integralPattern = "0";
			for(int i = 1; i < integral; i++)
				integralPattern += '0';
		}		
		
		String fractionPattern = "########################";
		if(fraction > 0){
			fractionPattern = "0";
			for(int i = 1; i < fraction; i++)
				fractionPattern += '0';
		}
		
		DecimalFormatSymbols delimiterFormat = new DecimalFormatSymbols();
		delimiterFormat.setDecimalSeparator(delimiter);
		
		return new DecimalFormat(integralPattern + '.' + fractionPattern, delimiterFormat).format(BigDecimal.valueOf(value));
	}
	
	/**
	 * <p>currencyFormat.</p>
	 *
	 * @param value a double.
	 * @param delimiter a char.
	 * @return a {@link java.lang.String} object.
	 */
	public static String currencyFormat(double value, char delimiter){
		return format(value, -1, 2, delimiter);
	}
	
	/**
	 * Ensure class can't be instantiated.
	 */
	private NumberUtils(){}

}
