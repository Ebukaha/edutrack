# ===== Build Stage =====
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the TiDB CA certificate (same folder as Dockerfile)
COPY isrgrootx1.pem /etc/ssl/certs/tidb-ca.pem

# Create a Java truststore and import the CA certificate
RUN keytool -importcert \
    -noprompt \
    -alias tidb-ca \
    -file /etc/ssl/certs/tidb-ca.pem \
    -keystore /etc/ssl/certs/tidb-truststore.jks \
    -storepass changeit \
    -storetype JKS

# Copy the built JAR
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Use the custom truststore
ENTRYPOINT ["java", \
    "-Djavax.net.ssl.trustStore=/etc/ssl/certs/tidb-truststore.jks", \
    "-Djavax.net.ssl.trustStorePassword=changeit", \
    "-jar", "app.jar"]