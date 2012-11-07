package net.anotheria.util.content.element;

import java.util.List;

/**
 * DynamicElement as subclass of ContentElement.
 *
 * @author lrosenberg
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

	@Override
	public boolean isDynamic() {
		return true;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String aPrefix) {
		this.prefix = aPrefix;
	}

	public List<ContentElement> getVariableIndex() {
		return variableIndex;
	}

	public void setVariableIndex(List<ContentElement> aVariableIndex) {
		this.variableIndex = aVariableIndex;
	}

	public List<ContentElement> getDefValueIndex() {
		return defValueIndex;
	}

	public void setDefValueIndex(List<ContentElement> aDefValueIndex) {
		this.defValueIndex = aDefValueIndex;
	}

}
