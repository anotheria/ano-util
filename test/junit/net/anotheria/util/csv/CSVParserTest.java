package net.anotheria.util.csv;

import net.anotheria.util.datatable.DataRow;

import org.apache.commons.lang.StringEscapeUtils;
import static org.junit.Assert.*;
import org.junit.Test;

public class CSVParserTest {

	@Test public void testUnescaping() {
		//some amount of test strings with symbols that need escaping in CSV - I know 2 of them - comma and quote symbols.
		String[] tests = {"abcdef","\"abcdef","abcdef\"","\"abcdef\"","a,b,cdef","ab,\"cdef","abc\",\"def","abc\"def",",\"ab\"\"cdef\"","\"abc\",,def","abc,\"d,e\"f", "\"\"\"\"\"fdggdfgdf"};
		
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
			unescaped = CSVParser.parse(escaped, false).getRow(0).getCell(0).toString();
			assertEquals(test, unescaped);
		}
		
		//test if CSVParser will both parse and unescape successfully
		StringBuilder csv = new StringBuilder();
		for (String test : tests) {
			csv.append(StringEscapeUtils.escapeCsv(test)).append(",");
		}
		csv.deleteCharAt(csv.length() - 1);
		DataRow dr = CSVParser.parse(csv.toString(), false).getRow(0);
		
		assertEquals("CSVParser has found incorrect number of tokens!", tests.length, dr.getRowSize());
		for (int i = 0; i < dr.getRowSize(); i++) {
			assertEquals(tests[i], dr.getCell(i).toString());
		}
	}
}
