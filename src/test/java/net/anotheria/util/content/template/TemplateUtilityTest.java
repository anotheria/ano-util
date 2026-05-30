package net.anotheria.util.content.template;

import org.junit.jupiter.api.Test;

import static net.anotheria.util.content.template.TemplateUtility.replaceVariables;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Junit for TemplateUtility.
 *
 * @author h3llka
 */
public class TemplateUtilityTest {

	@Test
	public void testReplaceTest() {
		TemplateUtility.addProcessor(SimpleTestProcessor.PREFIX, new SimpleTestProcessor());
		String replacePart1 = '{' + SimpleTestProcessor.PREFIX + ':' + SimpleTestProcessor.var1 + '}';
		String replacePart2 = '{' + SimpleTestProcessor.PREFIX + ":not_exist:default___}";
		String text = "I'm text for replace! " + replacePart1;

        String userID = "1";
		String language = "ua";

		String testReplaceVAR1 = replaceVariables(new TemplateReplacementContext(userID, language), text);
		assertNotNull(testReplaceVAR1, "Should not be null");
		assertEquals("I'm text for replace! "  + SimpleTestProcessor.var1Value + language, testReplaceVAR1, "Should not be null");

        String text2 = "TEST 2 " + replacePart2;
        String testReplaceToDefault = replaceVariables(new TemplateReplacementContext(userID, language), text2);
		assertNotNull(testReplaceToDefault, "Should not be null");
		assertEquals("TEST 2 " + "default___" + userID, testReplaceToDefault, "Should not be null");

        String staticText = "I'm static {::::>>>>>}}}}}";
        String testReplaceStaticContent = replaceVariables(new TemplateReplacementContext(userID, language), staticText);
		assertNotNull(testReplaceStaticContent, "Should not be null");
		assertEquals(staticText, testReplaceStaticContent, "Should not be null");

		String replaceEmpty = replaceVariables(null, "");
		assertTrue(replaceEmpty.isEmpty(), "Should be empty");
	}

	@Test
	public void testProcessorOperations() {
		TemplateUtility.addProcessor(SimpleTestProcessor.PREFIX, new SimpleTestProcessor());
		assertNotNull(TemplateUtility.getDefaultProcessors().get(SimpleTestProcessor.PREFIX));
		assertTrue(TemplateUtility.getDefaultProcessors().get(SimpleTestProcessor.PREFIX) instanceof SimpleTestProcessor);
	}

	/**
	 * TemplateProcessor.
	 */
	public static class SimpleTestProcessor implements TemplateProcessor {
		/**
		 * Prefix.
		 */
		public static String PREFIX = "test";
		/**
		 * Test var.
		 */
		public static String var1 = "test1";
		/**
		 * Hardcoded ret value.
		 */
		public static String var1Value = "checked_";

		@Override
		public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
			if (aVariable.equals(var1))
				return var1Value + aContext.getLanguage();
			else return aDefValue + aContext.getUserId();

		}

		/**
		 * Constructor.
		 */
		public SimpleTestProcessor(){}
	}
}
