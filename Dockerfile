FROM maven:3.6.3-jdk-11 as builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean verify

FROM mcr.microsoft.com/openjdk/jdk:11-distroless
ENV APP_HOME=/usr/app
EXPOSE 8080
WORKDIR $APP_HOME
COPY --from=builder /usr/src/app/target/CarRentProject-0.0.1-SNAPSHOT.jar $APP_HOME/application.jar
COPY --from=builder /usr/src/app/target/modules $APP_HOME
ENTRYPOINT ["java", "-p", "./application.jar:./3rdparty", "-m", "com.rent.carrent"]
