package net.anotheria.util.content.template;

import net.anotheria.util.StringUtils;
import net.anotheria.util.content.element.ContentElement;
import net.anotheria.util.content.element.DynamicElement;
import net.anotheria.util.content.element.StaticElement;
import net.anotheria.util.content.template.processors.ConditionTemplateProcessor;
import net.anotheria.util.content.template.processors.ConstantsTemplateProcessor;
import net.anotheria.util.content.template.processors.variables.ConditionPrefixes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.anotheria.util.content.TextReplaceConstants.LINE_DELIMITER;
import static net.anotheria.util.content.TextReplaceConstants.QUOTE;
import static net.anotheria.util.content.TextReplaceConstants.TAG_END;
import static net.anotheria.util.content.TextReplaceConstants.TAG_START;


/**
 * Utility for replacement of expressions with variables in texts. An expression consists of three parts:
 * {prefix:name:defaultValue}. The prefix tells the variable utility which processor to use, the name and default value are submitted to the processor in question.
 * Actually Similar to VariablesUtility from ano-site -  but uses different processors (TemplateProcessor).
 *
 * @author lrosenberg
 */
public final class TemplateUtility {

	/**
	 * Map with pre-configured  template processors.
	 */
	private static final Map<String, TemplateProcessor> defaultProcessors = new HashMap<>();


	static {
		defaultProcessors.put(ConstantsTemplateProcessor.PREFIX, new ConstantsTemplateProcessor());

		TemplateProcessor p = new ConditionTemplateProcessor();
		defaultProcessors.put(ConditionPrefixes.PREFIX_IF, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_IF_NOT, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_PRESENT, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_NOT_PRESENT, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_EQUALS, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_NOT_EQUALS, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_IN_RANGE, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_GREATERTHEN, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_GREATEREQUAL, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_LESSTHAN, p);
		defaultProcessors.put(ConditionPrefixes.PREFIX_LESSEQUAL, p);

	}

	/**
	 * Prevent instantiation.
	 */
	private TemplateUtility() {
	}

	/**
	 * Adds a processor to this variable utility.
	 *
	 * @param prefix	prefix for processor mapping.
	 * @param processor processor.
	 */
	public static void addProcessor(String prefix, TemplateProcessor processor) {
		defaultProcessors.put(prefix, processor);
	}

	/**
	 * Return pre configured processors.
	 *
	 */
	public static Map<String, TemplateProcessor> getDefaultProcessors() {
		Map<String, TemplateProcessor> ret = new HashMap<>(defaultProcessors);
		return ret;
	}

	/**
	 * Replaces variable expressions in the given string src with help of default processors.
	 *
	 * @param context additional parameters for processors
	 * @param src	 text
	 * @return text with replaced expressions
	 */
	public static String replaceVariables(TemplateReplacementContext context, String src) {
		if (defaultProcessors.isEmpty())
			throw new RuntimeException("Processors are not configured");
		return replaceVariables(context, src, defaultProcessors);
	}

	/**
	 * Replaces variable expressions in the given string src with the help of submitted processors. Useful for variable customization.
	 *
	 * @param context	additional parameters for processors
	 * @param src		text
	 * @param processors collection of processors which should be used
	 * @return text with replaced expressions
	 */
	public static String replaceVariables(TemplateReplacementContext context, String src, Map<String, TemplateProcessor> processors) {
		if (src == null || src.length() == 0)
			return src;
		List<ContentElement> index = indexSource(src);
		return replaceVariables(context, index, processors);
	}

	/**
	 * Execute replacement.
	 *
	 * @param context	additional processor parameters
	 * @param index	  ContentElement collection
	 * @param processors TemplateProcessor collection
	 * @return replaced text
	 */
	private static String replaceVariables(TemplateReplacementContext context, Iterable<ContentElement> index, Map<String, TemplateProcessor> processors) {
		StringBuilder ret = new StringBuilder();
		for (ContentElement el : index)
			ret.append(replaceContentElement(context, el, processors));
		return ret.toString();
	}

	/**
	 * Creates List of <a>ContentElement</a> from incoming string.
	 *
	 * @param src text
	 */
	private static List<ContentElement> indexSource(String src) {
		String myS = StringUtils.removeChar(src, '\r');
		List<String> stringIndex = StringUtils.indexSuperTags(myS, TAG_START, TAG_END);
		List<ContentElement> ret = new ArrayList<>(stringIndex.size());
		for (String s : stringIndex)
			ret.add(createContentElementInDynamic(s, TAG_START));
		return ret;
	}

	/**
	 * Create ContentElement from incoming text.
	 *
	 * @param elementText	 text
	 * @param dynamicTagStart char
	 * @return created ContentElement
	 */
	private static ContentElement createContentElementInDynamic(String elementText, char dynamicTagStart) {
		if (elementText.charAt(0) != dynamicTagStart)
			return new StaticElement(elementText);
		String varName = StringUtils.strip(elementText, 1, 1);
		char c = varName.charAt(0);
		if (c == '\t' || c == ' ' || c == LINE_DELIMITER || c == '\r')
			return new StaticElement(elementText);

		if (varName != null && varName.startsWith(QUOTE) && varName.endsWith(QUOTE)){
			return new StaticElement(StringUtils.strip(varName, 1, 1));
		}

		List<String> tokens = StringUtils.tokenize(varName, TAG_START, TAG_END, ':');
		if (tokens.size() < 2)
			return new StaticElement("Wrong format \"" + varName + "\" expected: {prefix:varname[:default value]}");
		String prefix = tokens.get(0);
		String var = tokens.get(1);
		String defaultValue = tokens.size() > 2 ? tokens.get(2) : "";
		List<ContentElement> varIndex = indexSource(var);
		List<ContentElement> defValueIndex = indexSource(defaultValue);
		return new DynamicElement(elementText, prefix, varIndex, defValueIndex);
	}

	/**
	 * Executes replacement using TemplateProcessors.
	 *
	 * @param context		parameters
	 * @param contentElement element to replace
	 * @param processors	 processors which should be used
	 * @return replaced content
	 */
	private static String replaceContentElement(TemplateReplacementContext context, ContentElement contentElement, Map<String, TemplateProcessor> processors) {

		if (!contentElement.isDynamic())
			return contentElement.getElementText();

		DynamicElement dynIndex = (DynamicElement) contentElement;

		String prefix = dynIndex.getPrefix();
		String var = replaceVariables(context, dynIndex.getVariableIndex(), processors);
		String defaultValue = replaceVariables(context, dynIndex.getDefValueIndex(), processors);
		TemplateProcessor processor = processors.get(prefix);
		if (processor == null)
			return dynIndex.getElementText();
		return processor.replace(prefix, var, defaultValue, context);
	}
}
