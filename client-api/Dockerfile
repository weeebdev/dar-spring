FROM openjdk:8-jdk-alpine

WORKDIR /opt/app

ARG JAR_FILE=target/client-api.jar

COPY ${JAR_FILE} app.jar

EXPOSE 5555

ENTRYPOINT ["java","-jar","app.jar"]