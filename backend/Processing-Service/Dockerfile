FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} processing-service.jar
ENTRYPOINT ["java", "-jar", "processing-service.jar"]