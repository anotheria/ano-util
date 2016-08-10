package net.anotheria.util.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a directory in the dif.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class DirectoryEntry extends Entry{
	/**
	 * Subentries.
	 */
	private List<Entry> entries;
	/**
	 * Creates a new directory entry.
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public DirectoryEntry(String name){
		super(name);
		entries = new ArrayList<>();
	}
	/**
	 * Adds a sub entry.
	 *
	 * @param anEntry a {@link net.anotheria.util.io.Entry} object.
	 */
	public void addEntry(Entry anEntry){
		entries.add(anEntry);
	}
	/**
	 * {@inheritDoc}
	 *
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
