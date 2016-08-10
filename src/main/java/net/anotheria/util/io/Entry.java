package net.anotheria.util.io;

/**
 * Base class for entries of the directory diff.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public abstract class Entry {
	/**
	 * Name of the entry.
	 */
	private String name;
	/**
	 * Constructor for the entry.
	 *
	 * @param aName a {@link java.lang.String} object.
	 */
	protected Entry(String aName){
		name = aName;
	}
	/**
	 * Returns the name.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName(){ return name; }
	
	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param aName a {@link java.lang.String} object.
	 */
	public void setName(String aName){ name = aName; }
	/**
	 * Returns the size of the entry.
	 *
	 * @return a long.
	 */
	public abstract long getSize();

	/**
	 * <p>toOut.</p>
	 *
	 * @param tab a int.
	 * @return a {@link java.lang.String} object.
	 */
	public String toOut(int tab){
        return getTab(tab).append(name).toString();
	}
	
	/**
	 * <p>getTab.</p>
	 *
	 * @param number a int.
	 * @return a {@link java.lang.StringBuilder} object.
	 */
	protected static StringBuilder getTab(int number){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<number; i++)
			ret.append('\t');
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override public String toString(){
		return toOut(0);
	}
}
