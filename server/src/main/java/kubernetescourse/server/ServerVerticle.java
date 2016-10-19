package kubernetescourse.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ServerVerticle extends AbstractVerticle {
    private final static Logger LOG = LoggerFactory.getLogger(ServerVerticle.class);

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(this::handleRequest).listen(8080);

    }

    private void handleRequest(HttpServerRequest httpServerRequest) {
        httpServerRequest.bodyHandler(buff -> {
            JsonObject json = buff.toJsonObject();
            LOG.info("Received from client: {}", json.encodePrettily());

            httpServerRequest.response().end("OK");
        });

        httpServerRequest.exceptionHandler(ex -> {
            LOG.error("Error handling client request", ex);
        });
    }
}
