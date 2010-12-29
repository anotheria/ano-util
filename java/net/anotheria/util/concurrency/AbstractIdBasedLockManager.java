package net.anotheria.util.concurrency;

import java.util.Map;

abstract class AbstractIdBasedLockManager {
	int getLockSize(){
		return getLockMap().size();
	}
	
	String debugString(){
		return getLockMap().toString();
	}
	
	public static void out(Object message){
		//System.out.println(Thread.currentThread().getName()+" "+message);
	}
	
	protected abstract Map<String, IdBasedLock> getLockMap();

}
