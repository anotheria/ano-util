package net.anotheria.util.content.template.processors;

import net.anotheria.util.StringUtils;
import net.anotheria.util.content.template.TemplateProcessor;
import net.anotheria.util.content.template.TemplateReplacementContext;
import net.anotheria.util.content.template.processors.variables.ConstantVariables;

/**
 * Constants template processor.
 *
 * @author h3llka
 * @version $Id: $Id
 */
public class ConstantsTemplateProcessor implements TemplateProcessor {

	/** Constant <code>PREFIX="c"</code> */
	public static final String PREFIX = "c";

	/** {@inheritDoc} */
	@Override
	public String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext) {
		if (PREFIX.equals(aPrefix) && !StringUtils.isEmpty(aVariable)) {
			String value = ConstantVariables.getConstantValue(aVariable);
			return value != null ? value : "Unknown constants: " + aVariable;
		}
		return aDefValue;
	}
}
