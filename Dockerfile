FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY target/placementPortal-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 20553

CMD ["java",".jar","app.jar"]
