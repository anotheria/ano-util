package net.anotheria.util.datatable;

import net.anotheria.util.Date;
import net.anotheria.util.StringUtils;

/**
 * A single cell in the table which contains untyped data.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class DataCell {

	/**
	 * The value of the cell.
	 */
	private Object value;

	/**
	 * <p>Constructor for DataCell.</p>
	 */
	public DataCell(){
	}
	
	/**
	 * <p>Constructor for DataCell.</p>
	 *
	 * @param aValue a {@link java.lang.Object} object.
	 */
	public DataCell(Object aValue){
		value = aValue;
	}
	
	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param aValue a {@link java.lang.Object} object.
	 */
	public void setValue(Object aValue) {
		this.value = aValue;
	}

	/**
	 * <p>Getter for the field <code>value</code>.</p>
	 *
	 * @return a {@link java.lang.Object} object.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * <p>getValueAsString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValueAsString() {
		return value.toString();
	}

	/**
	 * <p>getValueAsInteger.</p>
	 *
	 * @return a int.
	 */
	public int getValueAsInteger() {
		return value instanceof Integer? (Integer)value : Integer.parseInt(getValueAsString());
	}
	
	/**
	 * <p>getValueAsLong.</p>
	 *
	 * @return a long.
	 */
	public long getValueAsLong() {
		return value instanceof Long? (Long)value : Long.parseLong(getValueAsString());
	}
	
	/**
	 * <p>getValueAsFloat.</p>
	 *
	 * @return a float.
	 */
	public float getValueAsFloat() {
		return value instanceof Float? (Float)value : Float.parseFloat(getValueAsString());
	}
	
	/**
	 * <p>getValueAsDouble.</p>
	 *
	 * @return a double.
	 */
	public double getValueAsDouble() {
		return value instanceof Double? (Double)value : Double.parseDouble(getValueAsString());
	}
	
	/**
	 * <p>getValueAsDate.</p>
	 *
	 * @return a {@link net.anotheria.util.Date} object.
	 */
	public Date getValueAsDate() {
		return value instanceof Date? (Date)value : Date.parse(getValueAsString());
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return getValueAsString();
	}

	public String toCSV(){
		String value = getValueAsString();
		value = StringUtils.replace(value, '\"', '\'');
		value = StringUtils.replace(value, "'", "\"\"");
		return StringUtils.replace(value, '\n', '|');
	}
}
