package net.anotheria.util.cvsutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import net.anotheria.util.StringUtils;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class CVSRepositoryChanger {
	
	private static final Logger STATIC_LOGGER = Logger.getLogger(CVSRepositoryChanger.class.getName());
	
	//public static final String TO_REPLACE = "213.61.151.32";
	//public static final String REPLACE_WITH = "cvs.anotheria.net";

	public static final String TO_REPLACE = "lro@";
	public static final String REPLACE_WITH = "lrosenberg@";
	
	public static void main(String a[]){
		String startpath = ".";
		try {
			startpath = a[0];
		} catch(Exception e) {
			STATIC_LOGGER.error(e.getMessage(), e);
		}
		
		proceed(new File(startpath));
		System.out.println("done, changed: "+changedFiles+" files");
		
	}
	
	private static void proceed(File f){
		if (f.isDirectory())
			proceedDir(f); 
		else
			proceedFile(f);
	}
	static int changedFiles;
	private static void proceedFile(File f){
		if (!f.getName().equals("Root"))
			return;		
		System.out.print("Checking "+f.getAbsolutePath()+" ");
		try{	
			FileInputStream fIn = new FileInputStream(f);
			byte d[] = new byte[fIn.available()];
			fIn.read(d);
			String source = new String(d);
			String dest = source;
			while(dest.indexOf(TO_REPLACE)!=-1)
				dest = StringUtils.replace(dest, TO_REPLACE, REPLACE_WITH);
			
			if (!dest.equals(source)){
				System.out.println("changed");
				System.out.println("Saving "+f.getAbsolutePath());
				FileOutputStream fOut = new FileOutputStream(f);
				fOut.write(dest.getBytes());
				fOut.close();
				changedFiles++;
			}else{
				System.out.println("skipped.");
			}
			
		} catch(IOException e) {
			e.printStackTrace();		
		}
	}
	
	private static void proceedDir(File f){
		File files[] = f.listFiles();
		for (int i=0; i<files.length; i++){
			File aFile = files[i];
			proceed(aFile);
		}
	}
	
}
