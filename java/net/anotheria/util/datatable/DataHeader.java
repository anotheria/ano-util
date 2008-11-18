package net.anotheria.util.datatable;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.util.StringUtils;

public class DataHeader {
	
	private List<String> headers;
	
	public DataHeader(){
		headers = new ArrayList<String>();
	}
	
	public String[] getHeaders(){
		return headers.toArray(new String[0]);
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
