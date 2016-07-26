package net.anotheria.util.content.template;

import org.junit.Assert;
import org.junit.Test;

import static net.anotheria.util.content.template.TemplateUtility.replaceVariables;

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
		Assert.assertNotNull("Should not be null", testReplaceVAR1);
		Assert.assertEquals("Should not be null",  "I'm text for replace! "  + SimpleTestProcessor.var1Value + language, testReplaceVAR1);

        String text2 = "TEST 2 " + replacePart2;
        String testReplaceToDefault = replaceVariables(new TemplateReplacementContext(userID, language), text2);
		Assert.assertNotNull("Should not be null", testReplaceToDefault);
		Assert.assertEquals("Should not be null", "TEST 2 " + "default___" + userID, testReplaceToDefault);

        String staticText = "I'm static {::::>>>>>}}}}}";
        String testReplaceStaticContent = replaceVariables(new TemplateReplacementContext(userID, language), staticText);
		Assert.assertNotNull("Should not be null", testReplaceStaticContent);
		Assert.assertEquals("Should not be null", staticText, testReplaceStaticContent);

		String replaceEmpty = replaceVariables(null, "");
		Assert.assertTrue("Should be empty", replaceEmpty.isEmpty());
	}

	@Test
	public void testProcessorOperations() {
		TemplateUtility.addProcessor(SimpleTestProcessor.PREFIX, new SimpleTestProcessor());
		Assert.assertNotNull(TemplateUtility.getDefaultProcessors().get(SimpleTestProcessor.PREFIX));
		Assert.assertTrue(TemplateUtility.getDefaultProcessors().get(SimpleTestProcessor.PREFIX) instanceof SimpleTestProcessor);
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
