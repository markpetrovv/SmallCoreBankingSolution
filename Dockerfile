# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the build.gradle file and other required files
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Grant execution permissions to the 'gradlew' script
RUN chmod +x ./gradlew

# Run the Gradle build
RUN ./gradlew build

# Copy the built application
COPY build/libs/test-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]