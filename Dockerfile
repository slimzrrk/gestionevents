# Étape 1 : Construire l'application avec Maven
FROM maven:3.6.3-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn package -DskipTests

# Étape 2 : Créer l'image finale
FROM openjdk:17-jdk-slim AS prod
WORKDIR /app

# Installer curl et nettoyer les fichiers temporaires
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copier l'application jar depuis l'étape de construction
COPY --from=build /app/target/*.jar /app/app.jar

# Exposer le port
EXPOSE 8080

# Lancer l'application
CMD ["java", "-jar", "app.jar"]
