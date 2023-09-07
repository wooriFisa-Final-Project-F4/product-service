FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/product-service-0.0.1-SNAPSHOT.jar /app/product-service.jar
CMD ["java", "-jar", "/app/auction-status-updater.jar"]