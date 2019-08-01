package com.goodforgoodbusiness.webapp;

import java.nio.file.Files;

import com.goodforgoodbusiness.webapp.cors.CORSHeaderHandler;
import com.goodforgoodbusiness.webapp.cors.CORSOptionsHandler;
import com.goodforgoodbusiness.webapp.handler.FailureHandler;
import com.goodforgoodbusiness.webapp.handler.PingHandler;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Defines some standard base functionality.
 */
@Singleton
public class BaseVerticle extends AbstractVerticle {
	/**
	 * Provides handlers for {@link BaseServer}.
	 */
	public interface HandlerProvider {
		public static BodyHandler createBodyHandler() {
			String path;
			
			try {
				path = Files.createTempDirectory(null).toAbsolutePath().toString();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			return BodyHandler.create()
				.setUploadsDirectory(path)	
				.setMergeFormAttributes(true)
			;
		}
		
		public void provide(Router router);
	}
	
	private final HandlerProvider provider;
	private Router router;

	@Inject
	public BaseVerticle(HandlerProvider provider) {
		this.provider = provider;
	}
	
	protected Router getRouter() {
		return router;
	}
	
	@Override
	public void start() {
		this.router = Router.router(vertx);
		
		// set up CORS
		this.router.options().handler(new CORSOptionsHandler());
		this.router.get().handler(new CORSHeaderHandler());
		this.router.post().handler(new CORSHeaderHandler());
		
		// std ping reply handler
		this.router.get("/ping").handler(new PingHandler());
		
		provider.provide(this.router);
		
		this.router.route().failureHandler(new FailureHandler());
	}
	
	@Override
	public void stop() {
	}
}
