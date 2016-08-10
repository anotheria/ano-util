package net.anotheria.util;
/**
 * Enumeration for timeunits used along ano-libs and applications. Allows recalculation in milliseconds.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public enum TimeUnit {
	/**
	 * The milliseconds.
	 */
	MILLISECOND(1),
	/**
	 * Seconds.
	 */
	SECOND(1000),
	/**
	 * Minutes.
	 */
	MINUTE(60*SECOND.getMillis()),
	/**
	 * Hours.
	 */
	HOUR(60*MINUTE.getMillis()),
	/**
	 * Days.
	 */
	DAY(24*HOUR.getMillis()),
	/**
	 * Weeks.
	 */
	WEEK(7*DAY.getMillis()),
	/**
	 * Years.
	 */
	YEAR((long)(DAY.getMillis()*365.25));
	
	/**
	 * Recalculation factor.
	 */
	private final long inMills;
	
	/**
	 * Creates a new TimeUnit with given factor.
	 */
	private TimeUnit(long aInMills){
		this.inMills = aInMills;
	}
	
	/**
	 * Returns the duration of the unit in milliseconds.
	 *
	 * @return a long.
	 */
	public long getMillis(){
		return getMillis(1);
	}
	
	/**
	 * Returns the duration of units in milliseconds.
	 *
	 * @param unitsCount a int.
	 * @return a long.
	 */
	public long getMillis(int unitsCount){
		return inMills * unitsCount;
	}
}
 
