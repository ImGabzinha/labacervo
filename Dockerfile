FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /projeto
COPY pom.xml .
COPY src src
RUN mvn -B package
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /projeto/target/labacervo-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
