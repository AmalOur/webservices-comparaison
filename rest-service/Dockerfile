# Utilise une image de base OpenJDK 17
FROM openjdk:17-jdk

# Définit le répertoire de travail
WORKDIR /app

# Copie le fichier JAR généré dans le conteneur
COPY target/GestionReservation-0.0.1-SNAPSHOT.jar app.jar

# Expose le port 8082 pour REST
EXPOSE 8082

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
