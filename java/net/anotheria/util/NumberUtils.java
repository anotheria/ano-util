package net.anotheria.util;

/**
 * @author lrosenberg
 */
public final class NumberUtils {

	/**
	 * Constants for month names.
	 */	
	public static final String MONTH[] = {
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
	 * Example: itoa(23, 4) -> 0023.
	 */
	public static String itoa(int i, int limit){
		String a = ""+i;
		while (a.length()<limit)
			a = "0"+a;
		return a;			
	}
	
	/**
	 * Calls itoa(i,2);
	 */
	public static String itoa(int i){
		return itoa(i,2);
	}

	
	/**
	 * Often needed by different packages
	 * Return a time string in form of DD MMM YYYY (23 FEB 2002).
	 */
	public static String makeDateStringLong(long time){
		Date date = new Date(time);
		return itoa(date.day)+" "+
				MONTH[date.month]+" "+
				date.year;
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of DD MMM YY (23 FEB 02).
	 */
	public static String makeDateString(long time){
		Date date = new Date(time);
		int minus = date.year >2000 ? 2000 : 1900;
		return itoa(date.day)+" "+
				MONTH[date.month]+" "+
				itoa(date.year - minus);
	}
	
	/**
	 * Returns a digital time string (23.02.2002).
	 * @param time
	 * @return
	 */
	public static String makeDigitalDateString(long time){
		Date date = new Date(time);
		return itoa(date.day)+"."+
				itoa(date.month)+"."+
				itoa(date.year);
	}

	public static String makeDigitalDateStringLong(long time){
		Date date = new Date(time);
		return itoa(date.day)+"."+
				itoa(date.month)+"."+
				itoa(date.year,4);
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of hh:mm.
	 */
	public static String makeTimeString(long time){
		Date date = new Date(time);
		return itoa(date.hour)+":"+ itoa(date.min);
	}

	/**
	 * Often needed by different packages
	 * Return a time string in form of hh:mm am/pm.
	 */
	public static String makeTimeString12H(long time){
		Date date = new Date(time);
		int hour = date.hour;
		String t = "am";
		if (hour>12){
			hour-=12;
			t = "pm";
		}
		return itoa(hour)+":"+ itoa(date.min)+" "+t;
	}

	/**
	 * Returns the given size in bytes as short string (Kb, Mb, B)...
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
		
		return ""+size+" "+bytes;
			
	}

	/**
	 * Returns a short date string in form of "YY.MM.DD" or "YYYY.MM.DD" depending on the year parameter.  
	 * @param day day parameter.
	 * @param month month parameter.
	 * @param year year parameter.
	 * @return a string of form: "year.month.day", at least digits for each value.
	 */
	public static String getDateString(int day, int month, int year){
		return itoa(day,2)+"."+itoa(month,2)+"."+itoa(year,2);
	}

	/**
	 * Returns the ISO8601 confirmant date string for the given time. 
	 * @param millis the time in millis since 01.01.1970.
	 * @return string in form of YYYY-MM-DD.
	 */
	public static String makeISO8601DateString(long millis){
		Date d = new Date(millis);
		return itoa(d.year,4)+"-"+itoa(d.month)+"-"+itoa(d.day);
	}
	
	/**
	 * Creates an ISO8601 confirm timestamp string in form of YYYY-MM-DDTHH:MM:SS,zzz.
	 * @param millis time in millis.
	 * @return string that represents the time parameter as iso8601 timestamp.
	 */
	public static String makeISO8601TimestampString(long millis){
		String ret = makeISO8601DateString(millis);
		Date d = new Date(millis);
		ret += "T"+itoa(d.hour)+":"+itoa(d.min)+":"+itoa(d.sec);
		ret += ","+itoa((int)(millis-millis/1000*1000),3);
		return ret; 
	}

	public static long parseDateString(String str){
		if (str.length()==0)
			return 0;
		String t[] = StringUtils.tokenize(str, '.');
		int d,m,y;
		d = Integer.parseInt(t[0]);
		m = Integer.parseInt(t[1]);
		y = Integer.parseInt(t[2]);
		if (y<100){
			if (y<20)
				y+=2000;
			else
				y+=1900;
		}
		return new Date(d, m, y).toMill();
	}

	public static String getCurrencyValue(float aValue){
		return getCurrencyValue((double)aValue);
	}

	/**
	 * Returns rounded value as currency value (2 digits precision). This method uses 
	 * dot as separator regardless of the locale.
	 * @param aValue the value to convert.
	 * @return a currency value like 20.02.
	 */
	public static String getCurrencyValue(double aValue){
		aValue += 0.005;
		int tmp = (int)(aValue*100);
		aValue = (double)((double)tmp/100);
		String ret = ""+aValue;
		int ind = ret.indexOf('.');
		if (ind==-1)
			return ret + ".00";
		if (ret.length()-ind-1<2)
			ret += "0";
		return ret;
	}
	
	/**
	 * Returns true if the number parameter can pass a luhn check. 
	 * @see http://en.wikipedia.org/wiki/Luhn_check for details on luhn algorithm.
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
	 * For example 123456 -> 123.456. 1234567 -> 1.234.567 and so on.
	 * @param number the number to transform.
	 * @return transfored (doted) version of the parameter.
	 */
	public static String getDotedNumber(long number){
		String n = StringUtils.reverseString(""+number);
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<n.length(); i++){
			ret.insert(0, n.charAt(i));
			if (i<n.length()-1 && (i+1)%3==0)
				ret.insert(0, '.');
		}
		return ret.toString();
	}
	
	/**
	 * Ensure class can't be instantiated.
	 */
	private NumberUtils(){}
}
