package com.goodforgoodbusiness.webapp.error;

import java.io.IOException;

import com.goodforgoodbusiness.webapp.ContentType;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class IOExceptionHandler implements ExceptionHandler<IOException> {
	@Override
	public void handle(IOException e, Request req, Response res) {
		res.status(500);
		res.type(ContentType.text.getContentTypeString());
		res.body(e.getMessage());
	}
}
