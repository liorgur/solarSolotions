FROM maven:3.5-jdk-8 AS build  
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
COPY Xmed.iml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml package

FROM openjdk:latest
ENV VERSION=1.1-SNAPSHOT
COPY --from=build /usr/src/app/target/xmed-${VERSION}.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
