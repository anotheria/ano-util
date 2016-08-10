package net.anotheria.util.cvsutil;

import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>CVSRepositoryChanger class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
@Deprecated
public class CVSRepositoryChanger {
	
	private static final Logger STATIC_LOGGER = LoggerFactory.getLogger(CVSRepositoryChanger.class.getName());

	/** Constant <code>TO_REPLACE="lro@"</code> */
	public static final String TO_REPLACE = "lro@";
	/** Constant <code>REPLACE_WITH="lrosenberg@"</code> */
	public static final String REPLACE_WITH = "lrosenberg@";
	
	/**
	 * <p>main.</p>
	 *
	 * @param a a {@link java.lang.String} object.
	 */
	public static void main(String... a){
		String startpath = ".";
		try {
			startpath = a[0];
		} catch(RuntimeException e) {
			STATIC_LOGGER.error(e.getMessage(), e);
		}
		
		proceed(new File(startpath));
		STATIC_LOGGER.info("done, changed: "+changedFiles+" files");
		
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
		STATIC_LOGGER.info("Checking "+f.getAbsolutePath()+ ' ');
		try{	
			FileInputStream fIn = new FileInputStream(f);
			byte[] d = new byte[fIn.available()];
			fIn.read(d);
			String source = new String(d);
			String dest = source;
			while(dest.contains(TO_REPLACE))
				dest = StringUtils.replace(dest, TO_REPLACE, REPLACE_WITH);
			
			if (!dest.equals(source)){
				STATIC_LOGGER.info("changed");
				STATIC_LOGGER.info("Saving "+f.getAbsolutePath());
				FileOutputStream fOut = new FileOutputStream(f);
				fOut.write(dest.getBytes());
				fOut.close();
				changedFiles++;
			}else{
				STATIC_LOGGER.info("skipped.");
			}
			
		} catch(IOException e) {
			STATIC_LOGGER.error(e.getMessage(), e);
		}
	}
	
	private static void proceedDir(File f){
		File[] files = f.listFiles();
		for (File aFile : files) {
			proceed(aFile);
		}
	}
	
}
