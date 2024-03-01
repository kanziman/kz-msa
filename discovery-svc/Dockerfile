FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/discovery-svc-1.0.jar discovery-svc.jar
ENTRYPOINT ["java", "-jar", "discovery-svc.jar"]

