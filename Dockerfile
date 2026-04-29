# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY . .
# Run maven from inside the /app folder
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/loms-backend-0.0.1-SNAPSHOT.jar loms-backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "loms-backend.jar"]