# 1. Usamos una imagen de Maven con Java 17 para construir el proyecto
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Usamos una imagen ligera de Java 17 para ejecutarlo
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/vibeat-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]