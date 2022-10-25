package com.vertexprojecttraining;

import io.vertx.core.Vertx;

public class WebScocketMain {
	
	public static void main(String args[]) {
		
		Vertx vertx = Vertx.vertx();
		
		 vertx.deployVerticle(new WebSocketServerVerticle(), (__) -> {
	            vertx.deployVerticle(new WebSocketClientVerticle());
	        });
		

	}

}
