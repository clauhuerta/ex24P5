# Usa una imagen base de Java 17 (Spring Boot recomienda Amazon Corretto o OpenJDK)
FROM openjdk:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR al contenedor
COPY CH-0.0.1-SNAPSHOT.jar app.jar

# Copia la carpeta data.json, naves, peticiones si las necesitas (opcional)
COPY data.json .
COPY naves ./naves
COPY peticiones ./peticiones

# Expone el puerto (ajusta si no usas 8083)
EXPOSE 8083

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
