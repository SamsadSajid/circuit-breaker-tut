FROM adoptopenjdk/openjdk14:alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-service-app.jar
ENTRYPOINT ["java", "-jar", "/payment-service-app.jar"]