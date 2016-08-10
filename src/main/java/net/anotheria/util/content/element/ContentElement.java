package net.anotheria.util.content.element;

/**
 * Abstract Content Element class.
 *
 * @author lrosenberg
 * @version $Id: $Id
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

	/**
	 * <p>Getter for the field <code>elementText</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getElementText() {
		return elementText;
	}

	/**
	 * <p>Setter for the field <code>elementText</code>.</p>
	 *
	 * @param elementText a {@link java.lang.String} object.
	 */
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

