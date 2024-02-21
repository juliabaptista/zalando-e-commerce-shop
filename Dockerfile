# Use the official Maven image as a base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml and src directories to the container
COPY pom.xml .
COPY src/ ./src/

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use the official OpenJDK image as a base image
FROM openjdk:17-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build image to the current directory
COPY --from=build /app/target/e-commerce-shop-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
#CMD ["java", "-jar", "app.jar"]
CMD ["tail", "-f", "/dev/null"]
