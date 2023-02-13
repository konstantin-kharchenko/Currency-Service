FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} currency-service.jar
ENTRYPOINT ["java", "-jar", "currency-service.jar"]