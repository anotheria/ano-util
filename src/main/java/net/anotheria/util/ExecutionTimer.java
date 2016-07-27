package net.anotheria.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A utility to measure execution of a task which consists of subtasks.  
 * @author lrosenberg
 */
public class ExecutionTimer {

	/**
	 * Method for sorting, comparison and toString output.
	 * @author lrosenberg
	 *
	 */
	private enum Method{
		/**
		 * By creation key which is what the user selects as the unique identifier of a subtask.
		 */
		KEY,
		/**
		 * By execution time.
		 */
		TIME,
		/**
		 * By creation time.
		 */
		CREATION;
	};

	/**
	 * Running timers.
	 */
	private Map<String, TimerEntry> timers;
	/**
	 * Next unique id in this timer.
	 */
	private AtomicInteger nextID = new AtomicInteger(0);
	/**
	 * Name of the ExecutionTimer (i.e. name of the outer task like "calculateWorksheet"). 
	 */
	private String name;

	/**
	 * Creates a new ExecutionTimer.
	 */
    public ExecutionTimer(String aName) {
		timers = new ConcurrentHashMap<>();
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
	/**
	 * Continues previously stoped(paused) execution of the key.
	 */
	public void continueExecution(String aKey){
		try{
			TimerEntry entry = getTimerEntry(aKey);
			entry.continueExecution();
		}catch(RuntimeException e){
			startExecution(aKey);
		}
	}

	/**
	 * Notifies the timer, that the execution of the process assigned with the given key stoped or paused.
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
		Collection<TimerEntry> entries = timers.values();
		long sum = 0;
		for (TimerEntry entry : entries){
			sum += entry.getTime();
		}
		return sum;
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their keys.
	 */
	public List<TimerEntry> getExecutionTimerEntriesOrderedByKeys(){
		return sortEntries(getTimerEntries(), Method.KEY);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their creation order.
	 */
	public List<TimerEntry> getExecutionTimerEntriesOrderedByCreation(){
		return sortEntries(getTimerEntries(), Method.CREATION);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their execution time (fastest first).
	 */
	public List<TimerEntry> getExecutionTimerEntriesOrderedByTime(){
		return sortEntries(getTimerEntries(), Method.TIME);
	}

	/**
	 * Prints out all timer entries ordered by execution time (fastest first).
	 */
	public void printExecutionTimesOrderedByTime(){
		printExecutionTimes(Method.TIME);
	}
	/**
	 * Prints out all timer entries ordered by key.
	 */
	public void printExecutionTimesOrderedByKeys(){
		printExecutionTimes(Method.KEY);
	}

	/**
	 * Prints out all timer entries ordered by creation order.
	 */
	public void printExecutionTimesOrderedByCreation(){
		printExecutionTimes(Method.CREATION);
	}

	/**
	 * Prints execution times in the given sort order.
	 */
	private void printExecutionTimes(Method method){
	    List<TimerEntry> v = sortEntries(getTimerEntries(), method);
		System.out.println("============= "+name+" =============");
		for (TimerEntry aV : v) {
			System.out.println(aV.toString(method));
		}
		System.out.print("=============");
		for (int t=0;t<name.length()+2;t++)
			System.out.print("=");
		System.out.println("=============");
	}


	private List<TimerEntry> sortEntries(List<TimerEntry> aSrc, Method aMethod){
	    boolean changed = true;
		while(changed){
		    changed = false;
			for (int i=0; i<aSrc.size()-1; i++){
			    TimerEntry first = aSrc.get(i);
				TimerEntry second = aSrc.get(i+1);
				int result = compare(first, second, aMethod);
				if (result>0){
				    changed = true; 
					aSrc.set(i, second);
					aSrc.set(i+1, first);
				}
			}
		}
		return aSrc;
	}

	private int compare(TimerEntry aFirst, TimerEntry aSecond, Method aMethod){
		switch(aMethod){
			case CREATION:
				return aFirst.id < aSecond.id ? -1 : aFirst.id>aSecond.id ? 1 : 0;
			case TIME:
				long time1 = aFirst.getTime();
				long time2 = aSecond.getTime();
				return time1 < time2 ? -1 : time1 > time2 ? 1 : 0;
		    case KEY:
			default:
				return aFirst.key.compareToIgnoreCase(aSecond.key);
		}
	}
	/**
	 * Returns all contained timer entries.
	 */
	private List<TimerEntry> getTimerEntries(){
		List<TimerEntry> ret = new ArrayList<>(timers.values());
        return ret;
	}
	
	/**
	 * Returns the timer entry with given key.
	 * @param aKey the key.
	 */
	private TimerEntry getTimerEntry(String aKey){
	    TimerEntry entry = timers.get(aKey);
		if (entry==null)
			throw new RuntimeException("No such key:\""+aKey+ '"');
		return entry;
	}

	private class TimerEntry{
		/**
		 * Start time of this entry.
		 */
	    private long startTime;
	    /**
	     * End time of this entry.
	     */
		private long endTime;
		/**
		 * Stores previous duration for interrupted (stoped and restarted) tasks.
		 */
		private long previousTime;
		/**
		 * Id of this entry (order of creation).
		 */
		private int id;
		/**
		 * Key of this entry (unique id submitted by the user).
		 */
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
		public String toString(Method aMethod){
			switch(aMethod){
				case CREATION:
					return toStringCreation();
				case TIME:
				case KEY:
				default:
					return toStringKey();
			}

		}

		private String toStringCreation(){
		    return id+"\t"+key+ '\t' +getTime();
		}

		private String toStringKey(){
		    return key+ '\t' +getTime();
		}

		/**
		 * Returns the standart string representation of this object.
		 */
		@Override public String toString(){
		    return toStringKey();
		}
	}
}
