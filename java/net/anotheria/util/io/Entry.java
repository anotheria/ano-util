package net.anotheria.util.io;

public abstract class Entry {
	
	private String name;
	
	protected Entry(String aName){
		name = aName;
	}
	
	public String getName(){ return name; }
	
	public void setName(String aName){ name = aName; }
	
	public abstract long getSize();

	public String toOut(int tab){
		return getTab(tab).append(getName()).toString();
	}
	
	protected StringBuilder getTab(int number){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<number; i++)
			ret.append('\t');
		return ret;
	}
	
	public String toString(){
		return toOut(0);
	}
}
