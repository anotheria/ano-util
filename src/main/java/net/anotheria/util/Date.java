package net.anotheria.util;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * This class provide a simple date object.
 *
 * @author another
 * @version $Id: $Id
 */
public class Date implements Serializable{
    private static final long serialVersionUID = -8683008562899070994L;


    /** The day of week. Initial value {@code null} */
    public String wDay = null;
    /** The day of month. Initial value is -1.*/
    public int    day   = -1;
    /** The month. Initial value is -1.*/
    public int    month = -1;
    /** The yaer. Initial value is -1.*/
    public int    year  = -1;
    /** The hour of day. Initial value is -1.*/
    public int    hour  = -1;
    /** 
     * The minutes of hour. Initial value is -1.
     */
    public int    min   = -1;
    
    public int sec = 0;
    
    private int dayOfWeek;
    
    /** String array of month used for printing via {@code toString()} method*/
    public static final String[] MONTH     = {null,"Jan","Feb","Mar","Apr","Mai","Jun",
                                               "Jul","Aug","Sep","Oct","Nov","Dec"};

    /** Maximum nubers of days in month in a not leapyear*/
    public static final int[]  DAY_OF_MONTH= {-1,31,28,31,30,31,30,31,31,30,31,30,31};
    /** The array of day names in a week*/
    public static final String[] DAY   = {"Sun.","Mon.","Tue.","Wed.","Thu.","Fri.","Sat."};

    /**
     * Default constructor. Create a date
     * without hour an minutes notification. The minuts and hours have
     * a value of -1 The day name will be
     * calculated automaticly ba the {@code calcDay()} function.
     *
     * @param aDay the day
     * @param aMonth the month
     * @param aYear the year
     */
    public Date(int aDay, int aMonth, int aYear) {
        this(aDay, aMonth, aYear, -1,-1);
    }

    /**
     * Default constructor. Create a date object. The day name will be
     * calculated automaticly ba the {@code calcDay()} function.
     *
     * @param aDay the day
     * @param aMonth the month
     * @param aYear the year
     * @param aHour a int.
     * @param aMin a int.
     */
    public Date(int aDay, int aMonth, int aYear, int aHour, int aMin){
        this.month = aMonth;
        this.day   = aDay;
        this.year  = aYear;
        this.hour  = aHour;
        this.min   = aMin;
        this.wDay = calcDay(aDay,aMonth,aYear);
    }

    /**
     * Creates a new date object which correspongs to the given time in millis since 01.01.1970.
     *
     * @param aMillis a long.
     */
    public Date(long aMillis){
        GregorianCalendar cal = new GregorianCalendar();
     	cal.setGregorianChange(new java.util.Date(Long.MIN_VALUE));
        java.util.Date d = new java.util.Date(aMillis);
        cal.setTime(d);
        this.month = cal.get(GregorianCalendar.MONTH)+1;
        this.day   = cal.get(GregorianCalendar.DATE);
        this.year  = cal.get(GregorianCalendar.YEAR);
        this.hour  = cal.get(GregorianCalendar.HOUR_OF_DAY);
        this.min   = cal.get(GregorianCalendar.MINUTE);
        this.sec   = cal.get(GregorianCalendar.SECOND);
        this.wDay = calcDay(day, month, year);
    }
    

    /**
     * Create a Date object for this moment
     *
     * @return a {@link net.anotheria.util.Date} object.
     */
    public static Date currentDate(){
		return new Date(System.currentTimeMillis());
    }

    /** Calculate the day of week.
     * @param aDay the day
     * @param aMonth the month
     * @param aYear the year
     * @return the day of week for given date
     **/
    private String calcDay(int aDay, int aMonth, int aYear){
		 //System.out.println("Month="+month);
         GregorianCalendar cal = new GregorianCalendar(aYear/*-1900*/, (aMonth-1), aDay);
         int dow = cal.get(GregorianCalendar.DAY_OF_WEEK);
	     //dow = (dow+4)%7;
        // if(dow == 0)
        // dow =  6;
        dayOfWeek = dow-1;
        return DAY[dayOfWeek];
    }

