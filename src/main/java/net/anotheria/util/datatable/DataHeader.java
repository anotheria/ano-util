package net.anotheria.util.datatable;

import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A header of the table.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class DataHeader {
	/**
	 * List of names of headers.
	 */
	private List<String> headers;
	
	/**
	 * <p>Constructor for DataHeader.</p>
	 */
	public DataHeader(){
		headers = new ArrayList<>();
	}
	
	/**
	 * <p>Getter for the field <code>headers</code>.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public String[] getHeaders(){
		return headers.toArray(new String[headers.size()]);
	}
	
	/**
	 * <p>getHeader.</p>
	 *
	 * @param index a int.
	 * @return a {@link java.lang.String} object.
	 */
	public String getHeader(int index){
		return headers.get(index);
	}
	
	/**
	 * <p>addHeader.</p>
	 *
	 * @param header a {@link java.lang.String} object.
	 */
	public void addHeader(String header){
		headers.add(header);
	}
	
	/**
	 * <p>setHeader.</p>
	 *
	 * @param index a int.
	 * @param header a {@link java.lang.String} object.
	 */
	public void setHeader(int index, String header){
		headers.set(index, header);
	}
	
	/**
	 * <p>getIndex.</p>
	 *
	 * @param header a {@link java.lang.String} object.
	 * @return a int.
	 */
	public int getIndex(String header){
		return headers.indexOf(header);
	}
	
	/**
	 * <p>hasHeader.</p>
	 *
	 * @param header a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean hasHeader(String header){
		return headers.contains(header);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		String ret = "[";
		ret += StringUtils.concatenateTokens(headers, ";");
		ret += "]";
		return ret;
	}

	public String toCSV() {
		StringBuilder ret = new StringBuilder();
		for (String h : headers) {
			if (ret.length() > 0)
				ret.append(',');
			ret.append("\"" + h + "\"");
		}

		return ret.toString();
	}
}
