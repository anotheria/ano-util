package net.anotheria.util.xml;

/**
 * XMLAttribute is an element of the xml node. 
 * @author lrosenberg
 */
public class XMLAttribute {
	/**
	 * The name of the attribute.
	 */
	private String name;
	/**
	 * The value of the attribute.
	 */
	private String value;

	/**
	 * Creates a new empty XMLAttribute.
	 */
	public XMLAttribute(){
		
	}
	
	/**
	 * Creates a new XMLAttribute for a given name and value.
	 * @param aName
	 * @param aValue
	 */
	public XMLAttribute(String aName, int  aValue){
		this(aName, ""+aValue);
	}

	/**
	 * Creates a new XMLAttribute for a given name and value.
	 * @param aName
	 * @param aValue
	 */
	public XMLAttribute(String aName, String aValue){
		name = aName;
		value = aValue;
	}
	
	@Override public String toString(){
		return name+"="+value;
	}

	/**
	 * Returns the name of the attribute.
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	/**
	 * Returns the value of the attribute.
	 * @return
	 */
	public String getValue() {
		return value;
	}

	public void setValue(String aValue) {
		this.value = aValue;
	}

	public String toXMLString(){
		return getName()+"="+XMLHelper.quote(getValue());
	}
}