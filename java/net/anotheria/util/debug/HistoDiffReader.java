package net.anotheria.util.debug;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.02.13 19:15
 */

import net.anotheria.util.BasicComparable;
import net.anotheria.util.IOUtils;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import net.anotheria.util.sorter.IComparable;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HistoDiffReader {


	public static void main(String a[]) throws Exception{

		String newP = "/Users/another/Documents/Projects/AYN/CRASH/2013-02-05/histo3.txt";
		String oldP = "/Users/another/Documents/Projects/AYN/CRASH/2013-02-05/histo2.txt";

		compare(oldP, newP);
	}

	public static final void compare(String oldPath, String newPath) throws Exception{
		Histogramm oldH = readHistogram(oldPath);
		Histogramm newH = readHistogram(newPath);

		int oldObjects = oldH.getNumberOfObjects();
		int newObjects = newH.getNumberOfObjects();

		long oldMemory = oldH.getAmountOfMemory();
		long newMemory = newH.getAmountOfMemory();

		System.out.println("Comparing "+oldPath+" with "+newPath);
		System.out.println("Number of objects increased by "+NumberUtils.getDotedNumber(newObjects-oldObjects));
		System.out.println("Usage of memory   increased by "+NumberUtils.getDotedNumber(newMemory-oldMemory));
		System.out.println("number of classes increased by "+NumberUtils.getDotedNumber(newH.getEntryCount()-oldH.getEntryCount()));

		List<HistogramEntry> newEntries = newH.getEntries();
		List<HistogramEntry> oldEntries = oldH.getEntries();

		List<String> classNames = new ArrayList<String>();

		HashMap<String,HistogramEntry> map = new HashMap<String,HistogramEntry>(newEntries.size());
		//first put all new entries
		for (HistogramEntry entry : newEntries){
			map.put(entry.getClassName(), entry);
			classNames.add(entry.getClassName());
		}

		for (HistogramEntry entry : oldEntries){
			HistogramEntry ne = map.get(entry.className);
			if (ne==null){
				//this is an object that vanished from heap
				entry.negate();
				map.put(entry.getClassName(), entry);
				classNames.add(entry.getClassName());
			}else{
				ne.reduceBy(entry);
			}
		}

		int unchangedClassCount = 0;
		//sanity check
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(map.keySet());
		for (String key : keys){
			HistogramEntry entry = map.get(key);
			if (entry.getInstanceCount()==0 && entry.getBytes()==0){
				unchangedClassCount++;
				map.remove(key);
			}
			if (entry.getInstanceCount()!=0 && entry.getBytes()==0){
				System.out.println("SANITY CHECK FAILED ON "+entry);
			}
		}

		System.out.println("Unchanged classes: "+unchangedClassCount);
		System.out.println("Changed classes: "+map.size());

		long sumPosMem = 0;
		int sumPosInstance = 0;

		//--
		List<HistogramEntry> result = new ArrayList<HistoDiffReader.HistogramEntry>();
		result.addAll(map.values());
		result = StaticQuickSorter.sort(result, new HistogrammEntrySortType());
		for (HistogramEntry e : result){
			System.out.println(e.toDetails());
			if (e.bytes>0)
				sumPosMem+= e.bytes;
			if (e.instanceCount>0)
				sumPosInstance+= e.instanceCount;
		}

		System.out.println("Memory increased by "+NumberUtils.getDotedNumber(sumPosMem)+", instance increased by "+NumberUtils.getDotedNumber(sumPosInstance));


	}

	public static final Histogramm readHistogram(String path) throws Exception{
		Histogramm h = new Histogramm(path);
		String content = IOUtils.readFileAtOnceAsString(path);
		String tt[] = StringUtils.tokenize(content, '\n');
		for (String line : tt){
			if (line == null)
				continue;
			line = line.trim();
			if (line.equals(""))
				continue;

			if (line.startsWith("num"))
				continue;
			if (line.startsWith("Total"))
				continue;
			if (line.startsWith("---"))
				continue;

			try{
				HistogramEntry entry = new HistogramEntry(line);
//				if (entry.getClassName().contains("[Ljava.lang.ref.SoftReference;")){
//					System.out.println(entry);
//				}
				h.addEntry(entry);
			}catch(NumberFormatException e){
				e.printStackTrace();
				System.out.println("FAILED "+line);
			}

		}
		return h;
	}

	public static class Histogramm{
		private ArrayList<HistogramEntry> entries;
		private String name;

		public Histogramm(String aName) {
			name = aName;
			entries = new ArrayList<HistoDiffReader.HistogramEntry>();
		}

		public void addEntry(HistogramEntry toAdd){
			entries.add(toAdd);
		}

		public String toString(){
			return name + " with "+entries.size()+" entries "+NumberUtils.getDotedNumber(getNumberOfObjects())+" objects, "+NumberUtils.getDotedNumber(getAmountOfMemory())+" bytes.";
		}

		public int getNumberOfObjects(){
			int ret = 0;
			for (HistogramEntry e : entries)
				ret += e.instanceCount;
			return ret;
		}

		public long getAmountOfMemory(){
			long ret = 0;
			for (HistogramEntry e : entries)
				ret += e.bytes;
			return ret;
		}

		public int getEntryCount(){
			return entries.size();
		}

		public List<HistogramEntry> getEntries(){
			return entries;
		}

	}


	public static class HistogramEntry implements IComparable{
		private String className;
		private int position;
		private long bytes;
		private int instanceCount;

		public int getInstanceCount() {
			return instanceCount;
		}

		public void negate() {
			bytes*=-1;
			instanceCount*=-1;
		}

		public void reduceBy(HistogramEntry anotherEntry){
			bytes -= anotherEntry.bytes;
			instanceCount -= anotherEntry.instanceCount;
		}

		public void setInstanceCount(int instanceCount) {
			this.instanceCount = instanceCount;
		}

		public HistogramEntry(String line){
			String t[] = StringUtils.tokenize(line, ' ');
			List<String> tt = new ArrayList<String>();
			tt.addAll(Arrays.asList(t));
			int index = -1;
			while( (index=tt.indexOf(""))!=-1)
				tt.remove(index);
			position = Integer.parseInt(StringUtils.removeChar(tt.get(0), ':').trim());
			instanceCount = Integer.parseInt(tt.get(1).trim());
			bytes = Long.parseLong(tt.get(2).trim());
			className = tt.get(3).trim();
		}

		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		public long getBytes() {
			return bytes;
		}
		public void setBytes(int bytes) {
			this.bytes = bytes;
		}

		@Override public String toString(){
			String ret = "";
			ret += NumberUtils.itoa(position, 5);
			ret += " ";
			ret += NumberUtils.getDotedNumber(instanceCount);
			ret += " ";
			ret += NumberUtils.getDotedNumber(bytes);
			ret += " ";
			ret += className;
			return ret;
		}

		public String toDetails(){
			String ret = "";
			ret += NumberUtils.itoa(position, 5);
			ret += "\t";
			ret += NumberUtils.getDotedNumber(instanceCount);
			ret += "\t";
			ret += NumberUtils.getDotedNumber(bytes);
			ret += "\t";
			ret += className;
			return ret;
		}

		@Override
		public int compareTo(IComparable o, int method) {
			HistogramEntry anotherEntry = (HistogramEntry)o;
			switch(method){
				case HistogrammEntrySortType.SORT_BY_COUNT:
					return BasicComparable.compareInt(getInstanceCount(), anotherEntry.getInstanceCount());
				case HistogrammEntrySortType.SORT_BY_MEMORY:
					return BasicComparable.compareLong(getBytes(), anotherEntry.getBytes());
				default:
					throw new AssertionError("Unknown method "+method);
			}
		}

	}

	static class HistogrammEntrySortType extends SortType{
		public static final int SORT_BY_MEMORY = 1;
		public static final int SORT_BY_COUNT  = 2;
		public HistogrammEntrySortType(){
			super(SORT_BY_MEMORY, DESC);
		}
	}

}
