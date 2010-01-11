package net.anotheria.util.mapper;

import java.util.Map;

/**
 * Test destination Model Object.
 * <p/>
 * <P>Various attributes of test model, and related behaviour.
 * <p/>
 * @author vitaliy
 * @version 1.0
 *          Date: Jan 9, 2010
 *          Time: 7:36:12 PM
 */public class DestinationClass {
	private String step;

	@PopulateWith(value = "step")
	private Integer stepAnnotated;

	@PopulateWith(value = "step", converterId = "tempConverter")
	private String stepAnnotatedAndConverted;

	private Integer stepConfigured;
	private String stepConverted;
	private Map<String, Integer> intMap;
	
	public Map<String, Integer> getIntMap() {
		return intMap;
	}

	public void setIntMap(Map<String, Integer> intMap) {
		this.intMap = intMap;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Integer getStepAnnotated() {
		return stepAnnotated;
	}

	public void setStepAnnotated(Integer stepAnnotated) {
		this.stepAnnotated = stepAnnotated;
	}

	public Integer getStepConfigured() {
		return stepConfigured;
	}

	public void setStepConfigured(Integer stepConfigured) {
		this.stepConfigured = stepConfigured;
	}

	public String getStepConverted() {
		return stepConverted;
	}

	public void setStepConverted(String stepConverted) {
		this.stepConverted = stepConverted;
	}

	public String getStepAnnotatedAndConverted() {
		return stepAnnotatedAndConverted;
	}

	public void setStepAnnotatedAndConverted(String stepAnnotatedAndConverted) {
		this.stepAnnotatedAndConverted = stepAnnotatedAndConverted;
	}
}
