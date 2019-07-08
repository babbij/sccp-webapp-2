package com.goodforgoodbusiness.webapp;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.goodforgoodbusiness.webapp.cors.CORSFilter;
import com.goodforgoodbusiness.webapp.cors.CORSRoute;
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
	private static final Logger log = Logger.getLogger(Webapp.class);
	
	protected final int port;
	protected final Map<Resource, Route> routes;
	
	protected Service service = null;
	
	@Inject
	public Webapp(@Named("port") int port, Map<Resource, Route> routes) {
		this.port = port;
		this.routes = routes;
	}
	
	protected void configure() {
//		service.before(new JWTFilter());
		
		service.options("/*", new CORSRoute());
		service.before(new CORSFilter());
		
		routes.forEach((resource, route) -> 
			service.addRoute(resource.method, RouteImpl.create(resource.path, route)));
		
		service.exception(BadRequestException.class, new BadRequestExceptionHandler());
		service.exception(IOException.class, new IOExceptionHandler());
	}
	
	public final void start() {
		log.info("Starting webapp on " + port);
		
		service = Service.ignite();
		service.port(port);
		
		configure();
		
		service.awaitInitialization();
	}
	
	public final void stop() {
		if (service != null) {
			service.stop();
		}
	}
}

