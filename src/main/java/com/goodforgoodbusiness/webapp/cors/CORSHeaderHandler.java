package com.goodforgoodbusiness.webapp.cors;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class CORSHeaderHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		ctx.response().putHeader("Access-Control-Allow-Origin", "*");
		ctx.next();
	}
}
