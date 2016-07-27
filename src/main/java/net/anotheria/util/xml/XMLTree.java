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
	 */
	public XMLNode getRoot() {
		return root;
	}

	public void setRoot(XMLNode aRoot) {
		this.root = aRoot;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String aVersion) {
		this.version = aVersion;
	}

	/**
	 * Returns the encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * Sets the encoding.
	 * @param aEncoding the encoding value.
	 */
	public void setEncoding(String aEncoding) {
		this.encoding = aEncoding;
	}
	
	/**
	 * Writes the contents of the xml tree as xml to the given output writer.
	 * @param aWriter the writer to write the output to.
	 * @throws IOException rethrown if thrown by the writer.
	 */
	public void write(OutputStreamWriter aWriter) throws IOException{
		aWriter.write("<?xml version="+XMLHelper.quote(version)+" encoding="+XMLHelper.quote(encoding)+"?>\n");
		if (root!=null)
			root.write(aWriter, 0);
	}

}
