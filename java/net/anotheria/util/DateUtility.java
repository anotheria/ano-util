package  net.anotheria.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A utility for operation with Date objects.
 */
public final class DateUtility {

	/**
	 * Returns true if the first date is after second date.
	 * @param d1
	 * @param d2
	 * @return
	 */
    public static boolean isAfter(Date d1, Date d2) {
        if (d1.year < d2.year)
            return  false;
        if (d1.year > d2.year)
            return  true;
        if (d1.month < d2.month)
            return  false;
        if (d1.month > d2.month)
            return  true;
        if (d1.day < d2.day)
            return  false;
        if (d1.day > d2.day)
            return  true;
        if ((d1.hour != -1) && (d1.min != -1) && (d2.hour != -1) && (d2.min!= -1)) {
            if (d1.hour < d2.hour)
                return  false;
            if (d1.hour > d2.hour)
                return  true;
            if (d1.min < d2.min)
                return  false;
            if (d1.min > d2.min)
                return  true;
        }
        return  false;
    }

    /**
     * put your documentation comment here
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isBefore(Date d1, Date d2) {
        return  isAfter(d2, d1);
    }

    /**
     * put your documentation comment here
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isSame(Date d1, Date d2) {
        return  !isAfter(d2, d1) && !isBefore(d2, d1);
    }

    /**
     * put your documentation comment here
     * @param d
     * @return
     */
    public static Date nextDate(Date d) {
        if (!Date.isValid(d))
            return  null;
        int day = d.day;
        day++;
        Date nextDate = new Date(day, d.month, d.year);
        if (!Date.isValid(nextDate)) {
            int month = d.month + 1;
            nextDate = new Date(1, month, d.year);
            if (!Date.isValid(nextDate)) {
                int year = d.year + 1;
                nextDate = new Date(1, 1, year);
            }
        }
        return  nextDate;
    }

    /**
     * put your documentation comment here
     * @param d
     * @return
     */
    public static Date previousDate(Date d) {
        if (!Date.isValid(d))
            return  null;
        int day = d.day;
        day--;
        Date prevDate = new Date(day, d.month, d.year);
        if (!Date.isValid(prevDate)) {
            int month = d.month - 1;
            prevDate = new Date(Date.DAY_OF_MONTH[month], month, d.year);
            if (!Date.isValid(prevDate)) {
                int year = d.year - 1;
                prevDate = new Date(31, 12, year);
            }
        }
        return  prevDate;
    }

    /**
     * put your documentation comment here
     * @param date
     * @return
     */
    public static GregorianCalendar toLocalTime(GregorianCalendar date) {
        GregorianCalendar calLOC = new GregorianCalendar();
        GregorianCalendar calSRC = new GregorianCalendar(date.getTimeZone());
        int offset = -(calLOC.get(Calendar.HOUR_OF_DAY) - calSRC.get(Calendar.HOUR_OF_DAY))*60*60*1000;
        calSRC.set(Calendar.ZONE_OFFSET, offset);
        calSRC.setTimeZone(calLOC.getTimeZone());
        date.set(Calendar.ZONE_OFFSET, offset);
        return  date;
    }

    /**
     * put your documentation comment here
     * @param d
     */
    public static String toDateOnly(Date d) {
        String ret = "";
        ret += (d.day < 10 ? "0" + d.day : "" + d.day) + ".";
        ret += (d.month < 10 ? "0" + d.month : "" + d.month) + ".";
        ret += d.year;
        return  ret;
    }

    /**
     * put your documentation comment here
     * @param d
     * @return
     */
    public static String toDayAndMonthOnly(Date d) {
        String ret = "";
        ret += (d.day < 10 ? "0" + d.day : "" + d.day) + ".";
        ret += (d.month < 10 ? "0" + d.month : "" + d.month) + ".";
        return  ret;
    }

    /**
     * put your documentation comment here
     * @param d
     * @return
     */
    public static String toTimeOnly(Date d) {
        return  (d.hour < 10 ? "0" + d.hour : "" + d.hour) + ":" + (d.min < 10 ?
                "0" + d.min : "" + d.min);
    }

    /**
     * put your documentation comment here
     * @param date
     * @return
     */
    public static String dynamicString(Date date) {
        int year = date.year;
        int hour = date.hour;
        int min = date.min;
        int day = date.day;
        int month = date.month;
        if ((min == -1 || min == 0) && (hour == -1 || hour == 0)) {
            if (year == 0) {
                if (month == 0 || day == 0)
                    return  null;
                return  "" + (date.day < 10 ? "0" + date.day : "" + date.day)
                        + "." + (date.month < 10 ? "0" + date.month : "" +
                        date.month);
            }
            return  toDateOnly(date);
        }
        if (year == 0) {
            if (month == 0 || day == 0)
                return  toTimeOnly(date);
            else {
                return  "" + (date.day < 10 ? "0" + date.day : "" + date.day)
                        + "." + (date.month < 10 ? "0" + date.month : "" +
                        date.month) + ", " + toTimeOnly(date);
            }
        }
        return  toDateOnly(date) + ", " + toTimeOnly(date);
    }

    /**
     * Returns the date which lies one week after given date.
     */
    public static Date nextWeek(Date d) {
        Date d2 = new Date(d.toMill() + (7L*24*60*60*1000));
        return  d2;
    }

