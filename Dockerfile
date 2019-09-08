#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} xmed.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/xmed.jar"]


FROM openjdk:latest
#VOLUME /tmp
ADD target/xmed-1.1-SNAPSHOT.jar app.jar
#ARG JAR_FILE
#COPY ${JAR_FILE} target/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080

