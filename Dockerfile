# Step 1: Use OpenJDK as base image
FROM openjdk:17-jdk-slim

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy the built JAR into the container
COPY target/restapi-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose port (make sure it matches your Spring Boot app port)
EXPOSE 8080

# Step 5: Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
