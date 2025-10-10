# Build stage: compile the Java project with Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Runtime stage: use multi-arch compatible JRE
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/sciCalculator-1.0-SNAPSHOT.jar app.jar

# Run the Java application
ENTRYPOINT ["java", "-cp", "app.jar", "com.scicalculator.Main"]
