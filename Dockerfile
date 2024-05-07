FROM openjdk:17-slim

WORKDIR /app

# Copy application code and resources
COPY . .

# Install dependencies (if using Maven)
RUN mvn clean install

# Install RabbitMQ server
RUN apt-get update && apt-get install -y rabbitmq-server

# Expose RabbitMQ port (default: 5672)
EXPOSE 5672

# Expose the port where your Spring application runs (typically 8080)
EXPOSE 8080

# Command to start your Spring application (adjust based on your setup)
CMD ["java", "-jar", "TheRideBackEndApplication.jar"]
