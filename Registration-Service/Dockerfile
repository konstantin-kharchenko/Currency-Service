FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} registration-service.jar
ENTRYPOINT ["java", "-jar", "registration-service.jar"]