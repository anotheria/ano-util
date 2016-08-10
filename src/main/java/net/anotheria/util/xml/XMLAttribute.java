package net.anotheria.util.xml;

/**
 * XMLAttribute is an element of the xml node.
 *
 * @author lrosenberg
 * @version $Id: $Id
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
	 *
	 * @param aName a {@link java.lang.String} object.
	 * @param aValue a int.
	 */
	public XMLAttribute(String aName, int  aValue){
		this(aName, String.valueOf(aValue));
	}

	/**
	 * Creates a new XMLAttribute for a given name and value.
	 *
	 * @param aName a {@link java.lang.String} object.
	 * @param aValue a {@link java.lang.String} object.
	 */
	public XMLAttribute(String aName, String aValue){
		name = aName;
		value = aValue;
	}
	
	/** {@inheritDoc} */
	@Override public String toString(){
		return name+ '=' +value;
	}

	/**
	 * Returns the name of the attribute.
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
	 * Returns the value of the attribute.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param aValue a {@link java.lang.String} object.
	 */
	public void setValue(String aValue) {
		this.value = aValue;
	}

	/**
	 * <p>toXMLString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toXMLString(){
        return name + '=' +XMLHelper.quote(value);
	}
}
