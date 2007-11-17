package net.anotheria.util.io;

import java.util.ArrayList;
import java.util.List;

public class DirectoryEntry extends Entry{
	private List<Entry> entries;
	
	public DirectoryEntry(String name){
		super(name);
		entries = new ArrayList<Entry>();
	}
	
	public void addEntry(Entry anEntry){
		entries.add(anEntry);
	}
	
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
