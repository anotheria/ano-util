/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/Date.java,v $
  $Author: lro $
  $Date: 2005/01/07 12:15:34 $
  $Revision: 1.4 $


  Copyright 2002 by BeagleSoft GmbH, Berlin, Germany
  All rights reserved.

  This software is the confidential and proprietary information
  of BeagleSoft GmbH. ("Confidential Information").  You
  shall not disclose such Confidential Information and shall use
  it only in accordance with the terms of the license agreement
  you entered into with BeagleSoft GmbH.
  See www.beaglesoft.biz for details.
** ------------------------------------------------------------------------- */
package net.anotheria.util;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * This class provide a simple date object
 **/
public class Date implements Serializable{
    private static final long serialVersionUID = -8683008562899070994L;
    public static final int DD___MM___YYYY_HHMM = 1;
    public static final int DD___MM___YYYY      = 2;
    public static final int DD__MM__YYYY_HHMM   = 3;
    public static final int DD__MM__YYYY        = 4;
    public static final int DD_MM_YYYY_HHMM     = 5;
    public static final int DD_MM_YYYY          = 6;


    /** The day of week. Initial value <code>null</code> */
    public String w_day = null;
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
    
    /** String array of month used for printing via <code>toString()</code> method*/
    public static final String[] MONTH     = {null,"Jan","Feb","Mar","Apr","Mai","Jun",
                                               "Jul","Aug","Sep","Okt","Nov","Dec"};

	public static final String[] FULLMONTH_DE = {null, "Januar", "Februar", "März", "April",
 		"Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"};

    /** Maximum nubers of days in month in a not leapyear*/
    public static final int[]  DAY_OF_MONTH= {-1,31,28,31,30,31,30,31,31,30,31,30,31};
    /** The array of day names in a week*/
    public static final String[] DAY   = {"So.","Mo.","Di.","Mi.","Do.","Fr.","Sa."};

    /** Default constructor. Create a date
     * without hour an minutes notification. The minuts and hours have
     * a value of -1 The day name will be
     * calculated automaticly ba the <code>calcDay()</code> function.
     * @param day the day
     * @param month the month
     * @param year the year
     */
    public Date(int day, int month, int year) {
        this(day, month, year, -1,-1);
    }

    /** Default constructor. Create a date object. The day name will be
     * calculated automaticly ba the <code>calcDay()</code> function.
     * @param day the day
     * @param month the month
     * @param year the year
     */
    public Date(int day, int month, int year, int hour, int min){
        this.month = month;
        this.day   = day;
        this.year  = year;
        this.hour  = hour;
        this.min   = min;
        this.w_day = calcDay(day,month,year);
    }

    public Date(long millis){
        java.util.Date d = new java.util.Date(millis);
        GregorianCalendar cal = new GregorianCalendar();
     	cal.setGregorianChange(new java.util.Date(Long.MIN_VALUE));
        cal.setTime(d);
        this.month = cal.get(GregorianCalendar.MONTH)+1;
        this.day   = cal.get(GregorianCalendar.DATE);
        this.year  = cal.get(GregorianCalendar.YEAR);
        this.hour  = cal.get(GregorianCalendar.HOUR_OF_DAY);
        this.min   = cal.get(GregorianCalendar.MINUTE);
        this.sec   = cal.get(GregorianCalendar.SECOND);
        this.w_day = calcDay(day, month, year);
    }
    

    /**
     * Create a Date object for this moment
     */
    public static Date currentDate(){
		return new Date(System.currentTimeMillis());
    }

    /** Calculate the day of week.
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the day of week for given date
     **/
    private String calcDay(int day, int month, int year){
		 //System.out.println("Month="+month);
         GregorianCalendar cal = new GregorianCalendar(year/*-1900*/, (month-1), day);
         int dow = cal.get(GregorianCalendar.DAY_OF_WEEK);
	     //dow = (dow+4)%7;
        // if(dow == 0)
        // dow =  6;
        dayOfWeek = dow-1;
        return DAY[dayOfWeek];
    }

    /** Check date
     * @param d1 the date object to check it
     * @return true if and only if the given data object is valid
     */
    public static boolean isValid(Date d1){
        if(d1.month < 1 || d1.month > 12)
            return false;
        if(d1.day < 1)
            return false;
        GregorianCalendar cal = new GregorianCalendar();
        if(d1.month == 2 && cal.isLeapYear(d1.year) && d1.day > 29)
            return false;
        if(d1.month == 2 && !cal.isLeapYear(d1.year) && d1.day > 28)
            return false;
        if(d1.month !=2 && d1.day > DAY_OF_MONTH[d1.month])
            return false;
        if(d1.hour < -1 || d1.hour > 23)
            return false;
        if(d1.min <-1 || d1.min > 59)
            return false;
        String w_day = d1.calcDay(d1.day,d1.month,d1.year);
        if(!w_day.equals(d1.w_day))
            return false;
        return true;
    }
    
    public boolean isValid(){
    	return isValid(this);
    }

