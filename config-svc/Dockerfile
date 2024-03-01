FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY build/libs/config-svc-1.0.jar config-svc.jar
ENTRYPOINT ["java", "-jar", "config-svc.jar"]

