FROM openjdk:21
WORKDIR /app
EXPOSE 8080
COPY target/TaskManagement-0.0.1-SNAPSHOT.jar /app/TaskManagement-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/TaskManagement-0.0.1-SNAPSHOT.jar"]
