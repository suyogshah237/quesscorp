# Use a base image with JDK
FROM eclipse-temurin:17-jdk

# Set app directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