    /**
     * Check date
     *
     * @param aD1 the date object to check it
     * @return true if and only if the given data object is valid
     */
    public static boolean isValid(Date aD1){
        if(aD1.month < 1 || aD1.month > 12)
            return false;
        if(aD1.day < 1)
            return false;
        GregorianCalendar cal = new GregorianCalendar();
        if(aD1.month == 2 && cal.isLeapYear(aD1.year) && aD1.day > 29)
            return false;
        if(aD1.month == 2 && !cal.isLeapYear(aD1.year) && aD1.day > 28)
            return false;
        if(aD1.month !=2 && aD1.day > DAY_OF_MONTH[aD1.month])
            return false;
        if(aD1.hour < -1 || aD1.hour > 23)
            return false;
        if(aD1.min <-1 || aD1.min > 59)
            return false;
        String pWDay = aD1.calcDay(aD1.day,aD1.month,aD1.year);
        return pWDay.equals(aD1.wDay);
    }
    
    /**
     * Returns true if this date is a valid date. For example 30 Februar is not a valid date.
     *
     * @return a boolean.
     */
    public boolean isValid(){
    	return isValid(this);
    }

    /**
     * {@inheritDoc}
     *
     * Returns the string representation of this date.
     */
    @Override public String toString(){
		String ret = wDay;
 		ret+= ' ' +NumberUtils.itoa(day, 2) + '.';
		ret+= NumberUtils.itoa(month,2)+ '.' +year;

	    if(min != -1){
		    ret = ret + ", ";
		    ret += NumberUtils.itoa(hour, 2)+ ':';
			ret += NumberUtils.itoa(min, 2);
        }
		return ret;
    }

    /**
     * Parse date out from string
     *
     * @param aDateStrg Source string to parse it
     * @return a {@link net.anotheria.util.Date} object.
     */
    public static Date parse(String aDateStrg){
       if(aDateStrg.indexOf('.')!=-1&& aDateStrg.indexOf(':')==-1)
            return parseShort(aDateStrg,'.');
       if(aDateStrg.indexOf('.')!=-1&& aDateStrg.indexOf(':')!=-1)
            return parseLong(aDateStrg,'.');
       if(aDateStrg.indexOf('-')!=-1&& aDateStrg.indexOf(':')==-1)
            return parseShort(aDateStrg,'-');
       if(aDateStrg.indexOf('-')!=-1&& aDateStrg.indexOf(':')!=-1)
            return parseLong(aDateStrg,'-');
       if(aDateStrg.indexOf(' ')!=-1&& aDateStrg.indexOf(':')==-1)
            return parseShort(aDateStrg,' ');
       if(aDateStrg.indexOf(' ')!=-1&& aDateStrg.indexOf(':')!=-1)
            return parseLong(aDateStrg,' ');
       if(aDateStrg.indexOf('.')!=-1&& aDateStrg.indexOf(':')==-1)
            return parseShort(aDateStrg,'.');
       if(aDateStrg.indexOf('.')!=-1&& aDateStrg.indexOf(':')!=-1)
            return parseLong(aDateStrg,'.');
       return null;




    }


