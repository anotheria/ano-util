package net.anotheria.util.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * XMLNode. Actually 'Anotheria' custom XML representation.
 *
 * @author another
 */
public class XMLNode {

	private static final Logger log = LoggerFactory.getLogger(XMLNode.class);

	/**
	 * SubNodes of this node.
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
	 * Constructor. Creates a new XMLNode.
	 * @param aName node name
	 */
	public XMLNode(String aName){
		name = aName;
		attributes = new ArrayList<>();
		nodes = new ArrayList<>();
	}

	
	public List<XMLNode> getNodes() {
		return nodes;
	}

	/**
	 * Set child nodes  for current.
	 * @param aChildren xmlNodes list
	 */
	public void setChildren(List<XMLNode> aChildren){
        this.nodes = aChildren;
    }

	public void setNodes(List<XMLNode> aNodes) {
		this.nodes = aNodes;
	}

	/**
	 * Allow add child node  for current.
	 * @param aChild xmlNode
	 */
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
        this.content = ""+aContent;
    }
	
	public void setContent(float aContent){
        this.content = ""+aContent;
    }

	public void setContent(double aContent){
        this.content = ""+aContent;
    }

	public void setContent(long aContent){
        this.content = ""+aContent;
    }

	public void setContent(int aContent){
        this.content = ""+aContent;
    }
	
	public void setContent(List<Object> aL){
        this.content = aL.toString();
    }

	@Override public String toString(){
		return "name: "+name+", "+" attributes: "+attributes+", nodes: "+nodes;
	}

	/**
	 * Areates attribute string.
	 * @return string
	 */
	private String createAttributeString(){
		String ret = "";
		if (attributes == null || attributes.size()==0)
			return ret;
		
		for (XMLAttribute a : attributes){
			ret += ' ' +a.toXMLString();
		}
		
		return ret;
	}

	/**
	 * Recursive - Write method.
	 *
	 * @param aWriter PrintStream writer.
	 * @param aTabs position to start
	 * @throws IOException on errors
	 */
	public void write(PrintStream aWriter, int aTabs) {
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(aTabs);
        aWriter.println(ident+XMLHelper.entag(name +attributeString));
		
		for (XMLNode child : nodes)
			child.write(aWriter, aTabs+1);
		
		if (content!=null)
			aWriter.println(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");

        aWriter.println(ident+XMLHelper.detag(name));
	}


	/**
	 * Recursive - Write method.
	 *
	 * @param aWriter PrintWriter
	 * @param aTabs position to start
	 * @throws IOException on errors
	 */
	public void write(PrintWriter aWriter, int aTabs) {
		String attributeString = createAttributeString();
		String ident = XMLHelper.makeIdent(aTabs);
        aWriter.write(ident+XMLHelper.entag(name +attributeString));
		
		for (XMLNode child : nodes)
			child.write(aWriter, aTabs+1);
		
		if (content!=null)
			aWriter.write(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");

        aWriter.write(ident+XMLHelper.detag(name));
	}


	/**
	 * Recursive - Write method.
	 *
	 * @param aWriter OutputStreamWriter 
	 * @param aTabs position to start
	 * @throws IOException on errors
	 */
	public void write(OutputStreamWriter aWriter, int aTabs) {
		try{
			String attributeString = createAttributeString();
			String ident = XMLHelper.makeIdent(aTabs);
            aWriter.write(ident+XMLHelper.entag(name +attributeString));
			
			for (XMLNode child : nodes)
				child.write(aWriter, aTabs+1);
			
			if (content!=null)
				aWriter.write(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");

            aWriter.write(ident+XMLHelper.detag(name));
		}catch(Throwable t){
			log.error(t.getMessage(), t);
		}
	}

	/**
	 * Recursive - Write method.
	 * 
	 * @param aWriter java.io.Writer
	 * @param aTabs tabs
	 * @throws IOException on errors
	 */
	public void write(Writer aWriter, int aTabs) {
		try{
			String attributeString = createAttributeString();
			String ident = XMLHelper.makeIdent(aTabs);
            aWriter.write(ident+XMLHelper.entag(name +attributeString));

			for (XMLNode child : nodes)
				child.write(aWriter, aTabs+1);

			if (content!=null)
				aWriter.write(XMLHelper.makeIdent(aTabs+1)+"<![CDATA["+content+"]]>\n");

            aWriter.write(ident+XMLHelper.detag(name));
		}catch(Throwable t){
			log.error(t.getMessage(), t);
		}
	}
}
