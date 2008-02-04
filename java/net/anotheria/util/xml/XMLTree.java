package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class XMLTree {
	
	private String version;
	private String encoding;
	
	private XMLNode root;
	
	public XMLTree(){
		version = "1.0";
		encoding = "utf-8";
	}

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

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void write(OutputStreamWriter writer) throws IOException{
		writer.write("<?xml version="+XMLHelper.quote(version)+" encoding="+XMLHelper.quote(encoding)+"?>");
		if (root!=null)
			root.write(writer);
	}

}
