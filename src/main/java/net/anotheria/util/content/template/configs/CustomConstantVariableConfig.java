package net.anotheria.util.content.template.configs;

import org.configureme.annotations.Configure;
import org.configureme.annotations.DontConfigure;

import java.io.Serializable;

/**
 * Custom constant variable for constant template processor.
 *
 * @author Illya Bogatyrchuk
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "CustomConstantVariableConfig{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
