# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Skip tests during build
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-slim
WORKDIR /app
# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar
# Copy Firebase credentials
COPY src/main/resources/firebaseKeys.json /app/firebaseKeys.json
# Create uploads directory
RUN mkdir -p /app/uploads

# Environment variables with default values
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-service:3306/SpringPI
ENV SPRING_DATASOURCE_USERNAME=root
#ENV SPRING_DATASOURCE_PASSWORD=
ENV SERVER_PORT=8081

# Expose the application port
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]
