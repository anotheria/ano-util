package net.anotheria.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>UrlHelper class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class UrlHelper {
	/**
	 * Url protocol.
	 */
	private String protocol;
	/**
	 * Target host.
	 */
	private String host;
	/**
	 * Target port.
	 */
	private int port = -1;
	/**
	 * Context path.
	 */
	private String path;
	/**
	 * Url parameters.
	 */
	private List<Parameter> params;
	private String reference;
	
	/**
	 * <p>Constructor for UrlHelper.</p>
	 */
	public UrlHelper() {
		this(null, null, -1, "", null);
	}
	
	/**
	 * <p>Constructor for UrlHelper.</p>
	 *
	 * @param url a {@link java.net.URL} object.
	 */
	public UrlHelper(URL url) {
		this(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), url.getRef());
		setQuery(url.getQuery());
	}
	
	/**
	 * <p>Constructor for UrlHelper.</p>
	 *
	 * @param protocol a {@link java.lang.String} object.
	 * @param host a {@link java.lang.String} object.
	 * @param port a int.
	 * @param path a {@link java.lang.String} object.
	 * @param reference a {@link java.lang.String} object.
	 */
	public UrlHelper(String protocol, String host, int port, String path, String reference) {
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.path = path;
		this.reference = reference; 
		params = new ArrayList<>();
	}
	
	/**
	 * <p>Constructor for UrlHelper.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public UrlHelper(String url) {
		params = new ArrayList<>();
		
		int protocolPos = url.indexOf("://");
		
		if(protocolPos > -1) {
            this.protocol = url.substring(0, protocolPos);
            url = url.substring(protocolPos+3);
		}
		
		int referencePos = url.lastIndexOf('#');
		
		if(referencePos > -1) {
            this.reference = url.substring(referencePos+1);
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
            this.host = url.substring(0, portPos);
            if(pathPos > -1) {
                this.port = Integer.parseInt(url.substring(portPos+1, pathPos));
                this.path = url.substring(pathPos);
            } else {
                this.port = Integer.parseInt(url.substring(portPos+1, url.length()));
            }
		} else {
			if(pathPos > -1) {
                this.host = url.substring(0, pathPos);
                this.path = url.substring(pathPos);
            } else {
                this.host = url;
            }
		}
		
	
	}
	
	/**
	 * <p>setQuery.</p>
	 *
	 * @param query a {@link java.lang.String} object.
	 */
	public void setQuery(String query) {
		if(query != null) {
			if(query.startsWith("?") && query.length() > 1)
					query = query.substring(1);
			
			String[] params = StringUtils.tokenize(query, '&');
			for (String param : params) {
				addParameter(param);
			}
		}
	}
	
	/**
	 * <p>addParameter.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 * @param paramValue a {@link java.lang.String} object.
	 */
	public void addParameter(String paramName, String paramValue) {
		Parameter newParameter = new Parameter();
		newParameter.setName(paramName);
		newParameter.setValue(paramValue);
		
		if(paramValue != null && !paramValue.isEmpty()) {
			int i = params.indexOf(newParameter);
			if(i>=0)
				params.set(i, newParameter);
			else
				params.add(newParameter);
		} else {
			params.remove(newParameter);
		}
	}
	
	/**
	 * <p>getParameter.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getParameter(String paramName) {
		for (Parameter param : params) {
			if(param.getName().equals(paramName)) {
				return param.getValue();
			}
		}
		return null;
	}
	
	/**
	 * <p>removeParameter.</p>
	 *
	 * @param paramName a {@link java.lang.String} object.
	 */
	public void removeParameter(String paramName) {
		addParameter(paramName, null);
	}
	
	/**
	 * <p>removeParameters.</p>
	 */
	public void removeParameters() {
		params = new ArrayList<>();
	}
	
	/**
	 * <p>addParameter.</p>
	 *
	 * @param paramString a {@link java.lang.String} object.
	 */
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
	
	/**
	 * <p>addParameters.</p>
	 *
	 * @param parameters a {@link java.util.Map} object.
	 */
	public void addParameters(Map<String, String> parameters) {
		for (Map.Entry<String, String> param : parameters.entrySet()) {
			Object value = param.getValue();
			String key = param.getKey();
			if (value instanceof String[])
				addParameter(key, ((String[]) value)[0]);
			else
				addParameter(key, value.toString());
		}
	}
	
	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
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
			result.append(':');
			result.append(port);
		}
		
		if(path != null) {
			result.append(path);
		}

		if(!params.isEmpty()) {
			result.append('?');
		}
		
		result.append(parametersToString());
		
		if(reference != null && !reference.isEmpty()) {
			result.append('#');
			result.append(reference);
		}
		
		return result.toString();
	}
	
	/**
	 * <p>parametersToString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String parametersToString() {
		StringBuilder result = new StringBuilder();
		Iterator<Parameter> allParams = params.iterator();
		
		while(allParams.hasNext()) {
			Parameter param = allParams.next();
			result.append(param.getName());
			result.append('=');
			try {
				result.append(URLEncoder.encode(param.getValue(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if(allParams.hasNext()) {
				result.append('&');
			}
		}
		return result.toString();
	}

	/**
	 * <p>Getter for the field <code>host</code>.</p>
	 *
	 * @return Returns the host.
	 */
	public String getHost() {
		return host;
	}
	/**
	 * <p>Setter for the field <code>host</code>.</p>
	 *
	 * @param host The host to set.
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * <p>Getter for the field <code>path</code>.</p>
	 *
	 * @return Returns the path.
	 */
	public String getPath() {
		return path;
	}
	/**
	 * <p>Setter for the field <code>path</code>.</p>
	 *
	 * @param path The path to set.
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * <p>Getter for the field <code>port</code>.</p>
	 *
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * <p>Setter for the field <code>port</code>.</p>
	 *
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * <p>Getter for the field <code>protocol</code>.</p>
	 *
	 * @return Returns the protocol.
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * <p>Setter for the field <code>protocol</code>.</p>
	 *
	 * @param protocol The protocol to set.
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	/**
	 * <p>Getter for the field <code>reference</code>.</p>
	 *
	 * @return Returns the reference.
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * <p>Setter for the field <code>reference</code>.</p>
	 *
	 * @param reference The reference to set.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	/**
	 * <p>getQuery.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getQuery() {
		StringBuilder result = new StringBuilder(); 
		Iterator<Parameter> allParams = params.iterator();
	
		while(allParams.hasNext()) {
			Parameter param = allParams.next();
			result.append(param.getName());
			result.append('=');
			try {
				result.append(URLEncoder.encode( param.getValue(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			if(allParams.hasNext()) {
				result.append('&');
			}
		}
		
		return result.toString();
	}
	
	public static class Parameter {
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
					name.equals(((Parameter) o).name);
		}
		
		@Override public int hashCode(){
			return name.hashCode();
		}
	}
}
