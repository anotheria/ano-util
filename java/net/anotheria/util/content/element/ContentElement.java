package net.anotheria.util.content.element;

/**
 * Abstract Content Element class.
 *
 * @author lrosenberg
 */
public abstract class ContentElement {
	/**
	 * ContentElement 'elementText'.
	 */
	private String elementText;

	/**
	 * Constructor.
	 *
	 * @param aElementText string parameter
	 */
	public ContentElement(String aElementText) {
		this.elementText = aElementText;
	}

	public String getElementText() {
		return elementText;
	}

	public void setElementText(String elementText) {
		this.elementText = elementText;
	}

	/**
	 * Abstract method.
	 * Currently returns true if element is dynamic.
	 * False otherwise
	 *
	 * @return boolean parameter
	 */
	public abstract boolean isDynamic();

}

