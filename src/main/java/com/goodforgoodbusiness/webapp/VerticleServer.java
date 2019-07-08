package com.goodforgoodbusiness.webapp;

import static io.vertx.core.Vertx.vertx;

import org.apache.log4j.Logger;

import com.goodforgoodbusiness.webapp.cors.CORSHeaderHandler;
import com.goodforgoodbusiness.webapp.cors.CORSOptionsHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * Defines some standard base functionality.
 */
@Singleton
public class VerticleServer extends AbstractVerticle {
	private static final Logger log = Logger.getLogger(VerticleServer.class);
	
	/**
	 * Provides handlers for VerticleServer
	 * @author ijmad
	 *
	 */
	public interface HandlerProvider {
		public void provide(Router router);
	}
	
	private final int port;
	private final HandlerProvider provider;
	private final Router router;

	private HttpServer httpServer = null;

	@Inject
	protected VerticleServer(@Named("port") int port, HandlerProvider provider) {
		this.port = port;
		this.provider = provider;
		this.router = Router.router(vertx);
	}
	
	@Override
	public void start() {
		// set up CORS
		this.router.options().handler(new CORSOptionsHandler());
		this.router.get().handler(new CORSHeaderHandler());
		this.router.post().handler(new CORSHeaderHandler());
		
		provider.provide(this.router);
		
		this.router.route().failureHandler(new FailureHandler());
		
		log.info("Starting webapp on " + port);
		
		this.httpServer = vertx()
			.createHttpServer()
			.requestHandler(router)
			.listen(port)
		;
	}
	
	@Override
	public void stop() {
		if (this.httpServer != null) {
			this.httpServer.close();
		}
	}
}
