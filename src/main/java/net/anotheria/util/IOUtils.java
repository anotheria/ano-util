package net.anotheria.util;

import net.anotheria.util.io.UnicodeReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.in;

/**
 * Utils for input output.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public final class IOUtils {

	/**
	 * Reads the contents of the file at once and returns the byte array.
	 *
	 * @param file a {@link java.io.File} object.
	 * @return an array of byte.
	 * @throws java.io.IOException if any.
	 */
	public static byte[] readFileAtOnce(File file) throws IOException{
        FileInputStream fIn = new FileInputStream(file);
        return readFileAtOnce(fIn);		
	}
	
	/**
	 * Reads the contents of the file at once and returns the byte array.
	 *
	 * @param filename name of the file.
	 * @return an array of byte.
	 * @throws java.io.IOException if any.
	 */
	public static byte[] readFileAtOnce(String filename) throws IOException{
        FileInputStream fIn = new FileInputStream(filename);
        return readFileAtOnce(fIn);
    }
	
	/**
	 * Reads the contents of the fileinputstream. (Why not an InputStream btw?).
	 */
	private static byte[] readFileAtOnce(FileInputStream fIn) throws IOException{
		byte[] ret = new byte[fIn.available()];
        fIn.read(ret);
        fIn.close();
        return ret;
	}
    
    /**
     * Reads a file and returns the contents as string.
     *
     * @param filename a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readFileAtOnceAsString(String filename) throws IOException{
        return new String(readFileAtOnce(filename));
    }
    
    /**
     * Reads a file and returns the contents as string.
     *
     * @param file a {@link java.io.File} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readFileAtOnceAsString(File file) throws IOException{
        return new String(readFileAtOnce(file));
    }
    
    /**
     * Instead of reading the file at once, reads the file by reading 2K blocks. Useful for reading
     * special files, where the size of the file isn't determinable, for example /proc/xxx files on linux.
     *
     * @param filename a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readFileBufferedAsString(String filename) throws IOException{
    	try (FileReader in = new FileReader(filename)){
        	StringBuilder result = new StringBuilder();
	        char[] buffer = new char[2048];

	        int len;
	        do {
	         len = in.read(buffer);
	         if(len > 0)
	          result.append(buffer,0, len);
	        } while(len > 0);
	        return result.toString();
        }
    }
    
    /**
     * <p>readInputStreamBufferedAsString.</p>
     *
     * @param in a {@link java.io.InputStream} object.
     * @param charset a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readInputStreamBufferedAsString(InputStream in, String charset) throws IOException{
		try (BufferedReader reader = new BufferedReader(new UnicodeReader(in, charset))) {
			StringBuilder result = new StringBuilder();
			char[] cbuf = new char[2048];
			int read;
			while((read = reader.read(cbuf)) > 0)
				result.append(cbuf, 0, read);
			return result.toString();
		}
    }
    
    /**
     * <p>readFileBufferedAsString.</p>
     *
     * @param file a {@link java.io.File} object.
     * @param charset a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readFileBufferedAsString(File file, String charset) throws IOException{
		return readInputStreamBufferedAsString(new FileInputStream(file), charset);
    }
    
    /**
     * Reads a line from standard input.
     *
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public static String readlineFromStdIn() throws IOException{
    	StringBuilder ret = new StringBuilder();
    	int c;
    	while( (c= in.read())!='\n' && c!=-1){
    		if (c!='\r')
    			ret.append((char)c);
    	}
    	return ret.toString();
    }
    
    /**
     * Closes Closeable instance ignoring IOException. Should be called from a finally block whenever Closeable is used.
     *
     * @param closeable to close
     */
    public static void closeIgnoringException(Closeable closeable){
    	if(closeable!=null)
			try {
				closeable.close();
			} catch (IOException ignored) {
				//We can do nothing if on close failure
			}
    }
	/**
	 * <p>readBytes.</p>
	 *
	 * @param in a {@link java.io.InputStream} object.
	 * @return an array of byte.
	 * @throws java.io.IOException if any.
	 */
	public static byte[] readBytes(InputStream in) throws IOException {

		try (final ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = in.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			return buffer.toByteArray();
		}
	}
}

