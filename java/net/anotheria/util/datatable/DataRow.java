package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataRow  implements Iterable<DataCell>{

	private List<DataCell> cells;
	
	public DataRow(){
		this(15);
	}

	public DataRow(int initialCapacity){
		cells = new ArrayList<DataCell>(initialCapacity);
	}
	
	public DataCell getCell(int index){
		return cells.get(index);
	}
	
	public void setCell(int index, DataCell cell){
		cells.set(index, cell);
	}
	
	public void addCell(DataCell cell){
		cells.add(cell);
	}
	
	void removeCell(int index){
		cells.remove(index);		
	}
		
	public int getRowSize(){
		return cells.size();
	}
	
	@Override
	public Iterator<DataCell> iterator() {
		return cells.iterator();
	}
	
	@Override
	public String toString(){
//		String ret = "[";
//		ret += StringUtils.concatenateTokens(cells, ";");
//		ret += "]";
		String ret = cells.toString();
		return ret;
	}
}
