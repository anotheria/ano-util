package net.anotheria.util.content.template.processors;

import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;
import net.anotheria.util.content.template.processors.variables.ConditionProcessorNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConditionTemplateProcessor as  TemplateProcessor.
 *
 * @author h3llka
 */
public class ConditionTemplateProcessor implements TemplateProcessor {
	/**
	 * Logger.
	 */
	private final Logger log = LoggerFactory.getLogger(ConditionTemplateProcessor.class);

	@Override
	public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
		//sorry!  next If block -- is just the stupid hack!  due  to 'if' prefix name (
		if (ConditionProcessorNames.iF.getPrefixName().equals(aPrefix)) {
			return ConditionProcessorNames.iF.executeReplacement(aVariable, aDefValue);
		} else {
			try {
				return ConditionProcessorNames.valueOf(ConditionProcessorNames.class, aPrefix).executeReplacement(aVariable, aDefValue);
			} catch (Exception ignored) {
				log.error("An exceptions has been occurred while trying to replace variable. where prefix=" + aPrefix + " variable=" + aVariable + " defaultValue=" + aDefValue, ignored);
			}
		}
		return "";
	}
}

