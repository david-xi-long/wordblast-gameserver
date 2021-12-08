FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENV SPRING_ENV production

ENTRYPOINT ["java", "-jar", "/app.jar"]
