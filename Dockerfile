
FROM openjdk:17-jdk-slim


WORKDIR /app

# Copy the built jar file into the container
COPY target/*.jar app.jar

# Expose port your app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
