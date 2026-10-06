# ===== Build Stage =====
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B || true

COPY src ./src
RUN mvn clean package -DskipTests -B

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create a non-root system user for security
RUN addgroup -S edutrack && adduser -S edutrack -G edutrack

# Copy the built JAR artifact
COPY --from=builder /app/target/*.jar app.jar

# Set ownership to non-root user
RUN chown -R edutrack:edutrack /app
USER edutrack:edutrack

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]