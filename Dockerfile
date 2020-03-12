FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bitcoin-tracker.jar
ENTRYPOINT ["java","-jar","/bitcoin-tracker.jar"]