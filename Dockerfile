# Use a Maven base image with OpenJDK 17
FROM maven:3.8-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy application code and pom.xml
COPY . .

# Install RabbitMQ server
RUN apt-get update && apt-get install -y rabbitmq-server

# Expose RabbitMQ port (default: 5672)
EXPOSE 5672

# Build the application with Maven Shade Plugin
RUN mvn -DskipTests=true package shade:shade

# Start a new stage for the final image
FROM openjdk:17-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the fat JAR with dependencies
COPY --from=build /app/target/The_Ride_BackEnd-0.0.1-SNAPSHOT-shaded.jar .

# Set the environment to production
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the port
EXPOSE 8080

# Command to start your Spring application
CMD ["java", "-jar", "The_Ride_BackEnd-0.0.1-SNAPSHOT-shaded.jar"]
