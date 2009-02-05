package net.anotheria.util;

public enum TimeUnit {
	
	MILLISECOND(1),
	SECOND(1000),
	MINUTE(60*SECOND.getMillis()),
	HOUR(60*MINUTE.getMillis()),
	DAY(24*HOUR.getMillis()),
	WEEK(7*DAY.getMillis());
	
	private final long inMills;
	
	TimeUnit(long inMills){
		this.inMills = inMills;
	}
	
	public long getMillis(){
		return inMills;
	}
	
	public static void main(String[] args) {
		System.out.println(4*WEEK.inMills);
		System.out.println(4*(7*(24*(60*(60*1000L)))));
		System.out.println(4*7*24*60*60*1000);
		System.out.println(4L*7*24*60*60*1000);
	}
}
 