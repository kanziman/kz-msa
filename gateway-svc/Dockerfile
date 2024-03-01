FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/gateway-svc-1.0.jar gateway-svc.jar
ENTRYPOINT ["java", "-jar", "gateway-svc.jar"]

