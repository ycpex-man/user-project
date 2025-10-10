FROM openjdk:17-jdk-buster
LABEL authors="Алексей"
WORKDIR /app
COPY /target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]