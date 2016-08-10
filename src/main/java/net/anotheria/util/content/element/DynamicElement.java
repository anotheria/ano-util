package net.anotheria.util.content.element;

import java.util.List;

/**
 * DynamicElement as subclass of ContentElement.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class DynamicElement extends ContentElement {
	/**
	 * DynamicElement 'prefix'.
	 */
	private String prefix;
	/**
	 * DynamicElement 'variableIndex'.
	 */
	private List<ContentElement> variableIndex;
	/**
	 * DynamicElement 'defValueIndex'.
	 */
	private List<ContentElement> defValueIndex;

	/**
	 * Constructor.
	 *
	 * @param aElementText   string parameter
	 * @param aPrefix		aPrefix
	 * @param aVariableIndex list of content elements
	 * @param aDefValueIndex list of content elements
	 */
	public DynamicElement(String aElementText, String aPrefix, List<ContentElement> aVariableIndex, List<ContentElement> aDefValueIndex) {
		super(aElementText);
		this.prefix = aPrefix;
		this.variableIndex = aVariableIndex;
		this.defValueIndex = aDefValueIndex;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDynamic() {
		return true;
	}

	/**
	 * <p>Getter for the field <code>prefix</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * <p>Setter for the field <code>prefix</code>.</p>
	 *
	 * @param aPrefix a {@link java.lang.String} object.
	 */
	public void setPrefix(String aPrefix) {
		this.prefix = aPrefix;
	}

	/**
	 * <p>Getter for the field <code>variableIndex</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ContentElement> getVariableIndex() {
		return variableIndex;
	}

	/**
	 * <p>Setter for the field <code>variableIndex</code>.</p>
	 *
	 * @param aVariableIndex a {@link java.util.List} object.
	 */
	public void setVariableIndex(List<ContentElement> aVariableIndex) {
		this.variableIndex = aVariableIndex;
	}

	/**
	 * <p>Getter for the field <code>defValueIndex</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<ContentElement> getDefValueIndex() {
		return defValueIndex;
	}

	/**
	 * <p>Setter for the field <code>defValueIndex</code>.</p>
	 *
	 * @param aDefValueIndex a {@link java.util.List} object.
	 */
	public void setDefValueIndex(List<ContentElement> aDefValueIndex) {
		this.defValueIndex = aDefValueIndex;
	}

}
