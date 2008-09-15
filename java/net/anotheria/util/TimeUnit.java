package net.anotheria.util;

public enum TimeUnit {
	
	MILLISECOND(1),
	SECCOND(1000),
	MINUTE(60*SECCOND.getMillis()),
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
		System.out.println(MINUTE.getMillis());
	}
}
