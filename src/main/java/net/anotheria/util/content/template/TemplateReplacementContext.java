package net.anotheria.util.content.template;

import java.util.HashMap;
import java.util.Map;

/**
 * ReplacementContext - which contains additional parameters for proper
 * variable replacement by TemplateProcessor.
 *
 * @author h3llka
 * @version $Id: $Id
 */
public class TemplateReplacementContext {
	/**
	 * ReplacementContext 'userId'.
	 */
	private String userId;
	/**
	 * ReplacementContext 'language'.
	 */
	private String language;
	/**
	 * ReplacementContext 'attributes'.
	 */
	private Map<String, Object> attributes;

	/**
	 * Constructor.
	 *
	 * @param aUserId	 id
	 * @param aLanguage   lang
	 * @param aAttributes additional attributes
	 */
	public TemplateReplacementContext(String aUserId, String aLanguage, Map<String, Object> aAttributes) {
		this.userId = aUserId;
		this.language = aLanguage;
		this.attributes = aAttributes == null ? new HashMap<String, Object>() : aAttributes;
	}

	/**
	 * Constructor by default.
	 */
	public TemplateReplacementContext() {
		this.attributes = new HashMap<>();
	}

	/**
	 * Constructor.
	 *
	 * @param aUserId   id
	 * @param aLanguage lang
	 */
	public TemplateReplacementContext(String aUserId, String aLanguage) {
		this.userId = aUserId;
		this.language = aLanguage;
		this.attributes = new HashMap<>();
	}

	/**
	 * <p>Getter for the field <code>attributes</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * Attributes setter.
	 *
	 * @param attributes collection
	 */
	public void setAttributes(Map<String, Object> attributes) {
		if (attributes == null)
			throw new IllegalArgumentException("attributes Map can't be null");
		this.attributes = attributes;
	}

	/**
	 * <p>Getter for the field <code>language</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * <p>Setter for the field <code>language</code>.</p>
	 *
	 * @param language a {@link java.lang.String} object.
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * <p>Getter for the field <code>userId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p>Setter for the field <code>userId</code>.</p>
	 *
	 * @param userId a {@link java.lang.String} object.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Returns attribute with given name.
	 *
	 * @param attributeName attribute name
	 * @return attribute value
	 */
	public Object getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	/**
	 * Allow to add single attribute.
	 *
	 * @param attributeName  name
	 * @param attributeValue value
	 */
	public void addAttribute(String attributeName, Object attributeValue) {
		attributes.put(attributeName, attributeValue);
	}
}
