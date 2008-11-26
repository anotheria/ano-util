package test;

import net.anotheria.util.Date;

public class TestLong {
	public static void main(String[] args) {
		
		System.out.println(new Date(1,1,1976).toMill());
		
		int s = 1000*60*60*24;
		System.out.println(s);
		int days = Integer.MAX_VALUE / s;
		System.out.println(days+", "+days/365);
	}
}
