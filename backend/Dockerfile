FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vrp.jar
ENTRYPOINT ["java","-jar","/vrp.jar"]