# Use Eclipse Temurin Java 17 JRE Alpine as base image
FROM public.ecr.aws/docker/library/eclipse-temurin:17-jre-alpine
# Install curl for container health checks
RUN apk add --no-cache curl

# Set working directory
WORKDIR /app

# Copy the JAR file built by Maven
COPY target/tax-api-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]