# Build stage
FROM maven:3.8.4-openjdk-17 AS build
COPY loms-backend/pom.xml /app/
COPY loms-backend/src /app/src/
WORKDIR /app
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]