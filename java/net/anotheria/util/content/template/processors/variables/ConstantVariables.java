package net.anotheria.util.content.template.processors.variables;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains constants for Template && Variable processors.
 *
 * @author h3llka
 */
public final class ConstantVariables {

	/**
	 * Map with constants.
	 */
	private static Map<String, String> constants = new HashMap<String, String>();

	static {
		constants.put("spacer", "&nbsp;");
		constants.put("euro", "&euro;");
		constants.put("copyright", "&#169;");
		constants.put("EUR", "&euro;");
		constants.put("CHF", "CHF");
		constants.put("form", "form");
		constants.put("input", "input");
		constants.put("textarea", "textarea");
		constants.put("lbrace", "{");
		constants.put("rbrace", "}");
		constants.put("greatThan", "&gt;");
		constants.put("lessThan", "&lt;");
		constants.put("gt", "&gt;");
		constants.put("lt", "&lt;");
		constants.put("rbrace", "}");
		constants.put("colon", ":");
		constants.put("semicolon", ";");
		constants.put("raquo", "&raquo;");
		constants.put("laquo", "&laquo;");
		constants.put("lang", "lang");
	}

	/**
	 * Private Constructor.
	 */
	private ConstantVariables() {
	}

	/**
	 * Return all constants.
	 *
	 * @return constants collection
	 */
	public static Map<String, String> getConstants() {
		return constants;
	}

	/**
	 * Constant will be returned if exists.
	 *
	 * @param key string key
	 * @return string value
	 */
	public static String getConstantValue(String key) {
		return constants.get(key);
	}
}
