FROM adoptopenjdk/openjdk14:alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} order-service-app.jar
ENTRYPOINT ["java", "-jar", "/order-service-app.jar"]