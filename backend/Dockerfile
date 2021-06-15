FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vrp.jar
ENTRYPOINT ["java","-jar","/vrp.jar"]