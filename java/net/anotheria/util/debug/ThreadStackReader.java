package net.anotheria.util.debug;

import net.anotheria.util.StringUtils;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.02.13 17:45
 */
public class ThreadStackReader {
	public static void main(String a[]) throws Exception{
		test2();
	}

	private static void test() throws Exception{
		ThreadDump stackTrace1 = readStackTrace("/Users/another/Documents/Projects/AYN/CRASH/2013-02-05/5.txt");
		System.out.println(stackTrace1);
		//
		System.out.println("Total: "+stackTrace1.getTotalThreadCount());
		System.out.println("Daemons: "+stackTrace1.getDaemonThreadCount());
		stackTrace1.dumpOutShortened();
	}

	private static void test2() throws Exception{

		compare("/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-45.txt",
				"/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-10-44.txt");

		compare("/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-55.txt",
				"/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-45.txt");

		compare("/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-57-syncstarted.txt",
				"/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-55.txt");

		compare("/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-12-01-updatefinished.txt",
				"/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-11-57-syncstarted.txt");

		compare("/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-12-01-updatefinished.txt",
				"/Users/another/Documents/Projects/AYN/CRASH/2013-02-06/jstack-2013-02-06-10-44.txt");

	}

	private static void compare(String newer, String older) throws Exception{
		System.out.println("====================================");
		System.out.println("COMPARING "+newer+" and "+older);
		compare(readStackTrace(newer), readStackTrace(older));
	}

	private static void compare(ThreadDump newer, ThreadDump older){
		System.out.println("TC:\t"+newer.getTotalThreadCount()+"\t"+older.getTotalThreadCount()+" Dif: "+(newer.getTotalThreadCount()-older.getTotalThreadCount()));
		System.out.println("DC:\t"+newer.getDaemonThreadCount()+"\t"+older.getDaemonThreadCount()+" Dif: "+(newer.getDaemonThreadCount()-older.getDaemonThreadCount()));

		HashSet<DebugThread> oldSet = new HashSet<DebugThread>();
		HashSet<DebugThread> newSet = new HashSet<DebugThread>();
		HashSet<DebugThread> survived = new HashSet<DebugThread>();
		HashSet<DebugThread> died; HashSet<DebugThread> added;
		HashSet<DebugThread> stuck = new HashSet<DebugThread>();

		HashMap<String, DebugThread> oldForInspectionAndCompare = new HashMap<String, DebugThread>();

		for (DebugThread dt:older.threads){
			oldSet.add(dt);
			oldForInspectionAndCompare.put(dt.getId(), dt);
		}
		for (DebugThread dt:newer.threads){
			newSet.add(dt);
			DebugThread oldThread = oldForInspectionAndCompare.get(dt.getId());
			if (oldThread!=null && oldThread.sameState(dt))
				stuck.add(oldThread);
			if (oldSet.contains(dt)){
				oldSet.remove(dt);
				survived.add(dt);
			}
		}

		died = oldSet;

		for (DebugThread dt:older.threads){
			if (newSet.contains(dt)){
				newSet.remove(dt);
			}
		}
		added = newSet;

		System.out.println("Survived "+survived.size()+", Died: "+died.size()+" Born: "+added.size()+", Stuck: "+stuck.size());
		System.out.println("=== DIED: ");
		for (Iterator<DebugThread> it = died.iterator(); it.hasNext(); ){
			System.out.println(it.next());
		}

		System.out.println("=== BORN: ");
		for (Iterator<DebugThread> it = added.iterator(); it.hasNext(); ){
			System.out.println(it.next());
		}

		System.out.println("=== STUCK: ");
		for (Iterator<DebugThread> it = stuck.iterator(); it.hasNext(); ){
			System.out.println(it.next());
		}
	}

