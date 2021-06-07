FROM openjdk:11

ARG JAR_FILE=target/*.jar

VOLUME /tmp

EXPOSE 8080

COPY ${JAR_FILE} rest-api-social-meli.jar

ENTRYPOINT ["java", "-jar", "/rest-api-social-meli.jar"]