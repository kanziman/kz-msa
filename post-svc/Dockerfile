# Use the specified base image
FROM openjdk:17-ea-11-jdk-slim

# Set a volume pointing to /tmp
VOLUME /tmp

# Copy the built jar file into the image
COPY build/libs/post-svc-1.0.jar post-svc.jar

# Download wait-for-it script
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh

# Make wait-for-it.sh executable
RUN chmod +x /wait-for-it.sh

# Use wait-for-it.sh to wait for config-svc to be ready before starting the application
ENTRYPOINT ["/wait-for-it.sh", "config-svc:8888", "--", "java", "-jar", "/post-svc.jar"]
