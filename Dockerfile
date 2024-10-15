FROM amazoncorretto:21
WORKDIR /app
COPY build/libs/authService-0.0.1-SNAPSHOT-plain.jar /app/authService.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/authService.jar"]
