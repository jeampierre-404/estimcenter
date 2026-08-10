# 1. Etapa de construcción (Usamos Maven y Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Etapa de ejecución (Un entorno Java 21 súper ligero)
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# 3. Arrancamos el servidor
ENTRYPOINT ["java", "-jar", "app.jar"]