    /**
     * Returns the calendar week for this date.
     * @added by lro, 28-10-00
     * bug with week calculation for days in november/december
     */
    public static int getCalendarWeekForDate(Date date) {
        //first we need to find where the 4. januar is (this is the first cal week).
        Date startOfFirstWeek = new Date(4, 1, date.year);
        Date startOfThisWeek;
        int cal = 1;
        //calculate the day where this week started (for year change for example).
        startOfThisWeek = date;
        while (!startOfThisWeek.wDay.equals("Mo."))
            startOfThisWeek = DateUtility.previousDate(startOfThisWeek);
        //System.out.println("Start of this week:"+startOfThisWeek);
        if (!startOfThisWeek.equals(date))
            return  getCalendarWeekForDate(startOfThisWeek);
        //System.out.println("Date:"+date);
        //calculate the day where the first week in this year started (the first week is the week in which
        //the 4th january is.
        while (!startOfFirstWeek.wDay.equals("Mo."))
            startOfFirstWeek = DateUtility.previousDate(startOfFirstWeek);
        if (date.month == 1 && startOfThisWeek.month == 12) {
            if (date.day == 1 && (date.wDay.equals("So.") || date.wDay.equals("Sa.")
                    || date.wDay.equals("Fr.")))
                return  getCalendarWeekForDate(startOfThisWeek);
        }
        while (DateUtility.isBefore(startOfFirstWeek, date)) {
            startOfFirstWeek = DateUtility.nextWeek(startOfFirstWeek);
            startOfFirstWeek.hour = 0;
            cal++;
            if (DateUtility.isBefore(date, startOfFirstWeek)) {
                cal--;
            }
        }
        return  cal;
    }

    /**
     * Returns the date for the first day of a "calendar week" for a given year.
     * @param week
     * @param year
     * @return
     */
    public static Date getFirstDayOfWeek(int week, int year) {
        Date d = new Date(4, 1, year);
        while (!d.wDay.equals("Mo."))
            d = previousDate(d);
        //der erste tag der ersten woche.
        int kw = 1;
        while (kw < week) {
            d = nextDate(nextDate(nextDate(nextDate(nextDate(nextDate(nextDate(d)))))));
            kw++;
        }
        return  d;
    }
    
	/**
	 * Returns age - a period of time, measured by years from fromDate till now
	 * @param fromDate start date in mills
	 * @return age
	 */
	
	public static int getAge(long fromDate){
		return getAge(fromDate, System.currentTimeMillis());
	}
	
	/**
	 * Returns age - a period of time, measured by years from fromDate till now
	 * @param fromDate start date
	 * @return age
	 */
	public static int getAge(Date fromDate){
		return getAge(fromDate, new Date(System.currentTimeMillis()));
	}
    
	/**
	 * Returns age - a period of time, measured by years from fromDate to toDate
	 * @param fromDate start date in mills
	 * @param toDate end date in mills
	 * @return age
	 */
	public static int getAge(long fromDate, long toDate){
		Date _fromDate = new Date(fromDate);
		Date _toDate = new Date(toDate);
		return getAge(_fromDate, _toDate);
	}
	
	/**
	 * Returns age - a period of time, measured by years from fromDate to toDate
	 * @param fromDate start date
	 * @param toDate end date
	 * @return age
	 */
	public static int getAge(Date fromDate, Date toDate){
		int age = toDate.getYear() - fromDate.getYear();
		
		int monthDiff = toDate.getMonth() - fromDate.getMonth();
		if(monthDiff > 0)
			return age;
		if(monthDiff < 0)
			return --age;
		
		int daysDiff = toDate.getDay() - fromDate.getDay();
		if(daysDiff > 0)
			return age;
		if(daysDiff < 0)
			return --age;	
		
		return age > 0? age : 0;
	}
	
	public static int getDays(Date fromDate){
		return getDays(fromDate.toMill());
	}
	
	public static int getDays(long fromDate){
		return getDays(fromDate, System.currentTimeMillis());
	}
	
	public static int getDays(Date fromDate, Date toDate){
		return getDays(fromDate.toMill(), toDate.toMill());
	}

	public static int getDays(long fromDate, long toDate){
		return (int) ((toDate - fromDate)/TimeUnit.DAY.getMillis());
	}
	
	public static long getHourBeginning(){
		return getHourBeginning(System.currentTimeMillis());
	}
	
	public static long getHourBeginning(long date){
		Date d = new Date(date);
		return new Date(d.getDay(),d.getMonth(),d.getYear(),d.getHour(),0).toMill();
	}
	
	public static long getNextHourBeginning(){
		return getHourBeginning() + TimeUnit.HOUR.getMillis();
	}
	
	public static long getNextHourBeginning(long date){
		return getHourBeginning(date) + TimeUnit.HOUR.getMillis();
	}
	
	public static long getDayBeginning(long date){
		Date d = new Date(date);
		return new Date(d.getDay(),d.getMonth(),d.getYear(),0,0).toMill();
	}
	
	public static long getDayEnding(long date){
		return getDayBeginning(date) + TimeUnit.DAY.getMillis() - 1;
	}
	
	
    /**
     * Prevent initialization.
     */
    private DateUtility(){
  
    }
 }
