/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/ExecutionTimer.java,v $
  $Author: lro $
  $Date: 2004/03/29 09:39:04 $
  $Revision: 1.2 $


  Copyright 2002-2004 by Anotheria.net, Berlin, Germany
  All rights reserved.

  This software is the confidential and proprietary information
  of Anotheria.net. ("Confidential Information").  You
  shall not disclose such Confidential Information and shall use
  it only in accordance with the terms of the license agreement
  you entered into with Anotheria.net.
  See www.anotheria.net for details.
** ------------------------------------------------------------------------- */
package net.anotheria.util;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

public class ExecutionTimer {

	private static final int METHOD_KEY = 1;
	private static final int METHOD_TIME = 2;
	private static final int METHOD_CREATION = 3;

	private Hashtable timers;
	private int nextID;

	private String name;

	/**
	 * Creates a new ExecutionTimer.
	 */
    public ExecutionTimer(String name) {
		timers = new Hashtable();
		nextID = 1;
		this.name = name;
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
	public void startExecution(String key){
		TimerEntry entry = new TimerEntry(key);
		timers.put(key, entry);
	}
	
	public void continueExecution(String key){
		try{
			TimerEntry entry = getTimerEntry(key);
			entry.continueExecution();
		}catch(Exception e){
			startExecution(key);
		}
	}

	/**
	 * Notifies the timer, that the execution of the process assigned with the given key stoped.
	 */
	public void stopExecution(String key){
		getTimerEntry(key).stop();
	}

	/**
	 * Returns the execution time for the given process. If the process haven't been started,
	 * an exception will be thrown. If the process is still running, the start time will be returned.
	 */
	public long getExecutionTime(String key){
		return getTimerEntry(key).getTime();
	}

	/**
	 * Returns the sum of the single execution times.
	 */
	public long getTotalExecutionTime(){
		Enumeration e = timers.elements();
		long sum = 0;
		while(e.hasMoreElements()){
		    TimerEntry entry = (TimerEntry) e.nextElement();
			sum += entry.getTime();
		}
		return sum;
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their keys.
	 */
	public Vector getExecutionTimerEntriesOrderedByKeys(){
		return sortVector(getTimerEntriesVector(), METHOD_KEY);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their creation order.
	 */
	public Vector getExecutionTimerEntriesOrderedByCreation(){
		return sortVector(getTimerEntriesVector(), METHOD_CREATION);
	}

	/**
	 * Returns a vector with all TimerEntries in this Timer sorted by their execution time (fastest first).
	 */
	public Vector getExecutionTimerEntriesOrderedByTime(){
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
	    Vector v = sortVector(getTimerEntriesVector(), method);
		System.out.println("============= "+name+" =============");
		for (int i=0; i<v.size(); i++){
		    System.out.println( ((TimerEntry)v.elementAt(i)).toString(method) );
		}
		System.out.print("=============");
		for (int t=0;t<name.length()+2;t++)
			System.out.print("=");
		System.out.println("=============");
	}


	private Vector sortVector(Vector src, int method){
	    boolean changed = true;
		while(changed){
		    changed = false;
			for (int i=0; i<src.size()-1; i++){
			    TimerEntry first = (TimerEntry)src.elementAt(i);
				TimerEntry second = (TimerEntry)src.elementAt(i+1);
				int result = compare(first, second, method);
				if (result>0){
				    changed = true;
					src.setElementAt(second,i);
					src.setElementAt(first,i+1);
				}
			}
		}
		return src;
	}

	private int compare(TimerEntry first, TimerEntry second, int method){
		switch(method){
			case METHOD_CREATION:
				return first.id < second.id ? -1 : first.id>second.id ? 1 : 0;
			case METHOD_TIME:
				long time1 = first.getTime();
				long time2 = second.getTime();
				return time1 < time2 ? -1 : time1 > time2 ? 1 : 0;
		    case METHOD_KEY:
			default:
				return first.key.compareToIgnoreCase(second.key);
		}
	}

	private Vector getTimerEntriesVector(){
	    Vector ret = new Vector();
		Enumeration e = timers.elements();
		while(e.hasMoreElements())
			ret.addElement(e.nextElement());
		return ret;
	}

	private TimerEntry getTimerEntry(String key){
	    TimerEntry entry = (TimerEntry) timers.get(key);
		if (entry==null)
			throw new RuntimeException("No such key:\""+key+"\"");
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
		TimerEntry(String key){
			this.key = key;
			id = nextID++;
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
		public String toString(int method){
			switch(method){
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
	
	public static void main(String a []) throws Exception{
		ExecutionTimer t = new ExecutionTimer("1");
		t.startExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		t.printExecutionTimesOrderedByCreation();
		t.continueExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		t.printExecutionTimesOrderedByCreation();
		t.continueExecution("sleep");
		Thread.sleep(500);
		t.stopExecution("sleep");
		t.printExecutionTimesOrderedByCreation();
	}


}
/* ------------------------------------------------------------------------- *
  $Log: ExecutionTimer.java,v $
  Revision 1.2  2004/03/29 09:39:04  lro
  *** empty log message ***

  Revision 1.1  2004/02/06 21:41:49  lro
  *** empty log message ***

  Revision 1.1.1.1  2004/02/04 16:31:11  lro
  initial checkin

  Revision 1.1  2004/01/30 22:06:41  cvs
  *** empty log message ***

  Revision 1.1.1.1  2002/02/05 16:26:21  another
  no message


** ------------------------------------------------------------------------- */





