package net.anotheria.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyDirContents {

	private static byte[] buffer = new byte[1*1024*1024];
	private static int files = 0, dirs = 0;
	private static long bytes = 0;
	private static long time = System.currentTimeMillis();
	private static int skipped = 0;

	public static void main(String a[]) throws IOException{
		File src = new File("/storage/BAK_EXT_DISK");
		File dest = new File("/media/WD Passport");
		
		copy(src, dest);
		System.out.println("Finished: ");
		printInfo();
	}
	
	private static void printInfo(){
		long now = System.currentTimeMillis();
		long duration = now - time;
		double throughtput = (double)bytes/duration*1000;
		double mbs = throughtput/1024/1024;
		System.out.println("Copied dirs: "+dirs+", files: "+files+", bytes: "+bytes+" in "+duration+", throughtput: "+throughtput+" bytes/second, "+mbs+" MB/s, skiped: "+skipped);
	}
	
	public static void copy(File src, File dest) throws IOException{
		if (src.isDirectory())
			copyDir(src, dest);
		else
			copyFile(src, dest);
	}
	
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

	private static void copyFile(File src, File dest) throws IOException{
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
		System.out.println("Copying file: "+src.getAbsolutePath()+" to "+dest.getAbsolutePath());
		FileInputStream fIn = new FileInputStream(src);
		FileOutputStream fOut = new FileOutputStream(dest);
		copy(fIn, fOut);
		try{
			fIn.close();
		}catch(IOException ignored){}
		try{
			fOut.close();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Couldn't copy "+src.getAbsolutePath()+" to "+dest.getAbsolutePath());
		}
	}
	
	private static void copy(FileInputStream src, FileOutputStream dest) throws IOException{
		while(src.available()>0){
			int bytesToCopy = src.available()<buffer.length ? src.available() : buffer.length;
			int copied = src.read(buffer);
			dest.write(buffer, 0, copied);
			bytes+= copied;
		}
	}
}
