package net.anotheria.util.io;

/**
 * This entry represents a single file in the directory dif.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class FileEntry extends Entry{
	/**
	 * The size of the file.
	 */
	private long size;
	/**
	 * Creates a new file entry with a name and file size.
	 *
	 * @param aName a {@link java.lang.String} object.
	 * @param aSize a long.
	 */
	public FileEntry(String aName, long aSize){
		super(aName);
		size = aSize;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * Returns the size of the file.
	 */
	@Override
    public long getSize(){
		return size;
	}
}
