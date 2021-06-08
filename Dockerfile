FROM maven:3.6.0-jdk-11-slim AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean install

FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /${JAR_FILE} rest-api-social-meli.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/rest-api-social-meli.jar"]