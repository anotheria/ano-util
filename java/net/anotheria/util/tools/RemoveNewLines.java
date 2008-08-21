package net.anotheria.util.tools;

import java.io.FileOutputStream;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;

public class RemoveNewLines {
	public static void main(String a[]) throws Exception{
		String s = IOUtils.readFileAtOnceAsString(a[0]);
		s = StringUtils.removeChar(s, '\r');
		FileOutputStream fOut = new FileOutputStream(a[0]);
		fOut.write(s.getBytes());
		fOut.close();
	}
}
