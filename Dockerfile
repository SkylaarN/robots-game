# Use an official OpenJDK 22 runtime as a parent image
FROM openjdk:22-oracle

# Create and set the working directory
WORKDIR /srv

# Copy the JAR file into the container
COPY target/toyrobot-1.0-SNAPSHOT-jar-with-dependencies.jar /srv/toyrobot-1.0.jar

# Expose port 5000
EXPOSE 5050

# Command to run the application
CMD ["java", "-jar", "toyrobot-1.0.jar"]