package net.anotheria.util.tools;

import java.io.FileOutputStream;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;

/**
 * A simple command line programm that removes all \r from the file submitted as argument.
 * @author lrosenberg
 *
 */
public final class RemoveNewLines {
	/**
	 * Removes empty lines from the file at a[0]
	 * @param a do you need a comment on that?
	 * @throws Exception if something happens.
	 */
	public static void main(String[] a) throws Exception{
		String s = IOUtils.readFileAtOnceAsString(a[0]);
		s = StringUtils.removeChar(s, '\r');
		FileOutputStream fOut = new FileOutputStream(a[0]);
		fOut.write(s.getBytes());
		fOut.close();
	}
	
	private RemoveNewLines(){}
}