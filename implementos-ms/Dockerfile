# Dockerfile para implementos-ms
# Parte 1: imagen base ligera con Java Runtime Environment (JRE) 17
FROM eclipse-temurin:17-jre-alpine

# Parte 2: argumento que busca el JAR generado por Maven
ARG JAR_FILE=target/implementos-ms-*.jar

# Parte 3: copia el JAR al contenedor como app.jar
COPY ${JAR_FILE} app.jar

# Parte 4: expone el puerto interno de Spring Boot (8088)
EXPOSE 8088

# Parte 5: comando que se ejecuta siempre al iniciar el contenedor
ENTRYPOINT ["java","-jar","/app.jar"]
