FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/ok-cryptomarket-app-spring-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]
