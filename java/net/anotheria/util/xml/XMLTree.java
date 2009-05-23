package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Represents an XML Dom. Used for export/import data to and from xml.
 * @author lrosenberg
 *
 */
public class XMLTree {
	/**
	 * The version attribute. If not specified 1.0 is assumed.
	 */
	private String version;
	/**
	 * The encoding attribute. If not specified utf-8 is assumed.
	 */
	private String encoding;
	
	/**
	 * The root node.
	 */
	private XMLNode root;
	/**
	 * Creates a new, empty tree.
	 */
	public XMLTree(){
		version = "1.0";
		encoding = "utf-8";
	}

	/**
	 * Returns the root node.
	 * @return 
	 */
	public XMLNode getRoot() {
		return root;
	}

	public void setRoot(XMLNode root) {
		this.root = root;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns the encoding.
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Sets the encoding.
	 * @param encoding the encoding value.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * Writes the contents of the xml tree as xml to the given output writer.
	 * @param writer the writer to write the output to.
	 * @throws IOException rethrown if thrown by the writer.
	 */
	public void write(OutputStreamWriter writer) throws IOException{
		writer.write("<?xml version="+XMLHelper.quote(version)+" encoding="+XMLHelper.quote(encoding)+"?>\n");
		if (root!=null)
			root.write(writer, 0);
	}

}
