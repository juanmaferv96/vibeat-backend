# 1. Etapa de compilación (Usamos Maven con Eclipse Temurin 17)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Usamos una imagen ligera de Eclipse Temurin 17)
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# Copiamos el jar generado en la etapa anterior
COPY --from=build /app/target/vibeat-backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

# Añadimos -Xmx350m para que Java nunca use más de 350MB de RAM (dejando espacio al SO)
ENTRYPOINT ["java", "-Xmx350m", "-Xms350m", "-jar", "/app.jar"]