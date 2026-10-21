# ---- Étape 1 : build ----
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# On copie d'abord les fichiers Maven seuls pour profiter du cache Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Puis le code source
COPY src ./src
RUN ./mvnw package -DskipTests -B

# ---- Étape 2 : exécution ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# On évite de faire tourner l'application en root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
