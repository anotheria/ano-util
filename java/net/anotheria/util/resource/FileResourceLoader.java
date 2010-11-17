package net.anotheria.util.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import net.anotheria.util.IOUtils;
import net.anotheria.util.NumberUtils;

import org.apache.log4j.Logger;

public class FileResourceLoader implements ResourceLoader{
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(FileResourceLoader.class);
	
	@Override
	public boolean isAvailable(String fileName){
		File f = new File(fileName);
		return f.exists();
	}

	@Override
	public long getLastChangeTimestamp(String fileName){
		File f = new File(fileName);
		log.debug("Checking timestamp for file: "+f.getAbsolutePath());
		long ret =  f.lastModified();
		log.debug("file "+f.getAbsolutePath()+" last modified is: "+NumberUtils.makeISO8601TimestampString(ret));
		return ret;
	}
	
	@Override
	public String getContent(String fileName){
		Reader reader = null;
		try{
			File f = new File(fileName);
			reader = new BufferedReader(new FileReader(f));
			StringBuilder ret = new StringBuilder();
			int c ; 
			while((c=reader.read())!=-1)
				ret.append((char)c);
			return ret.toString();
		}catch(IOException e){
			log.error("getContent("+fileName+")", e);
			throw new RuntimeException("can't read source: "+fileName, e);
		}finally{
			IOUtils.closeIgnoringException(reader);
		}
	}
//*/
	
	public static void main(String[] args) throws Exception{
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
