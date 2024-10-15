FROM amazoncorretto:21-alpine
WORKDIR /app
COPY build/libs/authService-0.0.1-SNAPSHOT.jar /app/authService.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/authService.jar"]
