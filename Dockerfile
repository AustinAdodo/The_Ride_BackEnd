# Use a Maven base image with OpenJDK 17
FROM maven:3.8-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy application code and resources
COPY . .

# Build the application with Maven
RUN mvn clean install

# Start a new stage for the final image
FROM openjdk:17-slim

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/The_Ride_BackEnd-0.0.1-SNAPSHOT.jar ./

# Set the environment to production
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the port for Spring application
EXPOSE 8080

# Start your Spring application
CMD ["java", "-jar", "The_Ride_BackEnd-0.0.1-SNAPSHOT.jar"]
