# Use OpenJDK 17 as base image
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/tax-api-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
