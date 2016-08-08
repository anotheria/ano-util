package net.anotheria.util.datatable;

import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A header of the table.
 * @author lrosenberg
 *
 */
public class DataHeader {
	/**
	 * List of names of headers.
	 */
	private List<String> headers;
	
	public DataHeader(){
		headers = new ArrayList<>();
	}
	
	public String[] getHeaders(){
		return headers.toArray(new String[headers.size()]);
	}
	
	public String getHeader(int index){
		return headers.get(index);
	}
	
	public void addHeader(String header){
		headers.add(header);
	}
	
	public void setHeader(int index, String header){
		headers.set(index, header);
	}
	
	public int getIndex(String header){
		return headers.indexOf(header);
	}
	
	public boolean hasHeader(String header){
		return headers.contains(header);
	}
	
	@Override
	public String toString(){
		String ret = "[";
		ret += StringUtils.concatenateTokens(headers, ";");
		ret += "]";
		return ret;
	}
}
