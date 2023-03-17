FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} eureka-service.jar
ENTRYPOINT ["java", "-jar", "eureka-service.jar"]