package net.anotheria.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>DirDiff class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class DirDiff {
	/**
	 * <p>main.</p>
	 *
	 * @param a a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public static void main(String... a) throws IOException{
		String p2 = "/media/WD Passport";
		String p1 = "/storage/BAK_EXT_DISK";
		diff(p1,p2);
	}
	
	/**
	 * Creates a diff between two pathes.
	 *
	 * @param path1 a {@link java.lang.String} object.
	 * @param path2 a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public static void diff(String path1, String path2) throws IOException{
		File d1 = new File(path1);
		if (!d1.exists())
			throw new RuntimeException(path1+" does not exists");
		if (!d1.isDirectory())
			throw new RuntimeException(path1+" is not a directory");

		File d2 = new File(path2);
		
		Entry e1 = createEntry(d1);
		Entry e2 = createEntry(d2);

		System.out.println(e1);
		System.out.println("Size: "+e1.getSize());
		System.out.println(e2);
		System.out.println("Size: "+e2.getSize());
		
		
	}
	
	/**
	 * Creates a new entry. It will be either a FileEntry or a DirectoryEntry.
	 */
	private static Entry createEntry(File path) throws IOException{
		if (path.isDirectory())
			return createDirectoryEntry(path);
		else
			return createFileEntry(path);
	}
	
	/**
	 * Creates a new FileEntry.
	 */
	private static FileEntry createFileEntry(File path) throws IOException{
		FileInputStream fIn = new FileInputStream(path);
		long size = fIn.available();
		fIn.close();
		return new FileEntry(path.getAbsolutePath(), size);
	}
	
	/**
	 * Creates a directory entry.
	 */
	private static DirectoryEntry createDirectoryEntry(File path) throws IOException{
		System.out.println("creating "+path+"...");
		DirectoryEntry ret = new DirectoryEntry(path.getAbsolutePath());
		
		File[] ff = path.listFiles();
		for (File f : ff)
			ret.addEntry(createEntry(f));
		
		return ret;
	}
}
