package net.anotheria.util.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @version $Id: $Id
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
	 *
	 * @param aName node name
	 */
	public XMLNode(String aName){
		name = aName;
		attributes = new ArrayList<>();
		nodes = new ArrayList<>();
	}

	
	/**
	 * <p>Getter for the field <code>nodes</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<XMLNode> getNodes() {
		return nodes;
	}

	/**
	 * Set child nodes  for current.
	 *
	 * @param aChildren xmlNodes list
	 */
	public void setChildren(List<XMLNode> aChildren){
        this.nodes = aChildren;
    }

	/**
	 * <p>Setter for the field <code>nodes</code>.</p>
	 *
	 * @param aNodes a {@link java.util.List} object.
	 */
	public void setNodes(List<XMLNode> aNodes) {
		this.nodes = aNodes;
	}

	/**
	 * Allow add child node  for current.
	 *
	 * @param aChild xmlNode
	 */
	public void addChildNode(XMLNode aChild){
		nodes.add(aChild);
	}

	/**
	 * <p>Getter for the field <code>attributes</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<XMLAttribute> getAttributes() {
		return attributes;
	}

	/**
	 * <p>Setter for the field <code>attributes</code>.</p>
	 *
	 * @param aAttributes a {@link java.util.List} object.
	 */
	public void setAttributes(List<XMLAttribute> aAttributes) {
		this.attributes = aAttributes;
	}
	
	/**
	 * <p>addAttribute.</p>
	 *
	 * @param aAttribute a {@link net.anotheria.util.xml.XMLAttribute} object.
	 */
	public void addAttribute(XMLAttribute aAttribute){
		attributes.add(aAttribute);
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param aName a {@link java.lang.String} object.
	 */
	public void setName(String aName) {
		this.name = aName;
	}

	/**
	 * <p>Getter for the field <code>content</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a {@link java.lang.String} object.
	 */
	public void setContent(String aContent) {
		this.content = aContent;
	}
	
	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a boolean.
	 */
	public void setContent(boolean aContent){
        this.content = String.valueOf(aContent);
    }
	
	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a float.
	 */
	public void setContent(float aContent){
        this.content = String.valueOf(aContent);
    }

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a double.
	 */
	public void setContent(double aContent){
        this.content = String.valueOf(aContent);
    }

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a long.
	 */
	public void setContent(long aContent){
        this.content = String.valueOf(aContent);
    }

	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aContent a int.
	 */
	public void setContent(int aContent){
        this.content = String.valueOf(aContent);
    }
	
	/**
	 * <p>Setter for the field <code>content</code>.</p>
	 *
	 * @param aL a {@link java.util.List} object.
	 */
	public void setContent(List<Object> aL){
        this.content = aL.toString();
    }

	/** {@inheritDoc} */
	@Override public String toString(){
		return "name: "+name+", "+" attributes: "+attributes+", nodes: "+nodes;
	}

	/**
	 * Areates attribute string.
	 */
	private String createAttributeString(){
		String ret = "";
		if (attributes == null || attributes.isEmpty())
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
