package net.anotheria.util;

/**
 * This class helps to handle URLs and its parameters.
 * Easily parse an existing URL and remove/add parameters.
 */
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UrlHelper {

	private String protocol;
	private String host;
	private int port = -1;
	private String path;
	private List<Parameter> params;
	private String reference;
	
	public UrlHelper() {
		this(null, null, -1, "", null);
	}
	
	public UrlHelper(URL url) {
		this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef());
		setQuery(url.getQuery());
	}
	
	public UrlHelper(String protocol, String host, int port, String path, String reference) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.path = path;
		this.reference = reference; 
		params = new ArrayList<Parameter>();
	}
	
	public UrlHelper(String url) {
		params = new ArrayList<Parameter>();
		
		int protocolPos = url.indexOf("://");
		
		if(protocolPos > -1) {
			setProtocol(url.substring(0, protocolPos));
			url = url.substring(protocolPos+3);
		}
		
		int referencePos = url.lastIndexOf('#');
		
		if(referencePos > -1) {
			setReference(url.substring(referencePos+1));
			url = url.substring(0, referencePos);
		}
		
		int queryPos = url.indexOf('?');
		if(queryPos > -1) {
			setQuery(url.substring(queryPos));
			url = url.substring(0, queryPos);
		}
		
		int portPos = url.indexOf(':');
		int pathPos = url.indexOf('/', portPos);
		
		if(portPos > -1) {
			setHost(url.substring(0, portPos));
			if(pathPos > -1) {
				setPort(Integer.parseInt(url.substring(portPos+1, pathPos)));
				setPath(url.substring(pathPos));
			} else {
				setPort(Integer.parseInt(url.substring(portPos+1, url.length())));
			}
		} else {
			if(pathPos > -1) {
				setHost(url.substring(0, pathPos));
				setPath(url.substring(pathPos));	
			} else {
				setHost(url);
			}
		}
		
	
	}
	
	public void setQuery(String query) {
		if(query != null) {
			if(query.startsWith("?") && query.length() > 1)
					query = query.substring(1);
			
			String[] params = StringUtils.tokenize(query, '&');
			for(int i=0; i<params.length; i++) {
				addParameter(params[i]);
			}
		}
	}
	
	public void addParameter(String paramName, String paramValue) {
		Parameter newParameter = new Parameter();
		newParameter.setName(paramName);
		newParameter.setValue(paramValue);
		
		if(paramValue != null && paramValue.length() >0) {
			int i = params.indexOf(newParameter);
			if(i>=0)
				params.set(i, newParameter);
			else
				params.add(newParameter);
		} else {
			params.remove(newParameter);
		}
	}
	
	public String getParameter(String paramName) {
		for (Parameter param : params) {
			if(param.getName().equals(paramName)) {
				return param.getValue();
			}
		}
		return null;
	}
	
	public void removeParameter(String paramName) {
		addParameter(paramName, null);
	}
	
	public void removeParameters() {
		params = new ArrayList<Parameter>();
	}
	
	public void addParameter(String paramString) {
		String[] nameValuePair = StringUtils.tokenize(paramString, '=');
		if(nameValuePair.length == 2) {
			addParameter(nameValuePair[0], nameValuePair[1]);
		} 
		else
		if(nameValuePair.length == 1) {
			addParameter(paramString, "");
		} else {
			throw new RuntimeException("Parameter does not contain a name-value pair. (e.g. name=value)");
		}
	}
	
	public void addParameters(Map<String, String> parameters) {
		Iterator<Map.Entry<String, String>> allParameters = parameters.entrySet().iterator();
		while(allParameters.hasNext()) {
			Map.Entry<String, String> param = allParameters.next();
			Object value = param.getValue();
			String key = param.getKey();
			if(value instanceof String[])
				addParameter(key, ((String[])value)[0]);
			else
				addParameter(key, value.toString());
		}
	}
	
	/*
	public void addParameters(HttpServletRequest req) {
		Enumeration allParams = req.getParameterNames();
		while(allParams.hasMoreElements()) {
			String paramName = (String) allParams.nextElement();
			String paramValue = req.getParameter(paramName);
			addParameter(paramName, paramValue);
		}
	}
	*/
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		if(protocol != null) {
			result.append(protocol);
			result.append("://");
		}
		
		if(host != null)
			result.append(host);
		
		if(port != -1 && port != 80) {
			result.append(":");
			result.append(port);
		}
		
		if(path != null) {
			result.append(path);
		}

		if(params.size()>0) {
			result.append("?");
		}
		
		result.append(parametersToString());
		
		if(reference != null && reference.length()>0) {
			result.append("#");
			result.append(reference);
		}
		
		return result.toString();
	}
	
	public String parametersToString() {
		StringBuilder result = new StringBuilder();
		Iterator<Parameter> allParams = params.iterator();
		
		while(allParams.hasNext()) {
			Parameter param = allParams.next();
			result.append(param.getName());
			result.append("=");
			try {
				result.append(URLEncoder.encode(param.getValue(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if(allParams.hasNext()) {
				result.append("&");
			}
		}
		return result.toString();
	}

	/**
	 * @return Returns the host.
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host The host to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return Returns the protocol.
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * @param protocol The protocol to set.
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * @return Returns the reference.
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference The reference to set.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getQuery() {
		StringBuilder result = new StringBuilder(); 
		Iterator<Parameter> allParams = params.iterator();
	
		while(allParams.hasNext()) {
			Parameter param = allParams.next();
			result.append(param.getName());
			result.append("=");
			try {
				result.append(URLEncoder.encode( param.getValue(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if(allParams.hasNext()) {
				result.append("&");
			}
		}
		
		return result.toString();
	}
	
	public class Parameter {
		/**
		 * Parameter name.
		 */
		private String name;
		/**
		 * Parameter value.
		 */
		private String value;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		@Override public boolean equals(Object o) {
			return o instanceof Parameter && 
					name.equals(((Parameter)o).getName());
		}
		
		@Override public int hashCode(){
			return name.hashCode();
		}
	}
}
