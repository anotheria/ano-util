package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLNode {
	private List<XMLNode> nodes;
	private List<XMLAttribute> attributes;
	
	private String name;
	private String content;
	
	
	public XMLNode(String aName){
		name = aName;
	
		attributes = new ArrayList<XMLAttribute>();
		nodes = new ArrayList<XMLNode>();
	}

	
	public List<XMLNode> getNodes() {
		return nodes;
	}
	
	public void setChildren(List<XMLNode> children){
		setNodes(children);
	}

	public void setNodes(List<XMLNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addChildNode(XMLNode child){
		nodes.add(child);
	}

	public List<XMLAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<XMLAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(XMLAttribute attribute){
		attributes.add(attribute);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void setContent(boolean content){
		setContent(""+content);
	}
	
	public void setContent(float content){
		setContent(""+content);
	}

	public void setContent(double content){
		setContent(""+content);
	}

	public void setContent(long content){
		setContent(""+content);
	}

	public void setContent(int content){
		setContent(""+content);
	}
	
	public void setContent(List l ){
		setContent(l.toString());
	}

	public String toString(){
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

	public void write(PrintStream writer, int tabs) throws IOException{
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(tabs);
		writer.println(ident+XMLHelper.entag(getName()+attributeString));
		
		for (XMLNode child : nodes)
			child.write(writer, tabs+1);
		
		if (content!=null)
			writer.println(XMLHelper.makeIdent(tabs+1)+"<![CDATA["+content+"]]>\n");
		
		writer.println(ident+XMLHelper.detag(getName()));
	} 

	
	public void write(PrintWriter writer, int tabs) throws IOException{
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(tabs);
		writer.write(ident+XMLHelper.entag(getName()+attributeString));
		
		for (XMLNode child : nodes)
			child.write(writer, tabs+1);
		
		if (content!=null)
			writer.write(XMLHelper.makeIdent(tabs+1)+"<![CDATA["+content+"]]>\n");
		
		writer.write(ident+XMLHelper.detag(getName()));
	}

	
	public void write(OutputStreamWriter writer, int tabs) throws IOException{
		try{
			System.out.println("writing "+getName()+" attr: "+attributes);
			String attributeString = createAttributeString();
			String ident = XMLHelper.makeIdent(tabs);
			writer.write(ident+XMLHelper.entag(getName()+attributeString));
			
			for (XMLNode child : nodes)
				child.write(writer, tabs+1);
			
			if (content!=null)
				writer.write(XMLHelper.makeIdent(tabs+1)+"<![CDATA["+content+"]]>\n");
			
			writer.write(ident+XMLHelper.detag(getName()));
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
