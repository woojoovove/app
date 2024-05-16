FROM openjdk:17-alpine
RUN mkdir -p /data/
RUN mkdir -p /logs/
ARG JAR_FILE=build/libs/app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
EXPOSE 8080