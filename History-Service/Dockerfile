FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} history-service.jar
ENTRYPOINT ["java", "-jar", "history-service.jar"]