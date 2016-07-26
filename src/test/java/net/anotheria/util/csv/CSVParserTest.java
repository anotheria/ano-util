package net.anotheria.util.csv;

import net.anotheria.util.datatable.DataRow;
import net.anotheria.util.datatable.DataTable;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
		
		assertEquals("CSVParser has found incorrect number of tokens!", tests.length, dr.getRowSize());
		for (int i = 0; i < dr.getRowSize(); i++) {
			assertEquals(tests[i], dr.getCell(i).toString());
		}
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
