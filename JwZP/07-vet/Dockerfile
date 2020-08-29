FROM openjdk:11-jre-slim AS app
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
CMD java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$PORT -jar /app.jar
