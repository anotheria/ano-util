package net.anotheria.util.csv;

import java.util.List;

import net.anotheria.util.StringUtils;
import net.anotheria.util.datatable.DataCell;
import net.anotheria.util.datatable.DataHeader;
import net.anotheria.util.datatable.DataRow;
import net.anotheria.util.datatable.DataTable;

public class CSVParser {
	
	public static final char DEFAULT_VALUES_SEPARATOR = ',';
	public static final char DEFAULT_ROWS_SEPARATOR = '\n';
	
	private static String normalize(String csvSource){
		return StringUtils.removeChar(csvSource, '\r');
	}
	
	public static DataTable parse(String csvSource){
		return parse(csvSource, true);
	}
	
	public static DataTable parse(String csvSource, boolean hasHeader){
		return parse(normalize(csvSource),DEFAULT_VALUES_SEPARATOR, hasHeader);
	}
	
	public static DataTable parse(String csvSource, char valuesSeparator, boolean hasHeader){
		return parse(normalize(csvSource),valuesSeparator, DEFAULT_ROWS_SEPARATOR, hasHeader);
	}
	
	public static DataTable parse(String csvSource, char valuesSeparator, char rowsSeparator, boolean hasHeader){
		String[] rows = StringUtils.tokenize(csvSource, rowsSeparator);
//		System.out.println("Found " + rows.length + " row(s)!");
		
		if(rows.length == 0)
			throw new RuntimeException("No rows found!");
		
		DataTable ret = new DataTable(rows.length);

		if(hasHeader)
			ret.setHeader(toDataHeader(parseRow(rows[0], valuesSeparator)));
		
		for(int i = hasHeader?1:0; i < rows.length; i++)
			ret.addRow(parseRow(rows[i], valuesSeparator));
		
		
		return ret;
	}
	
	private static DataRow parseRow(String row, char valuesSeparator){
		try{
			List<String> tokens = StringUtils._tokenize(row, '"', '"', false,valuesSeparator);
			DataRow ret = new DataRow();
			for(String t: tokens){
				if(StringUtils.isSurroundedWith(t, '"', '"'))
					t = StringUtils.removeSurround(t);
				ret.addCell(new DataCell(t));
			}
			return ret;
		}catch(RuntimeException e){
			throw new RuntimeException("Could not parse CSV Row: " + row, e);
		}
	}
	
	private static DataHeader toDataHeader(DataRow headerRow){
		DataHeader ret = new DataHeader();
		for(DataCell cell: headerRow)
			ret.addHeader(cell.getValueAsString());
		return ret;
	}
	

}
