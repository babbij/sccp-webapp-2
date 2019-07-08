package com.goodforgoodbusiness.webapp;

public enum ContentType {
	JSON("application/json"),
	text("text/plain"),
	
	SPARQL_QUERY("application/sparql-query"),
	SPARQL_UPDATE("application/sparql-update")
	
	;
	
	private final String contentTypeString;
	
	private ContentType(String contentTypeString) {
		this.contentTypeString = contentTypeString;
	}
	
	public String getContentTypeString() {
		return contentTypeString;
	}
	
	public boolean matches(String contentTypeHeader) {
		return (contentTypeHeader != null) && contentTypeHeader.equalsIgnoreCase(contentTypeString);
	}
	
	@Override
	public String toString() {
		return contentTypeString;
	}
}
