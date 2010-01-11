package net.anotheria.util.mapper;

import java.util.Map;

/**
 * Test source Model Object.
 * <p/>
 * <P>Various attributes of test model, and related behaviour.
 * <p/>
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 9, 2010
 *          Time: 7:36:12 PM
 */
public class SourceClass {
	@PopulateWith(value = "stepAnnotated")
	private final int step;


	@PopulateWith(value = "stepAnnotatedAndConverted", converterId = "tempConverter")
	private int stepToConvert;

	private Map<String, Integer> intMap;

	public SourceClass(int step) {
		this.step = step;
	}
	
	public int getStepToConvert() {
		return stepToConvert;
	}

	public void setStepToConvert(int stepToConvert) {
		this.stepToConvert = stepToConvert;
	}

	public int getStep() {
		return step;
	}

	public Map<String, Integer> getIntMap() {
		return intMap;
	}

	public void setIntMap(Map<String, Integer> intMap) {
		this.intMap = intMap;
	}
}

