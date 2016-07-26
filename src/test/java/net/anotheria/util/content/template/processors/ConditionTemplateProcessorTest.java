package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.processors.variables.ConditionProcessorNames;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Junit.
 *
 * @author h3llka
 */
public class ConditionTemplateProcessorTest {

	/**
	 * Test processor.
	 */
	private static ConditionTemplateProcessor processor;

	@BeforeClass
	public static void setUp() {
		processor = new ConditionTemplateProcessor();
	}

	@Test
	public void testSipleFlow() {

		String valueIf = processor.replace(ConditionProcessorNames.iF.getPrefixName(), "true", "1;", null);
		Assert.assertTrue("should be true", valueIf.equals("1"));

		String valueNotIf = processor.replace(ConditionProcessorNames.ifNot.getPrefixName(), "false", "1;", null);
		Assert.assertTrue("should be true", valueNotIf.equals("1;"));

		String valuePrS = processor.replace(ConditionProcessorNames.present.getPrefixName(), "yeap", "test", null);
		Assert.assertTrue("should be true", valuePrS.equals("test"));

		String valueNotPrS = processor.replace(ConditionProcessorNames.notPresent.getPrefixName(), "", "test", null);
		Assert.assertTrue("should be true", valueNotPrS.equals("test"));

		String valueEquals = processor.replace(ConditionProcessorNames.equals.getPrefixName(), "", "", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valueEquals));

		String valueNotEquals = processor.replace(ConditionProcessorNames.notEquals.getPrefixName(), "", "", null);
		Assert.assertTrue("should be true", !Boolean.valueOf(valueNotEquals));


		String valueInRange1 = processor.replace(ConditionProcessorNames.inRange.getPrefixName(), "10-15", "11", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valueInRange1));

		String valueInRange2 = processor.replace(ConditionProcessorNames.inRange.getPrefixName(), "10-15", "0", null);
		Assert.assertTrue("should be true", !Boolean.valueOf(valueInRange2));


		String valGreaterThan = processor.replace(ConditionProcessorNames.greaterThan.getPrefixName(), "10", "1", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valGreaterThan));
		String valGreaterEq1 = processor.replace(ConditionProcessorNames.greaterEqual.getPrefixName(), "10", "1", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valGreaterEq1));
		String valGreaterEq2 = processor.replace(ConditionProcessorNames.greaterEqual.getPrefixName(), "10", "10", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valGreaterEq2));

		String valeLessThan = processor.replace(ConditionProcessorNames.lessThan.getPrefixName(), "1", "100", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valeLessThan));
		String valeLessEq1 = processor.replace(ConditionProcessorNames.lessEqual.getPrefixName(), "1", "100", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valeLessEq1));
		String valeLessEq2 = processor.replace(ConditionProcessorNames.lessEqual.getPrefixName(), "100", "100", null);
		Assert.assertTrue("should be true", Boolean.valueOf(valeLessEq2));
	}

	/**
	 * Trying to post bad data.
	 */
	public static void testWrongUse() {
		String reS = processor.replace("", ",", "", null);
		Assert.assertTrue(reS.isEmpty());
		processor.replace(null, ",", "", null);
	}
}
