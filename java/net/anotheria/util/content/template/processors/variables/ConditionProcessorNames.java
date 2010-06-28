package net.anotheria.util.content.template.processors.variables;

import net.anotheria.util.StringUtils;

/**
 * This helper class defines prefixes usable for variables in the variable processor.
 * Actually for template Processor && for VariableProcessor.
 *
 * @author lrosenberg
 */
public enum ConditionProcessorNames {
	/**
	 * @see ConditionProcessorNames constructor
	 */
	//Currently  hacked! --  due to 'if' operator  use ..... sorry!
	iF(ConditionPrefixes.PREFIX_IF) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			String[] ret = StringUtils.tokenize(defVal, ';');
			return Boolean.TRUE.toString().equals(variable) ? ret[0] : ret.length > 1 ? ret[1] : "";
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	ifNot(ConditionPrefixes.PREFIX_IF_NOT) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return !Boolean.TRUE.toString().equals(variable) ? defVal : "";
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	present(ConditionPrefixes.PREFIX_PRESENT) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return variable.length() > 0 ? defVal : "";
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	notPresent(ConditionPrefixes.PREFIX_NOT_PRESENT) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return !(variable.length() > 0) ? defVal : "";
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	equals(ConditionPrefixes.PREFIX_EQUALS) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(!(variable == null || defVal == null) && variable.equals(defVal));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	notEquals(ConditionPrefixes.PREFIX_NOT_EQUALS) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(!(!(variable == null || defVal == null) && variable.equals(defVal)));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	inRange(ConditionPrefixes.PREFIX_IN_RANGE) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(inRange(variable, defVal));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	greaterThan(ConditionPrefixes.PREFIX_GREATERTHEN) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(checkRelations(variable, defVal, Relations.greaterThanRelation));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	greaterEqual(ConditionPrefixes.PREFIX_GREATEREQUAL) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(checkRelations(variable, defVal, Relations.greaterEqualRelation));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	lessThan(ConditionPrefixes.PREFIX_LESSTHAN) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(checkRelations(variable, defVal, Relations.lessThanRelation));
		}},
	/**
	 * @see ConditionProcessorNames constructor
	 */
	lessEqual(ConditionPrefixes.PREFIX_LESSEQUAL) {
		@Override
		public String executeReplacement(String variable, String defVal) {
			return String.valueOf(checkRelations(variable, defVal, Relations.lessEqualRelation));
		}};

	/**
	 * ConditionProcessorNames prefixName.
	 */
	private String prefixName;

	/**
	 * Constructor
	 *
	 * @param stringValue string representation
	 */
	ConditionProcessorNames(String stringValue) {
		this.prefixName = stringValue;
	}

	public String getPrefixName() {
		return prefixName;
	}

	/**
	 * Simply making variable replacement
	 *
	 * @param variable value
	 * @param defVal   default value
	 * @return replaced value
	 */
	public abstract String executeReplacement(String variable, String defVal);

	private static boolean inRange(String s1, String s2) {
		if (s1 == null || s2 == null)
			return false;
		String[] rangeTokens = StringUtils.tokenize(s1, '-');
		if (rangeTokens.length != 2)
			return false;
		try {
			int rangeMin = Integer.parseInt(rangeTokens[0]);
			int rangeMax = Integer.parseInt(rangeTokens[1]);
			int value = Integer.parseInt(s2);
			return rangeMin <= value && value <= rangeMax;
		} catch (NumberFormatException ignored) {
			return false;
		}
	}

	/**
	 * Check relations.
	 *
	 * @param var	variable 1
	 * @param var2   variable 2
	 * @param option actually relation
	 * @return boolean value
	 */
	private static boolean checkRelations(String var, String var2, Relations option) {
		if ((var == null || var2 == null) && (!var.isEmpty() || !var2.isEmpty()))
			return false;
		try {
			int value = Integer.valueOf(var);
			int value2 = Integer.valueOf(var2);
			boolean result = false;
			switch (option) {
				case greaterThanRelation:
					result = value > value2;
					break;
				case greaterEqualRelation:
					result = value >= value2;
					break;
				case lessThanRelation:
					result = value < value2;
					break;
				case lessEqualRelation:
					result = value <= value2;
			}
			return result;
		} catch (NumberFormatException ignored) {
			return false;
		}
	}

	/**
	 * Simplest enum of relations between 2 numbers. etc.
	 */
	private static enum Relations {
		greaterThanRelation, greaterEqualRelation, lessThanRelation, lessEqualRelation
	}

}
