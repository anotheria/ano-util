package net.anotheria.util;

public enum TimeUnit {
	
	MILLISECOND(1),
	SECOND(1000),
	MINUTE(60*SECOND.getMillis()),
	HOUR(60*MINUTE.getMillis()),
	DAY(24*HOUR.getMillis()),
	WEEK(7*DAY.getMillis()),
	YEAR((long)(DAY.getMillis()*365.25));
	
	private final long inMills;
	
	private TimeUnit(long inMills){
		this.inMills = inMills;
	}
	
	public long getMillis(){
		return inMills;
	}
}
 