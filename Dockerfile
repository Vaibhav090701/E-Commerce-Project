# Use Maven image to build the app
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy all files
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use a smaller base image for the final container
FROM eclipse-temurin:17
WORKDIR /app

# Copy the jar from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