	private static ThreadDump readStackTrace(String name) throws Exception{
		FileReader fReader = new FileReader(name);
		LineNumberReader reader = new LineNumberReader(fReader);

		String ts = reader.readLine();
		String desc = reader.readLine();
		reader.readLine();

		ThreadDump ret = new ThreadDump();
		ret.setDateString(ts);

		String line = null;
		int tc = 0;
		int lines = 0;
		boolean inThread = false;
		DebugThread current = null;
		while((line = reader.readLine())!=null){
			lines++;
			if (line.startsWith("JNI global references"))
				continue;
			if (line.length()==0){
				if (inThread){
					inThread = false;
				}else{
					//skip;
				}
			}else{
				if (!inThread){
					tc++;
					inThread = true;
					current = new DebugThread();
					ret.addThread(current);
					int nameStart = line.indexOf('"');
					int nameEnd   = line.lastIndexOf('"');
					try{
						current.setName(line.substring(nameStart+1, nameEnd));
						String otherStuff = line.substring(nameEnd+1);
						//System.out.println("OT "+otherStuff);
						if (otherStuff.indexOf("daemon")!=-1)
							current.setDaemon(true);
						String tt[] = StringUtils.tokenize(otherStuff, ' ');
						for (int t=0; t<tt.length; t++){
							if (tt[t].startsWith("tid")){
								String tid[] = StringUtils.tokenize(tt[t], '=');
								current.setId(tid[1]);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("Line: "+line);
					}
					//System.out.println(line);
				}else{
					current.addCall(line);
				}
			}
		}

		System.out.println("read "+tc+" threads in "+lines+" lines.");

		return ret;
	}

	public static class DebugThread{
		private String name;
		private String id;
		private List<String> calls = new ArrayList<String>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isDaemon() {
			return daemon;
		}

		public void setDaemon(boolean daemon) {
			this.daemon = daemon;
		}

		private boolean daemon;

		public void addCall(String call){
			calls.add(call);
		}

		public static String DUMP_FILTER[] = {
				"org.apache",
				"java.lang",
				"java.util",
				"java.",
				"org.jboss",
				"sun.reflect",
				"javax.faces",
				"sun.nio",
				"org.richfaces",
				"com.sun",


		};
		public void dumpOutShortend() {
			for (int i=0; i<calls.size(); i++){
				String call = calls.get(i);
				if (i<5){
					System.out.println(call);
				}else{
					boolean mayPass = true;
					for (int t = 0; t<DUMP_FILTER.length; t++){
						if (mayPass && call.trim().startsWith(("at " + DUMP_FILTER[t])))
							mayPass=false;
					}
					if (mayPass)
						System.out.println(call);
				}
			}
		}

		@Override public String toString(){
			return getName()+" (tid: "+getId()+" ) "+calls.size()+" calls";
		}

		@Override public boolean equals(Object o){
			return o instanceof DebugThread && id.equals(((DebugThread)o).id);
		}

		public int hashCode(){
			return id.hashCode();
		}

		public boolean sameState(DebugThread otherThread){
			if (!equals(otherThread))
				return false;
			if (!(calls.size()==otherThread.calls.size()))
				return false;
			for (int i=0; i<calls.size(); i++)
				if (! calls.get(i).equals(otherThread.calls.get(i)))
					return false;
			return true;
		}
	}

	public static class ThreadDump{
		public String getDateString() {
			return dateString;
		}

		public void setDateString(String dateString) {
			this.dateString = dateString;
		}

		private String dateString;
		private List<DebugThread> threads = new ArrayList<DebugThread>();

		public void addThread(DebugThread t){
			threads.add(t);
		}

		public String toString(){
			return "ThreadDump @ "+dateString+" "+threads.size()+" threads.";
		}

		public int getTotalThreadCount(){
			return threads==null ? 0 : threads.size();
		}

		public int getDaemonThreadCount(){
			int ret = 0;
			for (DebugThread dt : threads){
				if (dt.isDaemon())
					ret++;
			}
			return ret;
		}

		public void dumpOut(){
			for (DebugThread dt : threads){
				System.out.println(dt);
			}
		}
		public void dumpOutShortened(){
			for (DebugThread dt : threads){
				System.out.println(dt);
				dt.dumpOutShortend();
				System.out.println();
			}
		}
	}
}