    /**
     * Returns the string representation of this date.
     * changed by L.R. 30.06.2000.
     */
    public String toString(){
		String ret = w_day;
 		ret+=" "+NumberUtils.itoa(day, 2) + ".";
		ret+= NumberUtils.itoa(month,2)+"."+year;

	    if(min != -1){
		    ret = ret + ", ";
		    ret += NumberUtils.itoa(hour, 2)+":";
			ret += NumberUtils.itoa(min, 2);
        }
		return ret;
    }

    /**
     * Parse date out from string
     * @param date_strg Source string to parse it
     */
    public static Date parse(String date_strg){
       if(date_strg.indexOf('.')!=-1&& date_strg.indexOf(':')==-1)
            return parseShort(date_strg,'.');
       if(date_strg.indexOf('.')!=-1&& date_strg.indexOf(':')!=-1)
            return parseLong(date_strg,'.');
       if(date_strg.indexOf('-')!=-1&& date_strg.indexOf(':')==-1)
            return parseShort(date_strg,'-');
       if(date_strg.indexOf('-')!=-1&& date_strg.indexOf(':')!=-1)
            return parseLong(date_strg,'-');
       if(date_strg.indexOf(' ')!=-1&& date_strg.indexOf(':')==-1)
            return parseShort(date_strg,' ');
       if(date_strg.indexOf(' ')!=-1&& date_strg.indexOf(':')!=-1)
            return parseLong(date_strg,' ');
       if(date_strg.indexOf('.')!=-1&& date_strg.indexOf(':')==-1)
            return parseShort(date_strg,'.');
       if(date_strg.indexOf('.')!=-1&& date_strg.indexOf(':')!=-1)
            return parseLong(date_strg,'.');
       return null;




    }

    /**
     * Parse date out from string with specified format
     * @param date_strg Source string to parse it
     */
    public static Date parse(String date_strg, int format){
        switch(format){
            case DD___MM___YYYY_HHMM : return parseLong(date_strg,'.');
            case DD___MM___YYYY      : return parseShort(date_strg,'.');
            case DD__MM__YYYY_HHMM : return parseLong(date_strg,'-');
            case DD__MM__YYYY      : return parseShort(date_strg,'-');
            case DD_MM_YYYY_HHMM   : return parseLong(date_strg,' ');
            case DD_MM_YYYY        : return parseShort(date_strg,' ');
        }
        return null;
    }

    /**
     * Parse date with hors and minutes out from string
     * @param date_strg Source string to parse it
     */
    public static Date parseLong(String date_strg, char ch){
        int first_ = date_strg.indexOf(ch,0);
        //first_--;
        String dayStrg   = date_strg.substring(0,first_);
        first_++;
        int second_ = date_strg.indexOf(ch,first_);
        //second_--;
        String monthStrg = date_strg.substring(first_,second_);
        int yearB = second_+1;
        int yearE = second_+5;
        String yearStrg  = date_strg.substring(yearB,yearE);
        second_=second_+6;
        int dbp = date_strg.indexOf(':');
        String hrStrg  = date_strg.substring(second_,dbp);
        String minStrg = date_strg.substring((dbp+1), date_strg.length());
        try{
            int day   = Integer.parseInt(dayStrg);
            int month = Integer.parseInt(monthStrg);
            int year  = Integer.parseInt(yearStrg);
            int hour  = Integer.parseInt(hrStrg);
            int min   = Integer.parseInt(minStrg);
            Date ret  = new Date(day,month,year,hour,min);
            return ret;
        } catch(NumberFormatException nfe){return null;}


    }


    /**
     * Parse date without hors and minutes out from string
     * @param date_strg source string to parse it
     */
    public static Date parseShort(String date_strg, char ch){
        int first_ = date_strg.indexOf(ch,0);
        //first_--;
        String dayStrg   = date_strg.substring(0,first_);
        first_++;
        int second_ = date_strg.indexOf(ch,first_);
        //second_--;
        String monthStrg = date_strg.substring(first_,second_);
        second_++;
        String yearStrg  = date_strg.substring(second_,date_strg.length());
        try{
            int day   = Integer.parseInt(dayStrg);
            int month = Integer.parseInt(monthStrg);
            int year  = Integer.parseInt(yearStrg);
            Date ret = new Date(day,month,year);
            return ret;
        } catch(NumberFormatException nfe){return null;}
    }

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

	public boolean equals(Object o){
 		return ( o instanceof Date ) ? 
 			((Date)o).toMill()==toMill() : 
 			false;
  	}

	/**
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @return
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return
	 */
	public String getW_day() {
		return w_day;
	}

	/**
	 * @return
	 */
	public int getYear() {
		return year;
	}

}


/* ------------------------------------------------------------------------- *
  $Log: Date.java,v $
  Revision 1.4  2005/01/07 12:15:34  lro
  *** empty log message ***

  Revision 1.3  2005/01/06 18:35:02  lro
  added seconds

  Revision 1.2  2004/08/19 08:53:23  lro
  *** empty log message ***

  Revision 1.1  2004/02/06 21:41:49  lro
  *** empty log message ***

  Revision 1.1.1.1  2004/02/04 16:31:10  lro
  initial checkin

  Revision 1.1  2004/01/30 22:06:41  cvs
  *** empty log message ***

  Revision 1.1.1.1  2002/02/05 16:26:21  another
  no message


** ------------------------------------------------------------------------- */
