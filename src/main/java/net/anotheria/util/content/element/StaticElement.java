package net.anotheria.util.content.element;

/**
 * StaticElement as subclass of ContentElement.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class StaticElement extends ContentElement {

	/**
	 * Constructor.
	 *
	 * @param aElementText string text
	 */
	public StaticElement(String aElementText) {
		super(aElementText);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDynamic() {
		return false;
	}

}
