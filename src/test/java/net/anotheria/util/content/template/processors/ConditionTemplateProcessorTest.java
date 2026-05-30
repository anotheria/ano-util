package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.processors.variables.ConditionProcessorNames;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@BeforeAll
	public static void setUp() {
		processor = new ConditionTemplateProcessor();
	}

	@Test
	public void testSipleFlow() {

		String valueIf = processor.replace(ConditionProcessorNames.iF.getPrefixName(), "true", "1;", null);
		assertTrue(valueIf.equals("1"), "should be true");

		String valueNotIf = processor.replace(ConditionProcessorNames.ifNot.getPrefixName(), "false", "1;", null);
		assertTrue(valueNotIf.equals("1;"), "should be true");

		String valuePrS = processor.replace(ConditionProcessorNames.present.getPrefixName(), "yeap", "test", null);
		assertTrue(valuePrS.equals("test"), "should be true");

		String valueNotPrS = processor.replace(ConditionProcessorNames.notPresent.getPrefixName(), "", "test", null);
		assertTrue(valueNotPrS.equals("test"), "should be true");

		String valueEquals = processor.replace(ConditionProcessorNames.equals.getPrefixName(), "", "", null);
		assertTrue(Boolean.valueOf(valueEquals), "should be true");

		String valueNotEquals = processor.replace(ConditionProcessorNames.notEquals.getPrefixName(), "", "", null);
		assertTrue(!Boolean.valueOf(valueNotEquals), "should be true");


		String valueInRange1 = processor.replace(ConditionProcessorNames.inRange.getPrefixName(), "10-15", "11", null);
		assertTrue(Boolean.valueOf(valueInRange1), "should be true");

		String valueInRange2 = processor.replace(ConditionProcessorNames.inRange.getPrefixName(), "10-15", "0", null);
		assertTrue(!Boolean.valueOf(valueInRange2), "should be true");


		String valGreaterThan = processor.replace(ConditionProcessorNames.greaterThan.getPrefixName(), "10", "1", null);
		assertTrue(Boolean.valueOf(valGreaterThan), "should be true");
		String valGreaterEq1 = processor.replace(ConditionProcessorNames.greaterEqual.getPrefixName(), "10", "1", null);
		assertTrue(Boolean.valueOf(valGreaterEq1), "should be true");
		String valGreaterEq2 = processor.replace(ConditionProcessorNames.greaterEqual.getPrefixName(), "10", "10", null);
		assertTrue(Boolean.valueOf(valGreaterEq2), "should be true");

		String valeLessThan = processor.replace(ConditionProcessorNames.lessThan.getPrefixName(), "1", "100", null);
		assertTrue(Boolean.valueOf(valeLessThan), "should be true");
		String valeLessEq1 = processor.replace(ConditionProcessorNames.lessEqual.getPrefixName(), "1", "100", null);
		assertTrue(Boolean.valueOf(valeLessEq1), "should be true");
		String valeLessEq2 = processor.replace(ConditionProcessorNames.lessEqual.getPrefixName(), "100", "100", null);
		assertTrue(Boolean.valueOf(valeLessEq2), "should be true");
	}

	/**
	 * Trying to post bad data.
	 */
	public static void testWrongUse() {
		String reS = processor.replace("", ",", "", null);
		assertTrue(reS.isEmpty());
		processor.replace(null, ",", "", null);
	}
}
