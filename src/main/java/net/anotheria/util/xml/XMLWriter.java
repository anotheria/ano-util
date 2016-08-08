package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class XMLWriter {
	
	public static OutputStreamWriter write(XMLTree tree, OutputStream stream) throws IOException{
		OutputStreamWriter writer = new OutputStreamWriter(stream, tree.getEncoding());
		tree.write(writer);
		writer.flush();
		return writer;
	}
}
