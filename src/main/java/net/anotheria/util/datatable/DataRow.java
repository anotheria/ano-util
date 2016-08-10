package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A DataRow is basically a list of cells.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class DataRow  implements Iterable<DataCell> {
	/**
	 * The list with cells.
	 */
	private List<DataCell> cells;
	
	/**
	 * <p>Constructor for DataRow.</p>
	 */
	public DataRow(){
		this(15);
	}

	/**
	 * <p>Constructor for DataRow.</p>
	 *
	 * @param initialCapacity a int.
	 */
	public DataRow(int initialCapacity){
		cells = new ArrayList<>(initialCapacity);
	}
	
	/**
	 * <p>getCell.</p>
	 *
	 * @param index a int.
	 * @return a {@link net.anotheria.util.datatable.DataCell} object.
	 */
	public DataCell getCell(int index){
		return cells.get(index);
	}
	
	/**
	 * <p>setCell.</p>
	 *
	 * @param index a int.
	 * @param cell a {@link net.anotheria.util.datatable.DataCell} object.
	 */
	public void setCell(int index, DataCell cell){
		cells.set(index, cell);
	}
	
	/**
	 * <p>addCell.</p>
	 *
	 * @param cell a {@link net.anotheria.util.datatable.DataCell} object.
	 */
	public void addCell(DataCell cell){
		cells.add(cell);
	}
	
	void removeCell(int index){
		cells.remove(index);		
	}
		
	/**
	 * <p>getRowSize.</p>
	 *
	 * @return a int.
	 */
	public int getRowSize(){
		return cells.size();
	}
	
	/** {@inheritDoc} */
	@Override
	public Iterator<DataCell> iterator() {
		return cells.iterator();
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return cells.toString();
	}
}