    /**
     * Parse date with hors and minutes out from string
     *
     * @param aDateStrg Source string to parse it
     * @param aCh a char.
     * @return a {@link net.anotheria.util.Date} object.
     */
    public static Date parseLong(String aDateStrg, char aCh){
        int pFirst = aDateStrg.indexOf(aCh,0);
        //first_--;
        String dayStrg   = aDateStrg.substring(0,pFirst);
        pFirst++;
        int pSecond = aDateStrg.indexOf(aCh,pFirst);
        //second_--;
        String monthStrg = aDateStrg.substring(pFirst,pSecond);
        int yearB = pSecond+1;
        int yearE = pSecond+5;
        String yearStrg  = aDateStrg.substring(yearB,yearE);
        pSecond=pSecond+6;
        int dbp = aDateStrg.indexOf(':');
        String hrStrg  = aDateStrg.substring(pSecond,dbp);
        String minStrg = aDateStrg.substring((dbp+1), aDateStrg.length());
        try{
            int day   = Integer.parseInt(dayStrg);
            int month = Integer.parseInt(monthStrg);
            int year  = Integer.parseInt(yearStrg);
            int hour  = Integer.parseInt(hrStrg);
            int min   = Integer.parseInt(minStrg);
            return new Date(day,month,year,hour,min);
        } catch(NumberFormatException nfe){
			return null;
        }
    }


    /**
     * Parse date without hors and minutes out from string
     *
     * @param aDateStrg source string to parse it
     * @param aCh a char.
     * @return a {@link net.anotheria.util.Date} object.
     */
    public static Date parseShort(String aDateStrg, char aCh){
        int pFirst = aDateStrg.indexOf(aCh,0);
        if(pFirst < 0)
        	throw new IllegalArgumentException("Date string wrong format: " + aDateStrg + ". Expected: dd" + aCh + "mm" + aCh + "yyyy");
        //first_--;
        String dayStrg   = aDateStrg.substring(0,pFirst);
        pFirst++;
        int pSecond = aDateStrg.indexOf(aCh,pFirst);
        if(pSecond < 0)
        	throw new IllegalArgumentException("Date string wrong format: " + aDateStrg + ". Expected: dd" + aCh + "mm" + aCh + "yyyy");
        //second_--;
        String monthStrg = aDateStrg.substring(pFirst,pSecond);
        pSecond++;
        String yearStrg  = aDateStrg.substring(pSecond,aDateStrg.length());
        try{
            int day   = Integer.parseInt(dayStrg);
            int month = Integer.parseInt(monthStrg);
            int year  = Integer.parseInt(yearStrg);
            return new Date(day,month,year);
        } catch(NumberFormatException nfe){
        	throw new IllegalArgumentException("Date string wrong format: " + aDateStrg + ". Expected: dd" + aCh + "mm" + aCh + "yyyy", nfe);
        }
    }

    /**
     * <p>toMill.</p>
     *
     * @return a long.
     */
    public long toMill(){
        GregorianCalendar cal;
        if(this.hour !=-1)
            cal = new GregorianCalendar(year, (month-1), day, hour, min);
        else
            cal = new GregorianCalendar(year, (month-1), day,0,0);
        // java.util.Date d = cal.getGregorianChange();
        java.util.Date d = cal.getTime();
        return d.getTime();
    }

	/** {@inheritDoc} */
	@Override public boolean equals(Object o){
 		return (o instanceof Date) && ((Date) o).toMill() == toMill();
  	}

	@Override
	public int hashCode() {
		int result = day;
		result = 31 * result + month;
		result = 31 * result + year;
		result = 31 * result + hour;
		result = 31 * result + min;
		result = 31 * result + sec;
		return result;
	}

	/**
	 * <p>Getter for the field <code>day</code>.</p>
	 *
	 * @return a int.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * <p>Getter for the field <code>dayOfWeek</code>.</p>
	 *
	 * @return a int.
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * <p>Getter for the field <code>hour</code>.</p>
	 *
	 * @return a int.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * <p>Getter for the field <code>min</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * <p>Getter for the field <code>month</code>.</p>
	 *
	 * @return a int.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * <p>getW_day.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getW_day() {
		return wDay;
	}

	/**
	 * <p>Getter for the field <code>year</code>.</p>
	 *
	 * @return a int.
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Returns the ISO8601 timestamp of this date.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toISO8601Timestamp(){
		return NumberUtils.makeISO8601TimestampString(toMill());
	}

	/**
	 * Returns this date as ISO8601 format.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toISO8601Date(){
		return NumberUtils.makeISO8601DateString(toMill());
	}

}

