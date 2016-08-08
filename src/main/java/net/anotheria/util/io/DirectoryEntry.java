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
	 */
	public DirectoryEntry(String name){
		super(name);
		entries = new ArrayList<>();
	}
	/**
	 * Adds a sub entry.
	 */
	public void addEntry(Entry anEntry){
		entries.add(anEntry);
	}
	/**
	 * Returns the size of the entry.
	 */
	@Override
	public long getSize(){
		long ret = 0;
		for (Entry e : entries)
			ret += e.getSize();
		return ret;
	}

}
