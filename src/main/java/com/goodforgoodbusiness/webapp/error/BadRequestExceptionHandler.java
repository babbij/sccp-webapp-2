package com.goodforgoodbusiness.webapp.error;

import com.goodforgoodbusiness.webapp.ContentType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class BadRequestExceptionHandler implements ExceptionHandler<BadRequestException> {
	private static final Gson GSON;
	
	static {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		GSON = builder.create();
	}
	
	@Override
	public void handle(BadRequestException e, Request req, Response res) {
		res.status(400);
		res.type(ContentType.json.getContentTypeString());
		res.body(GSON.toJson(e));
	}
}
