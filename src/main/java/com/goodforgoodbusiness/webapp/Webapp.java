package com.goodforgoodbusiness.webapp;

import java.io.IOException;
import java.util.Map;

import com.goodforgoodbusiness.webapp.cors.CorsFilter;
import com.goodforgoodbusiness.webapp.cors.CorsRoute;
import com.goodforgoodbusiness.webapp.error.BadRequestException;
import com.goodforgoodbusiness.webapp.error.BadRequestExceptionHandler;
import com.goodforgoodbusiness.webapp.error.IOExceptionHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import spark.Route;
import spark.RouteImpl;
import spark.Service;

@Singleton
public class Webapp {
	protected final int port;
	protected final Map<Resource, Route> routes;
	
	protected Service service = null;
	
	@Inject
	public Webapp(@Named("port") int port, Map<Resource, Route> routes) {
		this.port = port;
		this.routes = routes;
	}
	
	protected void configure() {
		service.options("/*", new CorsRoute());
		service.before(new CorsFilter());
		
		routes.forEach((resource, route) -> 
			service.addRoute(resource.method, RouteImpl.create(resource.path, route)));
		
		service.exception(BadRequestException.class, new BadRequestExceptionHandler());
		service.exception(IOException.class, new IOExceptionHandler());
	}
	
	public final void start() {
		service = Service.ignite();
		service.port(port);
		
		configure();
		
		service.awaitInitialization();
	}
}

