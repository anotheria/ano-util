package net.anotheria.util.resource;

import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * <p>FileResourceLoader class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class FileResourceLoader implements ResourceLoader{
	
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(FileResourceLoader.class);
	
	/** {@inheritDoc} */
	@Override
	public boolean isAvailable(String fileName){
		File f = new File(fileName);
		return f.exists();
	}

	/** {@inheritDoc} */
	@Override
	public long getLastChangeTimestamp(String fileName){
		File f = new File(fileName);
		log.debug("Checking timestamp for file: {}", f.getAbsolutePath());
		long ret =  f.lastModified();
		log.debug("File {} last modified is: {}", f.getAbsolutePath(), NumberUtils.makeISO8601TimestampString(ret));
		return ret;
	}
	
	/** {@inheritDoc} */
	@Override
	public String getContent(String fileName){
		File f = new File(fileName);
		try (Reader reader = new BufferedReader(new FileReader(f))) {
			StringBuilder ret = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1)
				ret.append((char) c);
			return ret.toString();
		} catch (IOException e) {
			log.error("getContent(" + fileName + ')', e);
			throw new RuntimeException("can't read source: " + fileName, e);
		}
	}
	
	/**
	 * <p>main.</p>
	 *
	 * @param args a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public static void main(String... args) throws Exception{
		ResourceLoader loader = new FileResourceLoader();
		System.out.println("Current base dir: " + new File("").getAbsolutePath());
		String testFile = "test.txt";
		System.out.println("File exists: " + loader.isAvailable(testFile));
		System.out.println("Creating file...");
		System.out.println("File lastchange: " + loader.getLastChangeTimestamp(testFile));
		File f = new File(testFile);
		FileWriter fw = new FileWriter(f);
		fw.append("MUMU");
		fw.close();
		System.out.println("File exists: " + loader.isAvailable(testFile));
		System.out.println("File content: " + loader.getContent(testFile));
		System.out.println("File lastchange: " + loader.getLastChangeTimestamp(testFile));
		f.delete();
	}
}
