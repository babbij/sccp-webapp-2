package com.goodforgoodbusiness.webapp;

import spark.route.HttpMethod;

public class Resource {
	public static Resource get(String path) {
		return new Resource(HttpMethod.get, path);
	}
	
	public static Resource post(String path) {
		return new Resource(HttpMethod.post, path);
	}
	
	protected final HttpMethod method;
	protected final String path;
	
	public Resource(HttpMethod method, String path) {
		this.method = method;
		this.path = path;
	}
	
	@Override
	public int hashCode() {
		return method.hashCode() ^ path.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (o instanceof Resource) {
			return path.equals(((Resource)o).path) && method.equals(((Resource)o).method);
		}
		
		return false;
	}
}
