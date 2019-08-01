package com.colabriq.webapp;

import static io.vertx.core.Vertx.vertx;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

@Singleton
public class VertxProvider implements Provider<Vertx> {
	private final VertxOptions vertxOptions;
	private final Vertx vertx;
	
	public VertxProvider() {
		// XXX inject these ?
		this.vertxOptions = new VertxOptions();
		this.vertxOptions.getEventBusOptions().setClustered(false);
		this.vertx = vertx(vertxOptions);
	}
	
	@Override @Singleton
	public Vertx get() {
		return vertx;
	}
}
