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

# Expose the port that the application listens on
EXPOSE 8080

## 5. Define the command to run when the container starts.
RUN mvn install
CMD [ "mvn", "clean", "spring-boot:run" ]