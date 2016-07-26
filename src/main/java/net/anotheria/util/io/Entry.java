package net.anotheria.util.io;

/**
 * Base class for entries of the directory diff.
 * @author lrosenberg
 */
public abstract class Entry {
	/**
	 * Name of the entry.
	 */
	private String name;
	/**
	 * Constructor for the entry.
	 * @param aName
	 */
	protected Entry(String aName){
		name = aName;
	}
	/**
	 * Returns the name.
	 * @return
	 */
	public String getName(){ return name; }
	
	public void setName(String aName){ name = aName; }
	/**
	 * Returns the size of the entry.
	 * @return
	 */
	public abstract long getSize();

	public String toOut(int tab){
        return getTab(tab).append(name).toString();
	}
	
	protected static StringBuilder getTab(int number){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<number; i++)
			ret.append('\t');
		return ret;
	}
	
	@Override public String toString(){
		return toOut(0);
	}
}
