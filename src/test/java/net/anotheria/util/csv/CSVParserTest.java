package net.anotheria.util.csv;

import net.anotheria.util.datatable.DataRow;
import net.anotheria.util.datatable.DataTable;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVParserTest {

	@Test public void testUnescaping() {
		//some amount of test strings with symbols that need escaping in CSV - I know 2 of them - comma and quote symbols.
		String[] tests = {"abcdef","\"abcdef","abcdef\"","\"abcdef\"","a,b,\ncdef","ab,\"cdef","abc\",\"def","abc\"def",",\"ab\"\"cdef\"","\"abc\",,def","abc,\"d,e\"f", "\"\"\"\"\"fdggdfgdf"};
		
		//actually tests escape/unescape functionality from commons-lang from apache
		String escaped, unescaped;
		for (String test : tests) {
			escaped = StringEscapeUtils.escapeCsv(test);
			unescaped = StringEscapeUtils.unescapeCsv(escaped);
			assertEquals(test, unescaped);
		}
		
		//test if CSVParser will unescape successfully
		for (String test : tests) {
			escaped = StringEscapeUtils.escapeCsv(test);
			unescaped = CSVParser.parse(escaped, ',', '\uFFFF', false).getRow(0).getCell(0).toString();
			assertEquals(test, unescaped);
		}
		
		//test if CSVParser will both parse and unescape successfully
		StringBuilder csv = new StringBuilder();
		for (String test : tests) {
			csv.append(StringEscapeUtils.escapeCsv(test)).append(',');
		}
		csv.deleteCharAt(csv.length() - 1);
		DataRow dr = CSVParser.parse(csv.toString(), ',', '\uFFFF', false).getRow(0);
		
		assertEquals(tests.length, dr.getRowSize(), "CSVParser has found incorrect number of tokens!");
		for (int i = 0; i < dr.getRowSize(); i++) {
			assertEquals(tests[i], dr.getCell(i).toString());
		}
	}
	
	/**
	 * A row ending in a separator has a last field, and it is empty. The row tokenizer used to drop
	 * it, so every row of a file whose last column is empty came back one cell short of the header —
	 * which shows up as a column that silently does not exist rather than as an error.
	 */
	@Test public void testTrailingEmptyField() {
		DataTable dt = CSVParser.parse("a,b,c\n1,2,\n");

		assertEquals(3, dt.getHeader().getHeaders().length);
		assertEquals(1, dt.getRowsSize());
		assertEquals(3, dt.getRow(0).getRowSize(), "the empty third field is still a field");
		assertEquals("1", dt.getRow(0).getCell(0).toString());
		assertEquals("2", dt.getRow(0).getCell(1).toString());
		assertEquals("", dt.getRow(0).getCell(2).toString());
	}

	@Test public void testSeveralTrailingEmptyFields() {
		DataTable dt = CSVParser.parse("a,b,c,d\n1,,,\n");

		assertEquals(4, dt.getRow(0).getRowSize());
		assertEquals("1", dt.getRow(0).getCell(0).toString());
		assertEquals("", dt.getRow(0).getCell(3).toString());
	}

	@Test public void testHandlingNewLinesInText() {
		String[] rows = {"1a\n1b1c\n   1d", "2x\n2y", "sdfss\"dfs"};
		StringBuilder csv = new StringBuilder();
		for (String row : rows) {
			csv.append(StringEscapeUtils.escapeCsv(row)).append('\n');
		}
		csv.deleteCharAt(csv.length() - 1);

		DataTable dt = CSVParser.parse(csv.toString(), false);

		assertEquals(rows.length, dt.getRowsSize());
		for (int i = 0; i < rows.length; i++) {
			assertEquals(1, dt.getRow(i).getRowSize());
			assertEquals(rows[i], dt.getRow(i).getCell(0).toString());
		}
	}
	
}
