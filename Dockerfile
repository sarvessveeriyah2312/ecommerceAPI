# ========================
# Stage 1: Build with Maven
# ========================
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copy pom.xml and Maven wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Download dependencies first (cache optimization)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests

# ========================
# Stage 2: Run Spring Boot JAR
# ========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the app port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
