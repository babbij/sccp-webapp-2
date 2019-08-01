package com.goodforgoodbusiness.webapp.handler;

import org.apache.log4j.Logger;

import com.goodforgoodbusiness.webapp.ContentType;
import com.goodforgoodbusiness.webapp.error.BadRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

/**
 * Handles various types of failures and responses they generate
 */
public class FailureHandler implements Handler<RoutingContext> {
	private static final Logger log = Logger.getLogger(FailureHandler.class);
	
	private static final Gson GSON;

	static {
		GsonBuilder builder = new GsonBuilder();
		builder.excludeFieldsWithoutExposeAnnotation();
		GSON = builder.create();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		if (ctx.failed()) {
			var thrown = ctx.failure();
			
			if (thrown != null) {
				log.error("Caught exception", thrown);
				
				if (thrown instanceof BadRequestException) {
					ctx.response().setStatusCode(400);
					ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getContentTypeString());
					ctx.response().end(GSON.toJson(thrown));
				}
				else {
					ctx.response().setStatusCode(500);
					ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getContentTypeString());
					ctx.response().end(GSON.toJson(thrown)); // XXX hide internal exception details
				}
			}
			else {
				ctx.response().setStatusCode(500);
				ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, ContentType.JSON.getContentTypeString());
				ctx.response().end("{\"message\": \"Server error\"}");
			}
		}
	}
}
