package com.goodforgoodbusiness.webapp.cors;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class CORSOptionsHandler implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
        var acrhHeader = ctx.request().getHeader("Access-Control-Request-Headers");
        if (acrhHeader != null) {
            ctx.response().putHeader("Access-Control-Allow-Headers", acrhHeader);
        }

        var acrmHeader = ctx.request().getHeader("Access-Control-Request-Method");
        if (acrmHeader != null) {
        	ctx.response().putHeader("Access-Control-Allow-Methods", acrmHeader);
        }
        
        ctx.response().end();
        ctx.next();
	}
}
