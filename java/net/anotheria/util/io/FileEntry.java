package net.anotheria.util.io;

public class FileEntry extends Entry{
	private long size;
	
	public FileEntry(String aName, long aSize){
		super(aName);
		size = aSize;
	}
	
	public long getSize(){ return size; }
}
