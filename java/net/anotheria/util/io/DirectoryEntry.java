package net.anotheria.util.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a directory in the dif.
 * @author lrosenberg
 */
public class DirectoryEntry extends Entry{
	/**
	 * Subentries.
	 */
	private List<Entry> entries;
	/**
	 * Creates a new directory entry.
	 * @param name
	 */
	public DirectoryEntry(String name){
		super(name);
		entries = new ArrayList<Entry>();
	}
	/**
	 * Adds a sub entry.
	 * @param anEntry
	 */
	public void addEntry(Entry anEntry){
		entries.add(anEntry);
	}
	/**
	 * Returns the size of the entry.
	 */
	public long getSize(){
		long ret = 0;
		for (Entry e : entries)
			ret += e.getSize();
		return ret;
	}
	
	public String toOut(int tab){
		return super.toOut(tab);
	}
}
