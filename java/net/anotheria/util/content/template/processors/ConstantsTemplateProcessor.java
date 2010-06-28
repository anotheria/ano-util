package net.anotheria.util.content.template.processors;

import net.anotheria.util.StringUtils;
import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;
import net.anotheria.util.content.template.processors.variables.ConstantVariables;

/**
 * Constants template processor.
 *
 * @author h3llka
 */
public class ConstantsTemplateProcessor implements TemplateProcessor {

	public static final String PREFIX = "c";

	@Override
	public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
		if (PREFIX.equals(aPrefix) && !StringUtils.isEmpty(aVariable)) {
			String value = ConstantVariables.getConstantValue(aVariable);
			return value != null ? value : "Unknown constants: " + aVariable;
		}
		return aDefValue;
	}
}
