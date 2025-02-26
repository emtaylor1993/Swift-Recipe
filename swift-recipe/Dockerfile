###############################################################
# Dockerfile
#
# @author Emmanuel Taylor
#
# @description
#   Provides a set of instructions to build a SpringBoot Docker
#   container.
###############################################################

# Use the official Maven runtime with JDK 17 as a base image
FROM maven:3.6.3-openjdk-17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Package the application into a JAR file.
RUN mvn clean package -DskipTests

# Expose the port that the application listens on
EXPOSE 8080

# Change working directory to the JAR location.
WORKDIR /app/target

## 5. Define the command to run when the container starts.
CMD [ "java", "-jar", "swift-recipe-0.0.1-SNAPSHOT.jar" ]