# Build stage (Keep this or use eclipse-temurin)
FROM maven:3.8.4-openjdk-17 AS build
COPY loms-backend/pom.xml /app/
COPY loms-backend/src /app/src/
WORKDIR /app
RUN mvn clean package -DskipTests

# Run stage (CHANGE THIS LINE)
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app.jar"]