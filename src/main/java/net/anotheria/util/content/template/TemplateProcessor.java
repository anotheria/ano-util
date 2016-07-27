package net.anotheria.util.content.template;

/**
 * Current interface describes TemplateProcessor - for runtime variables replacement with
 * some values.
 *
 * @author h3llka
 */
public interface TemplateProcessor {
	/**
	 * Execute expression replace in text.
	 *
	 * @param aPrefix   processor prefix
	 * @param aVariable processor variable name
	 * @param aDefValue default value
	 * @param aContext  additional replacement parameters
	 */
	String replace(String aPrefix, String aVariable, String aDefValue, TemplateReplacementContext aContext);

}
