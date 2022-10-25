package com.vertexprojecttraining;

import java.util.concurrent.TimeUnit;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.Json;

public class WebSocketClientVerticle extends AbstractVerticle {

	@Override
	public void start() {
		startClient(vertx);
	}

	@SuppressWarnings("deprecation")
	private void startClient(Vertx vertx) {
		HttpClient client = vertx.createHttpClient();

		client.websocket(8080, "localhost", "/", (ctx) -> {
			ctx.textMessageHandler((msg) -> {
				System.out.println("Client " + msg);
				ctx.writeTextMessage(Json.encodePrettily("Pong"));
			}).exceptionHandler((e) -> {
				System.out.println("Closed, restarting in 10 seconds");
				restart(client, 5);
			}).closeHandler((__) -> {
				System.out.println("Closed, restarting in 10 seconds");
				restart(client, 10);
			});
		});
	}

	private void restart(HttpClient client, int delay) {
		client.close();
		vertx.setTimer(TimeUnit.SECONDS.toMillis(delay), (__) -> {
			startClient(vertx);
		});
	}
}
