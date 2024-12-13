# Utilise une image de base OpenJDK 17
FROM openjdk:17-jdk

# Définit le répertoire de travail
WORKDIR /app

# Copie le fichier JAR généré dans le conteneur
COPY target/GestionReservation-0.0.1-SNAPSHOT.jar app.jar

# Expose le port 4000 pour GraphQL
EXPOSE 4000

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
