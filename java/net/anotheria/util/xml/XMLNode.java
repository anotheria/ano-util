package net.anotheria.util.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class XMLNode {
	private List<XMLNode> nodes;
	private List<XMLAttribute> attributes;
	
	private String name;
	private String content;
	
	
	public XMLNode(String aName){
		name = aName;
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

	public String toString(){
		return "name: "+name+", "+" attributes: "+attributes+", nodes: "+nodes;
	}
	
	public void write(OutputStreamWriter writer) throws IOException{
		writer.write(XMLHelper.entag(getName()));
		
		for (XMLNode child : nodes)
			child.write(writer);
		
		writer.write(XMLHelper.detag(getName()));
	}
}
