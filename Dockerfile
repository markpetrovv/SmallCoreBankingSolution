# Build stage
FROM openjdk:17-slim AS build

# Set the working directory
WORKDIR /app

# Copy the build.gradle file and other required files
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

# Grant execution permissions to the 'gradlew' script
RUN chmod +x ./gradlew

# Copy the rest of your application's source code
COPY src /app/src

# Run the Gradle build
RUN ./gradlew build -x test

# Run stage
FROM openjdk:17-slim

# Set the working directory
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
