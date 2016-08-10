package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * <p>XMLWriter class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class XMLWriter {
	
	/**
	 * <p>write.</p>
	 *
	 * @param tree a {@link net.anotheria.util.xml.XMLTree} object.
	 * @param stream a {@link java.io.OutputStream} object.
	 * @return a {@link java.io.OutputStreamWriter} object.
	 * @throws java.io.IOException if any.
	 */
	public static OutputStreamWriter write(XMLTree tree, OutputStream stream) throws IOException{
		OutputStreamWriter writer = new OutputStreamWriter(stream, tree.getEncoding());
		tree.write(writer);
		writer.flush();
		return writer;
	}
}
