package net.anotheria.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This utility class copies recursively everything from source directory to target directory.
 * @author lrosenberg
 */
public class CopyDirContents {

	private static final Logger log = LoggerFactory.getLogger(CopyDirContents.class);

	/**
	 * Buffer used for copies.
	 */
	private static byte[] buffer = new byte[1024 * 1024];
	/**
	 * Counter for files and directories.
	 */
	private static int files = 0, dirs = 0;
	/**
	 * Byte counter.
	 */
	private static long bytes = 0;
	/**
	 * Start time.
	 */
	private static long time = System.currentTimeMillis();
	/**
	 * Number of skiped files.
	 */
	private static int skipped = 0;

	public static void main(String... a) throws IOException{
		File src = new File("/storage/BAK_EXT_DISK");
		File dest = new File("/media/WD Passport");
		
		copy(src, dest);
		log.debug("Finished: ");
		printInfo();
	}
	
	/**
	 * Prints current copy status.
	 */
	private static void printInfo(){
		long now = System.currentTimeMillis();
		long duration = now - time;
		double throughtput = (double)bytes/duration*1000;
		double mbs = throughtput/1024/1024;
		log.info("Copied dirs: "+dirs+", files: "+files+", bytes: "+bytes+" in "+duration+", throughtput: "+throughtput+" bytes/second, "+mbs+" MB/s, skiped: "+skipped);
	}
	
	public static void copy(File src, File dest) throws IOException{
		if (src.isDirectory())
			copyDir(src, dest);
		else
			copyFile(src, dest);
	}
	
	/**
	 * Copies two directories.
	 */
	private static void copyDir(File src, File dest) throws IOException{
		dirs++;
		//System.out.println("Copying dir: "+src.getAbsolutePath()+" to "+dest.getAbsolutePath());
		if (!dest.exists()){
			boolean res = dest.mkdir();
			if (!res)
				throw new RuntimeException("Can't create directory "+dest.getAbsolutePath());
		}
		File[] ff = src.listFiles();
		for (File f : ff){
			copy(f, new File(dest.getAbsolutePath()+File.separatorChar+f.getName()));
		}
	}

	/**
	 * Copies two files.
	 */
	private static void copyFile(File src, File dest) {
		files++;
		if (files/1000*1000==files)
			printInfo();
		//System.out.println("Copying file: "+src.getAbsolutePath()+" to "+dest.getAbsolutePath());
		if (dest.exists()){
			//System.out.println("File exists, "+dest.getAbsolutePath()+", skipping.");
			skipped++;
			if (skipped/1000*1000==skipped)
				printInfo();
			return;
		}
		log.info("Copying file: "+src.getAbsolutePath()+" to "+dest.getAbsolutePath());
		try(FileInputStream fIn = new FileInputStream(src);
		FileOutputStream fOut = new FileOutputStream(dest)) {
			copy(fIn, fOut);
		} catch(IOException e){
			log.error("Couldn't copy "+src.getAbsolutePath()+" to "+dest.getAbsolutePath(), e);
		}
	}
	
	/**
	 * Copies from stream to stream.
	 */
	private static void copy(FileInputStream src, FileOutputStream dest) throws IOException{
		while(src.available()>0){
			int copied = src.read(buffer);
			dest.write(buffer, 0, copied);
			bytes+= copied;
		}
	}
}
