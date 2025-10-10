FROM openjdk:17-jdk-buster
LABEL authors="Алексей"
WORKDIR /app
COPY /target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service.jar
ENTRYPOINT ["java", "-jar", "notification-service.jar"]