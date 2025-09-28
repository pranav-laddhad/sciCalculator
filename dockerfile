# Step 1: Use Maven + JDK image to build the app
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and create JAR
RUN mvn clean package

# Step 2: Use lightweight JDK image for runtime
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/sciCalculator-1.0-SNAPSHOT.jar app.jar

# Set default command to run the CLI app interactively
ENTRYPOINT ["java", "-cp", "app.jar", "com.scicalculator.Main"]
