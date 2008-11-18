package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataTable implements Iterable<DataRow>{

	private DataHeader header;
	private List<DataRow> rows;
	
	public DataTable(){
		this(10);
	}

	public DataTable(int initialCapacity){
		rows = new ArrayList<DataRow>(initialCapacity);
	}
	
	public DataHeader getHeader() {
		return header;
	}
	public void setHeader(DataHeader header) {
		this.header = header;
	}
	
	public DataRow getRow(int index){
		return rows.get(index);
	}
	
	public void setRow(int index, DataRow row){
		rows.set(index, row);
	}
	
	public void addRow(DataRow row){
		rows.add(row);
	}
	
	public void removeRow(int index){
		rows.remove(index);
	}
		
	public void removeRow(DataRow row){
		rows.remove(row);
	}
		
	public int getRowIndex(DataRow row){
		return rows.indexOf(row);
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
