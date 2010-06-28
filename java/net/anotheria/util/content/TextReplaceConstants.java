package net.anotheria.util.content;

/**
 * Constants for TemplateUtilityClass, and actually VariablesUtility (ano-site).
 *
 * @author h3llka
 */
public final class TextReplaceConstants {

	/**
	 * The default value if no 'fallback' default value is specified.
	 */
	public static final String DEFAULT_VALUE = "*UNSET*";
	/**
	 * Line delimiter for line parsing. The Variables utility only supports expression in a single line.
	 */
	public static final char LINE_DELIMITER = '\n';
	/**
	 * Word delimiter.
	 */
	public static final char WORD_DELIMITER = ' ';
	/**
	 * A tag (expression) starts with this character.
	 */
	public static final char TAG_START = '{';
	/**
	 * A tag (expression) ends with this character.
	 */
	public static final char TAG_END = '}';

	/**
	 * Constant for quoting.
	 */
	public static final String QUOTE = "\"";
	/**
	 * Escape char for special characters in the expressions.
	 */
	public static final char ESCAPE_CHAR = '\\';

	/**
	 * Constructor.
	 */
	private TextReplaceConstants() {
	}

}
