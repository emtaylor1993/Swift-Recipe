###############################################################
# Dockerfile
#
# @author Emmanuel Taylor
#
# @description
#   Provides a set of instructions to build a Cypress Docker
#   container.
###############################################################

# Use the official Maven runtime with JDK 17 as a base image
FROM cypress/base

# Set the working directory inside the container
WORKDIR /app

# Copy the project files
COPY . .

# Define the command to run when the container starts.
RUN npm install