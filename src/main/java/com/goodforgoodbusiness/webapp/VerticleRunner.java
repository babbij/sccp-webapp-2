package com.goodforgoodbusiness.webapp;

import static io.vertx.core.Vertx.vertx;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Starts the Vert.x subsystem
 * @author ijmad
 */
@Singleton
public class VerticleRunner {
	private final VertxOptions vertxOptions;
	private final DeploymentOptions deployOptions;
	
	private final Vertx vertx;
	private final VerticleServer verticle;
	
	@Inject
	public VerticleRunner(VerticleServer verticle) {
		// XXX inject these?
		this.vertxOptions = new VertxOptions();
		this.vertxOptions.getEventBusOptions().setClustered(false);
		this.deployOptions = new DeploymentOptions();
		
		this.vertx = vertx(vertxOptions);
		
		this.verticle = verticle;
	}
	
	public void start() {
		this.vertx.deployVerticle(verticle, deployOptions);
	}
	
	public void stop() {
//		this.vertx.undeploy(deploymentID);
	}
}

