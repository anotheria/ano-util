package net.anotheria.util;
/**
 * Enumeration for timeunits used along ano-libs and applications. Allows recalculation in milliseconds.
 * @author lrosenberg
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
	 * @param aInMills
	 */
	private TimeUnit(long aInMills){
		this.inMills = aInMills;
	}
	/**
	 * Returns the duration of the period in milliseconds.
	 * @return
	 */
	public long getMillis(){
		return inMills;
	}
}
 