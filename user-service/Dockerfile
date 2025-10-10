FROM openjdk:17-jdk-buster
LABEL authors="Алексей"
WORKDIR /app
COPY /target/user-service-0.0.1-SNAPSHOT.jar /app/user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]