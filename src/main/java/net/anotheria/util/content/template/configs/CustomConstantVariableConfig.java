package net.anotheria.util.content.template.configs;

import org.configureme.annotations.Configure;
import org.configureme.annotations.DontConfigure;

import java.io.Serializable;

/**
 * Custom constant variable for constant template processor.
 *
 * @author Illya Bogatyrchuk
 * @version $Id: $Id
 */
public class CustomConstantVariableConfig implements Serializable {
	/**
	 * Basic serial version UID.
	 */
	@DontConfigure
	private static final long serialVersionUID = 6389036128350515695L;
	/**
	 * Constant variable key.
	 */
	@Configure
	private String key;
	/**
	 * Constant variable value.
	 */
	@Configure
	private String value;

	/**
	 * <p>Getter for the field <code>key</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <p>Setter for the field <code>key</code>.</p>
	 *
	 * @param key a {@link java.lang.String} object.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "CustomConstantVariableConfig{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
