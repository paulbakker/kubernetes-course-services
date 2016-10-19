FROM java:8-jre-alpine

WORKDIR /app
ADD build/libs/server-fat.jar /app/server.jar

CMD java -Xmx128m -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -jar server.jar