package net.anotheria.util.content.template.configs;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * {@link TemplateProcessorConfig} test.
 *
 * @author Illya Bogatyrchuk
 */
public class TemplateProcessorConfigTest {
	@Test
	public void testConfig() throws Exception {
		final TemplateProcessorConfig config = TemplateProcessorConfig.getInstance();
		assertNotNull(config);

		final CustomConstantVariableConfig[] variableConfigs = config.getCustomConstantVariables();
		assertNotNull(variableConfigs);
		assertEquals(2, variableConfigs.length);
		assertEquals("regSign", variableConfigs[0].getKey());
		assertEquals("&reg;", variableConfigs[0].getValue());
		assertEquals("sharpS", variableConfigs[1].getKey());
		assertEquals("&szlig;", variableConfigs[1].getValue());

		final Map<String, String> variablesMap = config.getCustomConstantVariablesMap();
		assertNotNull(variablesMap);
		assertEquals(2, variablesMap.size());
		assertEquals("&reg;", variablesMap.get("regSign"));
		assertEquals("&szlig;", variablesMap.get("sharpS"));
	}
}
