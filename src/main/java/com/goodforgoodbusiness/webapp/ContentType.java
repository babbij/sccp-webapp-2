package com.goodforgoodbusiness.webapp;

public enum ContentType {
	json("application/json"),
	text("text/plain"),
	
	sparql_query("application/sparql-query"),
	sparql_update("application/sparql-update")
	
	;
	
	private final String contentTypeString;
	
	private ContentType(String contentTypeString) {
		this.contentTypeString = contentTypeString;
	}
	
	public String getContentTypeString() {
		return contentTypeString;
	}
	
	@Override
	public String toString() {
		return contentTypeString;
	}
}
