FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/url-shortener-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} employee-demo.jar
ENTRYPOINT ["java","-jar","employee-demo.jar"]