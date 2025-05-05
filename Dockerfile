# Use a lightweight JDK base image
FROM openjdk:17-jdk-slim

# Set a working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose port (adjust if your app uses a different port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
	