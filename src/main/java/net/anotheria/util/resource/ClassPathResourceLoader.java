package net.anotheria.util.resource;

import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

public class ClassPathResourceLoader implements ResourceLoader{
	
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(ClassPathResourceLoader.class);
	
	private ClassLoader getClassPathLoader(){
		return getClass().getClassLoader();
	}
	
	@Override
	public boolean isAvailable(String fileName){
		//ensure an exception is thrown if we are not file.
		ClassLoader myLoader = getClassPathLoader();

		URL u = myLoader.getResource(fileName);
		return u!=null;
	}

	@Override
	public long getLastChangeTimestamp(String fileName){
		//ensure an exception is thrown if we are not file.
		ClassLoader myLoader = getClassPathLoader();

		URL u = myLoader.getResource(fileName);
		if (u==null){
			throw new IllegalArgumentException("File: "+fileName+" doesn't exists (URL is null)");
		}
		
		File f = new File(u.getFile());
		log.debug("Checking timestamp for file: {}", f.getAbsolutePath());
		long ret =  f.lastModified();
		log.debug("File {}. Last modified: {}", f.getAbsolutePath(), NumberUtils.makeISO8601TimestampString(ret));
		return ret;
	}

	@Override
	public String getContent(String fileName) {
		//ensure an exception is thrown if we are not file.
		ClassLoader myLoader = getClassPathLoader();

		URL u = myLoader.getResource(fileName);
		if (u == null) {
			throw new IllegalArgumentException("File: " + fileName + " doesn't exists (URL is null)");
		}

		File f = new File(u.getFile());
		try (Reader reader = new BufferedReader(new FileReader(f));) {
			StringBuilder ret = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1)
				ret.append((char) c);
			return ret.toString();
		} catch (IOException e) {
			log.error("getContent("+fileName+ ')', e);
			throw new RuntimeException("can't read source: " + fileName, e);
		}
	}

}
