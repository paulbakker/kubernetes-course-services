package kubernetescourse.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ServerVerticle extends AbstractVerticle {
    private final static Logger LOG = LoggerFactory.getLogger(ServerVerticle.class);

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(this::handleRequest).listen(8080, result -> {
            LOG.info("Server started: {}", result.succeeded());
        });

    }

    private void handleRequest(HttpServerRequest httpServerRequest) {
        if(httpServerRequest.method() == HttpMethod.POST) {
            httpServerRequest.bodyHandler(buff -> {

                try {
                    JsonObject json = buff.toJsonObject();
                    LOG.info("Received from client: {}", json.encodePrettily());
                    httpServerRequest.response().end("OK");
                } catch(Exception ex) {
                    LOG.error("Received invalid input", ex);
                    httpServerRequest.response().end("NOT OK");
                }
            });
        } else {
            LOG.info("Received empty request from client");
            httpServerRequest.response().end("OK");
        }

        httpServerRequest.exceptionHandler(ex -> {
            LOG.error("Error handling client request", ex);
        });

    }
}
