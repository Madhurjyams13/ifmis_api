# Build stage
FROM maven:3.8-openjdk-17-slim AS builder

# Set working directory for the build
WORKDIR /build

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Remove unused dependencies and pre-fetch necessary ones
#RUN mvn dependency:purge-local-repository dependency:go-offline

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage (Final Image)
FROM openjdk:17-jdk-alpine

# Create a non-root user
RUN adduser -u 1001 -g 1001 -D -h /home/doat doat && \
    mkdir -p /home/doat/.secureapi && \
    mkdir -p /app/logs && \
    chown -R doat:doat /home/doat/.secureapi && \
    chown -R doat:doat /app

# Set working directory
WORKDIR /app

# Copy only the built JAR and start up sh files from the builder stage
COPY --from=builder /build/target/ifmis_api.jar ./app.jar
COPY --chown=doat:doat entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Switch to non-root user
USER doat

# Set environment variable for user home
ENV USER_HOME=/home/doat

# Expose application port and use production application properties
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=prod

# Start entrypoint script to start the application
ENTRYPOINT ["/app/entrypoint.sh"]
