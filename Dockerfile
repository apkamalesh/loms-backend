# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM openjdk:17-jdk-slim
COPY --from=build /target/loms-backend-0.0.1-SNAPSHOT.jar loms-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "loms-backend.jar"]