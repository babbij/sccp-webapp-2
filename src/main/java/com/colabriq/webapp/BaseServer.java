package com.goodforgoodbusiness.webapp;

import java.nio.file.Paths;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * Starts the Vert.x subsystem
 */
@Singleton
public class BaseServer {
	private static final Logger log = Logger.getLogger(BaseServer.class);
	
	static {
		var cwd = Paths.get("").toAbsolutePath().toString();
		System.setProperty("vertx.cwd", cwd);
	}
	
	private final Vertx vertx;
	private final int port;
	private final DeploymentOptions deployOptions;
	private final BaseVerticle verticle;
	
	private HttpServer httpServer = null;
	
	@Inject
	public BaseServer(Vertx vertx, @Named("port") int port, BaseVerticle verticle) {
		this.vertx = vertx;
		this.port = port;
		this.deployOptions = new DeploymentOptions();
		this.verticle = verticle;
	}
	
	public void start() {
		this.vertx.deployVerticle(verticle, deployOptions);
		
		log.info("Starting webapp on " + port);
		
		this.httpServer = vertx
			.createHttpServer()
			.requestHandler(verticle.getRouter())
			.listen(port)
		;
	}
	
	public void stop() {
		if (this.httpServer != null) {
			this.httpServer.close();
		}
	}
}

