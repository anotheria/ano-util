package net.anotheria.util.datatable;

import net.anotheria.util.Date;
/**
 * A single cell in the table which contains untyped data.
 * @author lrosenberg
 */
public class DataCell {

	/**
	 * The value of the cell.
	 */
	private Object value;

	public DataCell(){
	}
	
	public DataCell(Object aValue){
		value = aValue;
	}
	
	public void setValue(Object aValue) {
		this.value = aValue;
	}

	public Object getValue() {
		return value;
	}
	
	public String getValueAsString() {
		return value.toString();
	}

	public int getValueAsInteger() {
		return value instanceof Integer? (Integer)value : Integer.parseInt(getValueAsString());
	}
	
	public long getValueAsLong() {
		return value instanceof Long? (Long)value : Long.parseLong(getValueAsString());
	}
	
	public float getValueAsFloat() {
		return value instanceof Float? (Float)value : Float.parseFloat(getValueAsString());
	}
	
	public double getValueAsDouble() {
		return value instanceof Double? (Double)value : Double.parseDouble(getValueAsString());
	}
	
	public Date getValueAsDate() {
		return value instanceof Date? (Date)value : Date.parse(getValueAsString());
	}
	
	@Override
	public String toString(){
		return getValueAsString();
	}
}
