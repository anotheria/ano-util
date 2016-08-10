package net.anotheria.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A utility for operation with Date objects.
 *
 * @author another
 * @version $Id: $Id
 */
public final class DateUtility {

	/**
	 * <p>isAfterNow.</p>
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isAfterNow(Date d) {
		return isAfter(d, new Date(System.currentTimeMillis()));
	}

	/**
	 * Returns true if the first date is after second date.
	 *
	 * @param d1 a {@link net.anotheria.util.Date} object.
	 * @param d2 a {@link net.anotheria.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isAfter(Date d1, Date d2) {
		if (d1.year < d2.year)
			return false;
		if (d1.year > d2.year)
			return true;
		if (d1.month < d2.month)
			return false;
		if (d1.month > d2.month)
			return true;
		if (d1.day < d2.day)
			return false;
		if (d1.day > d2.day)
			return true;
		if ((d1.hour != -1) && (d1.min != -1) && (d2.hour != -1) && (d2.min != -1)) {
			if (d1.hour < d2.hour)
				return false;
			if (d1.hour > d2.hour)
				return true;
			if (d1.min < d2.min)
				return false;
			if (d1.min > d2.min)
				return true;
		}
		return false;
	}

	/**
	 * <p>isBeforeNow.</p>
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isBeforeNow(Date d) {
		return isBefore(d, new Date(System.currentTimeMillis()));
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d1 a {@link net.anotheria.util.Date} object.
	 * @param d2 a {@link net.anotheria.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isBefore(Date d1, Date d2) {
		return isAfter(d2, d1);
	}

	/**
	 * Return true if incoming {@link net.anotheria.util.Date} is Today, false otherwise.
	 * {@link java.lang.IllegalArgumentException} will be thrown if incoming {@link net.anotheria.util.Date} is {@code null}.
	 *
	 * @param date {@link net.anotheria.util.Date} to check
	 * @return boolean value
	 */
	public static boolean isToday(final Date date) {
		if (date == null)
			throw new IllegalArgumentException("Invalid incoming parameter date");
		final Date currentDate = new Date(System.currentTimeMillis());
		return date.getYear() == currentDate.getYear() && date.getMonth() == currentDate.getMonth() && date.getDay() == currentDate.getDay();
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d1 a {@link net.anotheria.util.Date} object.
	 * @param d2 a {@link net.anotheria.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isSame(Date d1, Date d2) {
		return !isAfter(d2, d1) && !isBefore(d2, d1);
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link net.anotheria.util.Date} object.
	 */
	public static Date nextDate(Date d) {
		if (!Date.isValid(d))
			return null;
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
		return nextDate;
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link net.anotheria.util.Date} object.
	 */
	public static Date previousDate(Date d) {
		if (!Date.isValid(d))
			return null;
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
		return prevDate;
	}

	/**
	 * put your documentation comment here
	 *
	 * @param date a {@link java.util.GregorianCalendar} object.
	 * @return a {@link java.util.GregorianCalendar} object.
	 */
	public static GregorianCalendar toLocalTime(GregorianCalendar date) {
		GregorianCalendar calLOC = new GregorianCalendar();
		GregorianCalendar calSRC = new GregorianCalendar(date.getTimeZone());
		int offset = -(calLOC.get(Calendar.HOUR_OF_DAY) - calSRC.get(Calendar.HOUR_OF_DAY)) * 60 * 60 * 1000;
		calSRC.set(Calendar.ZONE_OFFSET, offset);
		calSRC.setTimeZone(calLOC.getTimeZone());
		date.set(Calendar.ZONE_OFFSET, offset);
		return date;
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toDateOnly(Date d) {
		String ret = "";
		ret += (d.day < 10 ? "0" + d.day : String.valueOf(d.day)) + '.';
		ret += (d.month < 10 ? "0" + d.month : String.valueOf(d.month)) + '.';
		ret += d.year;
		return ret;
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toDayAndMonthOnly(Date d) {
		String ret = "";
		ret += (d.day < 10 ? "0" + d.day : String.valueOf(d.day)) + '.';
		ret += (d.month < 10 ? "0" + d.month : String.valueOf(d.month)) + '.';
		return ret;
	}

	/**
	 * put your documentation comment here
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toTimeOnly(Date d) {
		return (d.hour < 10 ? "0" + d.hour : String.valueOf(d.hour)) + ':' + (d.min < 10 ?
				"0" + d.min : String.valueOf(d.min));
	}

	/**
	 * put your documentation comment here
	 *
	 * @param date a {@link net.anotheria.util.Date} object.
	 * @return a {@link java.lang.String} object.
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
					return null;
				return (date.day < 10 ? "0" + date.day : String.valueOf(date.day)) + '.' + (date.month < 10 ? "0" + date.month : String.valueOf(date.month));
			}
			return toDateOnly(date);
		}
		if (year == 0) {
			if (month == 0 || day == 0)
				return toTimeOnly(date);
			else {
				return (date.day < 10 ? "0" + date.day : String.valueOf(date.day)) + '.' + (date.month < 10 ? "0" + date.month : String.valueOf(date.month)) + ", " + toTimeOnly(date);
			}
		}
		return toDateOnly(date) + ", " + toTimeOnly(date);
	}

	/**
	 * Returns the date which lies one week after given date.
	 *
	 * @param d a {@link net.anotheria.util.Date} object.
	 * @return a {@link net.anotheria.util.Date} object.
	 */
	public static Date nextWeek(Date d) {
		return new Date(d.toMill() + (7L * 24 * 60 * 60 * 1000));
	}

	/**
	 * Returns the calendar week for this date.
	 *
	 * @since 28-10-00
	 * bug with week calculation for days in november/december
	 * @param date a {@link net.anotheria.util.Date} object.
	 * @return a int.
	 */
	public static int getCalendarWeekForDate(Date date) {
		while (true) {
			//first we need to find where the 4. januar is (this is the first cal week).
			Date startOfFirstWeek = new Date(4, 1, date.year);
			Date startOfThisWeek;
			int cal = 1;
			//calculate the day where this week started (for year change for example).
			startOfThisWeek = date;
			while (!startOfThisWeek.wDay.equals("Mo."))
				startOfThisWeek = DateUtility.previousDate(startOfThisWeek);
			//System.out.println("Start of this week:"+startOfThisWeek);
			if (!startOfThisWeek.equals(date)) {
				date = startOfThisWeek;
				continue;
			}
			//System.out.println("Date:"+date);
			//calculate the day where the first week in this year started (the first week is the week in which
			//the 4th january is.
			while (!startOfFirstWeek.wDay.equals("Mo."))
				startOfFirstWeek = DateUtility.previousDate(startOfFirstWeek);
			if (date.month == 1 && startOfThisWeek.month == 12) {
				if (date.day == 1 && (date.wDay.equals("So.") || date.wDay.equals("Sa.")
						|| date.wDay.equals("Fr."))) {
					date = startOfThisWeek;
					continue;
				}
			}
			while (DateUtility.isBefore(startOfFirstWeek, date)) {
				startOfFirstWeek = DateUtility.nextWeek(startOfFirstWeek);
				startOfFirstWeek.hour = 0;
				cal++;
				if (DateUtility.isBefore(date, startOfFirstWeek)) {
					cal--;
				}
			}
			return cal;
		}
	}

	/**
	 * Returns the date for the first day of a "calendar week" for a given year.
	 *
	 * @param week a int.
	 * @param year a int.
	 * @return a {@link net.anotheria.util.Date} object.
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
		return d;
	}

	/**
	 * Returns age - a period of time, measured by years from fromDate till now
	 *
	 * @param fromDate start date in mills
	 * @return a int.
	 */
	public static int getAge(long fromDate) {
		return getAge(fromDate, System.currentTimeMillis());
	}

	/**
	 * Returns age - a period of time, measured by years from fromDate till now
	 *
	 * @param fromDate start date
	 * @return a int.
	 */
	public static int getAge(Date fromDate) {
		return getAge(fromDate, new Date(System.currentTimeMillis()));
	}

	/**
	 * Returns age - a period of time, measured by years from fromDate to toDate
	 *
	 * @param fromDate start date in mills
	 * @param toDate   end date in mills
	 * @return a int.
	 */
	public static int getAge(long fromDate, long toDate) {
		Date _fromDate = new Date(fromDate);
		Date _toDate = new Date(toDate);
		return getAge(_fromDate, _toDate);
	}

	/**
	 * Returns age - a period of time, measured by years from fromDate to toDate
	 *
	 * @param fromDate start date
	 * @param toDate   end date
	 * @return a int.
	 */
	public static int getAge(Date fromDate, Date toDate) {
		int age = toDate.getYear() - fromDate.getYear();

		int monthDiff = toDate.getMonth() - fromDate.getMonth();
		if (monthDiff > 0)
			return age;
		if (monthDiff < 0)
			return --age;

		int daysDiff = toDate.getDay() - fromDate.getDay();
		if (daysDiff > 0)
			return age;
		if (daysDiff < 0)
			return --age;

		return age > 0 ? age : 0;
	}

	/**
	 * <p>getDays.</p>
	 *
	 * @param fromDate a {@link net.anotheria.util.Date} object.
	 * @return a int.
	 */
	public static int getDays(Date fromDate) {
		return getDays(fromDate.toMill());
	}

	/**
	 * <p>getDays.</p>
	 *
	 * @param fromDate a long.
	 * @return a int.
	 */
	public static int getDays(long fromDate) {
		return getDays(fromDate, System.currentTimeMillis());
	}

	/**
	 * <p>getDays.</p>
	 *
	 * @param fromDate a {@link net.anotheria.util.Date} object.
	 * @param toDate a {@link net.anotheria.util.Date} object.
	 * @return a int.
	 */
	public static int getDays(Date fromDate, Date toDate) {
		return getDays(fromDate.toMill(), toDate.toMill());
	}

	/**
	 * <p>getDays.</p>
	 *
	 * @param fromDate a long.
	 * @param toDate a long.
	 * @return a int.
	 */
	public static int getDays(long fromDate, long toDate) {
		return (int) ((toDate - fromDate) / TimeUnit.DAY.getMillis());
	}

	/**
	 * <p>getHourBeginning.</p>
	 *
	 * @return a long.
	 */
	public static long getHourBeginning() {
		return getHourBeginning(System.currentTimeMillis());
	}

	/**
	 * <p>getHourBeginning.</p>
	 *
	 * @param date a long.
	 * @return a long.
	 */
	public static long getHourBeginning(long date) {
		Date d = new Date(date);
		return new Date(d.getDay(), d.getMonth(), d.getYear(), d.getHour(), 0).toMill();
	}

	/**
	 * <p>getNextHourBeginning.</p>
	 *
	 * @return a long.
	 */
	public static long getNextHourBeginning() {
		return getHourBeginning() + TimeUnit.HOUR.getMillis();
	}

	/**
	 * <p>getNextHourBeginning.</p>
	 *
	 * @param date a long.
	 * @return a long.
	 */
	public static long getNextHourBeginning(long date) {
		return getHourBeginning(date) + TimeUnit.HOUR.getMillis();
	}

	/**
	 * <p>getDayBeginning.</p>
	 *
	 * @param date a long.
	 * @return a long.
	 */
	public static long getDayBeginning(long date) {
		Date d = new Date(date);
		return new Date(d.getDay(), d.getMonth(), d.getYear(), 0, 0).toMill();
	}

	/**
	 * <p>getDayEnding.</p>
	 *
	 * @param date a long.
	 * @return a long.
	 */
	public static long getDayEnding(long date) {
		return getDayBeginning(date) + TimeUnit.DAY.getMillis() - 1;
	}


	/**
	 * Prevent initialization.
	 */
	private DateUtility() {

	}
}
