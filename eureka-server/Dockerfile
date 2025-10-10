FROM openjdk:17-jdk-buster
LABEL authors="Алексей"
WORKDIR /app
COPY /target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]