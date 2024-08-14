FROM amazoncorretto:17.0.6
COPY target/travel-project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]