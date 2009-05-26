package net.anotheria.util;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutionTimer {

	private static final int METHOD_KEY = 1;
	private static final int METHOD_TIME = 2;
	private static final int METHOD_CREATION = 3;

	private Hashtable<String, TimerEntry> timers;
	private AtomicInteger nextID = new AtomicInteger(0);

	private String name;

	/**
	 * Creates a new ExecutionTimer.
	 */
    public ExecutionTimer(String aName) {
		timers = new Hashtable<String, TimerEntry>();
		this.name = aName;
    }

	/**
	 * Creates a new unnamed ExecutionTimer.
	 */
	public ExecutionTimer(){
	    this("timer");
	}

	/**
	 * Notifies timer that the execution of the process assigned with the given key started.
	 * The key must be an unique identifier in one ExecutionTimer.
	 *
	 */
	public void startExecution(String aKey){
		TimerEntry entry = new TimerEntry(aKey);
		timers.put(aKey, entry);
	}
	
	public void continueExecution(String aKey){
		try{
			TimerEntry entry = getTimerEntry(aKey);
			entry.continueExecution();
		}catch(Exception e){
			startExecution(aKey);
		}
	}

	/**
	 * Notifies the timer, that the execution of the process assigned with the given key stoped.
	 */
	public void stopExecution(String aKey){
		getTimerEntry(aKey).stop();
	}

	/**
	 * Returns the execution time for the given process. If the process haven't been started,
	 * an exception will be thrown. If the process is still running, the start time will be returned.
	 */
	public long getExecutionTime(String aKey){
		return getTimerEntry(aKey).getTime();
	}

	/**
	 * Returns the sum of the single execution times.
	 */
	public long getTotalExecutionTime(){
		Enumeration<TimerEntry> e = timers.elements();
		long sum = 0;
		while(e.hasMoreElements()){
		    TimerEntry entry = e.nextElement();
			sum += entry.getTime();
		}
		return sum;
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their keys.
	 */
	public Vector<TimerEntry> getExecutionTimerEntriesOrderedByKeys(){
		return sortVector(getTimerEntriesVector(), METHOD_KEY);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their creation order.
	 */
	public Vector<TimerEntry> getExecutionTimerEntriesOrderedByCreation(){
		return sortVector(getTimerEntriesVector(), METHOD_CREATION);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their execution time (fastest first).
	 */
	public Vector<TimerEntry> getExecutionTimerEntriesOrderedByTime(){
		return sortVector(getTimerEntriesVector(), METHOD_TIME);
	}

	/**
	 * Prints out all timer entries ordered by execution time (fastest first).
	 */
	public void printExecutionTimesOrderedByTime(){
		printExecutionTimes(METHOD_TIME);
	}
	/**
	 * Prints out all timer entries ordered by key.
	 */
	public void printExecutionTimesOrderedByKeys(){
		printExecutionTimes(METHOD_KEY);
	}

	/**
	 * Prints out all timer entries ordered by creation order.
	 */
	public void printExecutionTimesOrderedByCreation(){
		printExecutionTimes(METHOD_CREATION);
	}

	private void printExecutionTimes(int method){
	    Vector<TimerEntry> v = sortVector(getTimerEntriesVector(), method);
		System.out.println("============= "+name+" =============");
		for (int i=0; i<v.size(); i++){
		    System.out.println( v.elementAt(i).toString(method) );
		}
		System.out.print("=============");
		for (int t=0;t<name.length()+2;t++)
			System.out.print("=");
		System.out.println("=============");
	}


	private Vector<TimerEntry> sortVector(Vector<TimerEntry> aSrc, int aMethod){
	    boolean changed = true;
		while(changed){
		    changed = false;
			for (int i=0; i<aSrc.size()-1; i++){
			    TimerEntry first = (TimerEntry)aSrc.elementAt(i);
				TimerEntry second = (TimerEntry)aSrc.elementAt(i+1);
				int result = compare(first, second, aMethod);
				if (result>0){
				    changed = true;
					aSrc.setElementAt(second,i);
					aSrc.setElementAt(first,i+1);
				}
			}
		}
		return aSrc;
	}

	private int compare(TimerEntry aFirst, TimerEntry aSecond, int aMethod){
		switch(aMethod){
			case METHOD_CREATION:
				return aFirst.id < aSecond.id ? -1 : aFirst.id>aSecond.id ? 1 : 0;
			case METHOD_TIME:
				long time1 = aFirst.getTime();
				long time2 = aSecond.getTime();
				return time1 < time2 ? -1 : time1 > time2 ? 1 : 0;
		    case METHOD_KEY:
			default:
				return aFirst.key.compareToIgnoreCase(aSecond.key);
		}
	}

	private Vector<TimerEntry> getTimerEntriesVector(){
	    Vector<TimerEntry> ret = new Vector<TimerEntry>();
		Enumeration<TimerEntry> e = timers.elements();
		while(e.hasMoreElements())
			ret.addElement(e.nextElement());
		return ret;
	}

	private TimerEntry getTimerEntry(String aKey){
	    TimerEntry entry = (TimerEntry) timers.get(aKey);
		if (entry==null)
			throw new RuntimeException("No such key:\""+aKey+"\"");
		return entry;
	}

	class TimerEntry{
	    private long startTime;
		private long endTime;
		private long previousTime;
		private int id;
		private String key;

		/**
		 * Creates new timer entry with given key and next creation id and starts it.
		 */
		TimerEntry(String aKey){
			this.key = aKey;
			id = nextID.incrementAndGet();
			start();
			previousTime = 0L;
		}

		/**
		 * Starts time measuring.
		 */
		protected void start(){
		    startTime = System.currentTimeMillis();
		}

		/**
		 * Ends time measuring.
		 */
		protected void stop(){
		    endTime = System.currentTimeMillis();
		}
		
		protected void continueExecution(){
			previousTime += endTime-startTime;
			start();
		}

		/**
		 * Returns the time between start and stop in millis.
		 */
		public long getTime(){
		    return (endTime - startTime)+previousTime;
		}

		/**
		 * Returns the Key (unique String id) of this TimerEntry.
		 */
		public String getKey(){
		    return key;
		}

		/**
		 * Returns the creation id of this TimerEntry. 1 means first, 2 - second etc.
		 */
		public int getCreationID(){
		    return id;
		}

		/**
		 * Returns a method dependent string representation of this object.
		 */
		public String toString(int aMethod){
			switch(aMethod){
				case METHOD_CREATION:
					return toStringCreation();
				case METHOD_TIME:
				case METHOD_KEY:
				default:
					return toStringKey();
			}

		}

		private String toStringCreation(){
		    return id+"\t"+key+"\t"+getTime();
		}

		private String toStringKey(){
		    return key+"\t"+getTime();
		}

		/**
		 * Returns the standart string representation of this object.
		 */
		public String toString(){
		    return toStringKey();
		}
	}
	

}
