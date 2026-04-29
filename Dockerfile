# Step 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy the contents of the loms-backend folder into /app
COPY loms-backend/ .

# This will now find the pom.xml correctly
RUN mvn clean package -DskipTests

# Step 2: Run the application
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Match the jar name produced by your build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]