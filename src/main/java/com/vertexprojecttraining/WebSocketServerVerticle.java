package com.vertexprojecttraining;

import java.util.Random;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;


public class WebSocketServerVerticle extends AbstractVerticle  {

	@Override
	    public void start() {
	        startServer(vertx);
	    }

	 @SuppressWarnings("deprecation")
	private void startServer(Vertx vertx) {
		    HttpServer server = vertx.createHttpServer();

		    server.websocketHandler((ctx) -> {
		        ctx.writeTextMessage(Json.encodePrettily("Ping"));

		        ctx.textMessageHandler((msg) -> {
		            System.out.println("Server " + msg);

		            if ((new Random()).nextInt(100) == 0) {
		                ctx.close();
		            }
		            else {
		                ctx.writeTextMessage(Json.encodePrettily("Ping"));
		            }
		        });
		    }).listen(8080);
		}
}
