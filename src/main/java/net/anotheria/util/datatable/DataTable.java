package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * This class represents a table with untyped data, similar to a csv file. 
 * @author lrosenberg
 */
public class DataTable implements Iterable<DataRow> {
	/**
	 * The header of the table.
	 */
	private DataHeader header;
	/**
	 * A list with rows.
	 */
	private List<DataRow> rows;
	
	public DataTable(){
		this(10);
	}

	public DataTable(int aInitialCapacity){
		rows = new ArrayList<>(aInitialCapacity);
	}
	
	public DataHeader getHeader() {
		return header;
	}
	public void setHeader(DataHeader aHeader) {
		this.header = aHeader;
	}
	
	public DataRow getRow(int aIndex){
		return rows.get(aIndex);
	}
	
	public void setRow(int aIndex, DataRow aRow){
		rows.set(aIndex, aRow);
	}
	
	public void addRow(DataRow aRow){
		rows.add(aRow);
	}
	
	public void removeRow(int aIndex){
		rows.remove(aIndex);
	}
		
	public void removeRow(DataRow aRow){
		rows.remove(aRow);
	}
		
	public int getRowIndex(DataRow aRow){
		return rows.indexOf(aRow);
	}
	
	public int getRowsSize(){
		return rows.size();
	}
	
	@Override
	public Iterator<DataRow> iterator() {
		return rows.iterator();
	}
	
	@Override
	public String toString(){
		String ret = "";
		ret += "HEADER:";
		ret += "\n";
		ret += header.toString();
		ret += "\n";
		ret += "ROWS:";
		ret += "\n";
		for(DataRow r: this){
			ret += r.toString();
			ret += "\n";
		}
		return ret;
	}
}
