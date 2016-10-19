package kubernetescourse.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ClientVerticle extends AbstractVerticle {
    private final static Logger LOG = LoggerFactory.getLogger(ClientVerticle.class);
    private final static String SERVER = "kubernetescourse-server.default.svc.cluster.local";

    @Override
    public void start() throws Exception {
        LOG.info("Starting client...");

        HttpClient httpClient = vertx.createHttpClient();

        vertx.setPeriodic(1000, i -> {

            httpClient.post(8080, SERVER, "/", resp -> {
                resp.bodyHandler(LOG::info);

                resp.exceptionHandler(ex -> LOG.error("Error posting to server", ex));
            }).end(new JsonObject().put("action", "test").encode());

        });
    }
}

