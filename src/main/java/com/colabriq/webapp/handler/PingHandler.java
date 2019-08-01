package com.colabriq.webapp.handler;

import static com.colabriq.webapp.ContentType.TEXT;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

/**
 * Handles various types of failures and responses they generate
 */
public class PingHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		ctx.response().setStatusCode(400);
		ctx.response().putHeader(CONTENT_TYPE, TEXT.getContentTypeString());
		ctx.response().end("PONG\n");
	}
}
