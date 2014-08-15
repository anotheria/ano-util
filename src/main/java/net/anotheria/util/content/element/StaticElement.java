package net.anotheria.util.content.element;

/**
 * StaticElement as subclass of ContentElement.
 *
 * @author lrosenberg
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

	@Override
	public boolean isDynamic() {
		return false;
	}

}
