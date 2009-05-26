package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLNode {
	/**
	 * Subnodes of this node.
	 */
	private List<XMLNode> nodes;
	/**
	 * Attributes of the node.
	 */
	private List<XMLAttribute> attributes;
	
	/**
	 * The name of the node.
	 */
	private String name;
	/**
	 * The content of the node.
	 */
	private String content;
	
	/**
	 * Creates a new XMLNode.
	 * @param aName
	 */
	public XMLNode(String aName){
		name = aName;
	
		attributes = new ArrayList<XMLAttribute>();
		nodes = new ArrayList<XMLNode>();
	}

	
	public List<XMLNode> getNodes() {
		return nodes;
	}
	
	public void setChildren(List<XMLNode> aChildren){
		setNodes(aChildren);
	}

	public void setNodes(List<XMLNode> aNodes) {
		this.nodes = aNodes;
	}
	
	public void addChildNode(XMLNode aChild){
		nodes.add(aChild);
	}

	public List<XMLAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<XMLAttribute> aAttributes) {
		this.attributes = aAttributes;
	}
	
	public void addAttribute(XMLAttribute aAttribute){
		attributes.add(aAttribute);
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String aContent) {
		this.content = aContent;
	}
	
	public void setContent(boolean aContent){
		setContent(""+aContent);
	}
	
	public void setContent(float aContent){
		setContent(""+aContent);
	}

	public void setContent(double aContent){
		setContent(""+aContent);
	}

	public void setContent(long aContent){
		setContent(""+aContent);
	}

	public void setContent(int aContent){
		setContent(""+aContent);
	}
	
	public void setContent(List<Object> aL){
		setContent(aL.toString());
	}

	@Override public String toString(){
		return "name: "+name+", "+" attributes: "+attributes+", nodes: "+nodes;
	}
	
	private String createAttributeString(){
		String ret = "";
		if (attributes == null || attributes.size()==0)
			return ret;
		
		for (XMLAttribute a : attributes){
			ret += " "+a.toXMLString();
		}
		
		
		return ret;
	}

	public void write(PrintStream aWriter, int aTabs) throws IOException{
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(aTabs);
		aWriter.println(ident+XMLHelper.entag(getName()+attributeString));
		
		for (XMLNode child : nodes)
			child.write(aWriter, aTabs+1);
		
		if (content!=null)
			aWriter.println(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");
		
		aWriter.println(ident+XMLHelper.detag(getName()));
	} 

	
	public void write(PrintWriter aWriter, int aTabs) throws IOException{
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(aTabs);
		aWriter.write(ident+XMLHelper.entag(getName()+attributeString));
		
		for (XMLNode child : nodes)
			child.write(aWriter, aTabs+1);
		
		if (content!=null)
			aWriter.write(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");
		
		aWriter.write(ident+XMLHelper.detag(getName()));
	}

	
	public void write(OutputStreamWriter aWriter, int aTabs) throws IOException{
		try{
			System.out.println("writing "+getName()+" attr: "+attributes);
			String attributeString = createAttributeString();
			String ident = XMLHelper.makeIdent(aTabs);
			aWriter.write(ident+XMLHelper.entag(getName()+attributeString));
			
			for (XMLNode child : nodes)
				child.write(aWriter, aTabs+1);
			
			if (content!=null)
				aWriter.write(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");
			
			aWriter.write(ident+XMLHelper.detag(getName()));
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
