package net.anotheria.util.csv;

import java.util.List;

import net.anotheria.util.StringUtils;
import net.anotheria.util.datatable.DataCell;
import net.anotheria.util.datatable.DataHeader;
import net.anotheria.util.datatable.DataRow;
import net.anotheria.util.datatable.DataTable;

public class CSVParser {
	
	public static final char DEFAULT_ROWS_SEPARATOR = '\n';
	
	private static String normalize(String csvSource){
		return StringUtils.removeChar(csvSource, '\r');
	}
	
	public static DataTable parse(String csvSource){
		return parse(csvSource, true);
	}
	
	public static DataTable parse(String csvSource, boolean hasHeader){
		return parse(normalize(csvSource), hasHeader, DEFAULT_ROWS_SEPARATOR);
	}
	
	public static DataTable parse(String csvSource, boolean hasHeader,char rowsSeparator){
		String[] rows = StringUtils.tokenize(csvSource, rowsSeparator);
//		System.out.println("Found " + rows.length + " row(s)!");
		
		if(rows.length == 0)
			throw new RuntimeException("No rows found!");
		
		DataTable ret = new DataTable(rows.length);

		if(hasHeader)
			ret.setHeader(toDataHeader(parseRow(rows[0])));
		
		for(int i = hasHeader?1:0; i < rows.length; i++)
			ret.addRow(parseRow(rows[i]));
		
		
		return ret;
	}
	
	private static DataRow parseRow(String row){
		List<String> tokens = StringUtils._tokenize(row, '"', '"', ',');
		DataRow ret = new DataRow();
		for(String t: tokens){
			if(StringUtils.isSurroundedWith(t, '"', '"'))
				t = StringUtils.removeSurround(t);
			ret.addCell(new DataCell(t));
		}
		return ret;
	}
	
	private static DataHeader toDataHeader(DataRow headerRow){
		DataHeader ret = new DataHeader();
		for(DataCell cell: headerRow)
			ret.addHeader(cell.getValueAsString());
		return ret;
	}
	

}
