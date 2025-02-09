FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml .  
COPY .mvn .mvn  

# Grant execution permission to Maven wrapper  
RUN chmod +x mvnw  

# Copy source code and build the application
COPY src src  

# Use `sh mvnw` instead of `./mvnw` to avoid permission issues
RUN sh mvnw package -DskipTests  

# Use a minimal runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR file
COPY --from=build /app/target/jadca2-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
