package net.anotheria.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * TODO Please remain lrosenberg to comment IOUtils.java
 * @author lrosenberg
 * @created on Feb 8, 2005
 */
public class IOUtils {
    
	public static final byte[] readFileAtOnce(File file) throws IOException{
        FileInputStream fIn = new FileInputStream(file);
        return readFileAtOnce(fIn);		
	}
	
	public static final byte[] readFileAtOnce(String filename) throws IOException{
        FileInputStream fIn = new FileInputStream(filename);
        return readFileAtOnce(fIn);
    }
	
	private static final byte[] readFileAtOnce(FileInputStream fIn) throws IOException{
		byte[] ret = new byte[fIn.available()];
        fIn.read(ret);
        fIn.close();
        return ret;
	}
    
    public static final String readFileAtOnceAsString(String filename) throws IOException{
        return new String(readFileAtOnce(filename));
    }
    
    public static final String readFileAtOnceAsString(File file) throws IOException{
        return new String(readFileAtOnce(file));
    }
    
    /**
     * Instead of reading the file at once, reads the file by reading 2K blocks. Useful for reading
     * special files, where the size of the file isn't determinable, for example /proc/xxx files on linux.
     * @param filename
     * @return
     * @throws IOException
     */
    public static final String readFileBufferedAsString(String filename) throws IOException{
        StringBuffer result = new StringBuffer();
        char[] buffer = new char[2048];
        FileReader in = new FileReader(filename);
        int len = 0;
        do {
         len = in.read(buffer);
         if(len > 0)
          result.append(buffer,0, len);
        } while(len > 0);
        in.close();
        return result.toString();
    }

    public static void main(String a[]) throws Exception{
        String s = readFileAtOnceAsString("/opt/projects/datingr4p1/build/local.properties");
        System.out.println(s.length()+" bytes read");
        System.out.println(s);
        
    }
    
    public static String readlineFromStdIn() throws IOException{
    	StringBuilder ret = new StringBuilder();
    	int c;
    	while( (c=System.in.read())!='\n' && c!=-1){
    		if (c!='\r')
    			ret.append((char)c);
    	}
    	return ret.toString();
    }
}

