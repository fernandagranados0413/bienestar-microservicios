# Dockerfile para dashboard-prestamos-ms
FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/dashboard-prestamos-ms-*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","/app.jar"]
