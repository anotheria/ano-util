package net.anotheria.util.content.template.configs;

import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for template functionality.
 *
 * @author Illya Bogatyrchuk
 * @version $Id: $Id
 */
@ConfigureMe(name = "ano-util-template-processor-config")
public final class TemplateProcessorConfig implements Serializable {
	/**
	 * Basic serial version UID.
	 */
	@DontConfigure
	private static final long serialVersionUID = -4135549619855269472L;
	/**
	 * {@link Logger} instance.
	 */
	@DontConfigure
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateProcessorConfig.class);
	/**
	 * {@link TemplateProcessorConfig} instance.
	 */
	@DontConfigure
	private static TemplateProcessorConfig instance;
	/**
	 * Sync lock.
	 */
	@DontConfigure
	private static final Object LOCK = new Object();
	/**
	 * {@link CustomConstantVariableConfig} collection.
	 */
	@Configure
	private CustomConstantVariableConfig[] customConstantVariables;

	/**
	 * Private constructor.
	 */
	private TemplateProcessorConfig() {
	}

	/**
	 * Returns configuration instance.
	 *
	 * @return a {@link net.anotheria.util.content.template.configs.TemplateProcessorConfig} object.
	 */
	public static TemplateProcessorConfig getInstance() {
		if (instance != null)
			return instance;

		synchronized (LOCK) {
			if (instance != null)
				return instance;

			instance = new TemplateProcessorConfig();

			try {
				ConfigurationManager.INSTANCE.configure(instance);
				LOGGER.info("getInstance() configured using instance '{}'", instance);
			} catch (IllegalArgumentException e) {
				LOGGER.warn("getInstance() configuration fail. Relaying on defaults: " + instance, e);
			}

			return instance;
		}
	}

	/**
	 * <p>Getter for the field <code>customConstantVariables</code>.</p>
	 *
	 * @return an array of {@link net.anotheria.util.content.template.configs.CustomConstantVariableConfig} objects.
	 */
	public CustomConstantVariableConfig[] getCustomConstantVariables() {
		return customConstantVariables;
	}

	/**
	 * <p>Setter for the field <code>customConstantVariables</code>.</p>
	 *
	 * @param customConstantVariables a {@link net.anotheria.util.content.template.configs.CustomConstantVariableConfig} object.
	 */
	public void setCustomConstantVariables(CustomConstantVariableConfig... customConstantVariables) {
		this.customConstantVariables = customConstantVariables;
	}

	/**
	 * Returns custom constants variables map.
	 *
	 * @return {@link java.util.Map} with custom variable key/value pairs
	 */
	public Map<String, String> getCustomConstantVariablesMap() {
		final Map<String, String> result = new HashMap<>();

		if (this.customConstantVariables == null || this.customConstantVariables.length == 0)
			return result;

		for (CustomConstantVariableConfig customConstantVariable : this.customConstantVariables) {
			if (StringUtils.isEmpty(customConstantVariable.getKey()))
				continue;

			result.put(customConstantVariable.getKey(), customConstantVariable.getValue());
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "TemplateProcessorConfig{" +
				"customConstantVariables=" + Arrays.toString(customConstantVariables) +
				'}';
	}
}
