package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * This class represents a table with untyped data, similar to a csv file.
 *
 * @author lrosenberg
 * @version $Id: $Id
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
	
	/**
	 * <p>Constructor for DataTable.</p>
	 */
	public DataTable(){
		this(10);
	}

	/**
	 * <p>Constructor for DataTable.</p>
	 *
	 * @param aInitialCapacity a int.
	 */
	public DataTable(int aInitialCapacity){
		rows = new ArrayList<>(aInitialCapacity);
	}
	
	/**
	 * <p>Getter for the field <code>header</code>.</p>
	 *
	 * @return a {@link net.anotheria.util.datatable.DataHeader} object.
	 */
	public DataHeader getHeader() {
		return header;
	}
	/**
	 * <p>Setter for the field <code>header</code>.</p>
	 *
	 * @param aHeader a {@link net.anotheria.util.datatable.DataHeader} object.
	 */
	public void setHeader(DataHeader aHeader) {
		this.header = aHeader;
	}
	
	/**
	 * <p>getRow.</p>
	 *
	 * @param aIndex a int.
	 * @return a {@link net.anotheria.util.datatable.DataRow} object.
	 */
	public DataRow getRow(int aIndex){
		return rows.get(aIndex);
	}
	
	/**
	 * <p>setRow.</p>
	 *
	 * @param aIndex a int.
	 * @param aRow a {@link net.anotheria.util.datatable.DataRow} object.
	 */
	public void setRow(int aIndex, DataRow aRow){
		rows.set(aIndex, aRow);
	}
	
	/**
	 * <p>addRow.</p>
	 *
	 * @param aRow a {@link net.anotheria.util.datatable.DataRow} object.
	 */
	public void addRow(DataRow aRow){
		rows.add(aRow);
	}
	
	/**
	 * <p>removeRow.</p>
	 *
	 * @param aIndex a int.
	 */
	public void removeRow(int aIndex){
		rows.remove(aIndex);
	}
		
	/**
	 * <p>removeRow.</p>
	 *
	 * @param aRow a {@link net.anotheria.util.datatable.DataRow} object.
	 */
	public void removeRow(DataRow aRow){
		rows.remove(aRow);
	}
		
	/**
	 * <p>getRowIndex.</p>
	 *
	 * @param aRow a {@link net.anotheria.util.datatable.DataRow} object.
	 * @return a int.
	 */
	public int getRowIndex(DataRow aRow){
		return rows.indexOf(aRow);
	}
	
	/**
	 * <p>getRowsSize.</p>
	 *
	 * @return a int.
	 */
	public int getRowsSize(){
		return rows.size();
	}
	
	/** {@inheritDoc} */
	@Override
	public Iterator<DataRow> iterator() {
		return rows.iterator();
	}
	
	/** {@inheritDoc} */
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

	public String toCSV(){
		StringBuilder ret = new StringBuilder();
		ret.append(header.toCSV());
		ret.append("\n");
		for(DataRow r: this){
			ret.append(r.toCSV());
			ret.append("\n");
		}
		return ret.toString();
	}
}
