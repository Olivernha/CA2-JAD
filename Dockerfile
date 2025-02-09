FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml .
COPY .mvn .mvn

# Copy source code and build the application
COPY src src
RUN ./mvnw package -DskipTests

# Use a minimal runtime image
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR file
COPY --from=build /app/target/jadca2-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]