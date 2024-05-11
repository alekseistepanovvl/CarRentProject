FROM openjdk:11-jdk-slim
ENV APP_HOME=/usr/app
EXPOSE 8080
WORKDIR $APP_HOME
ARG JAR_FILE=target/CarRentProject-0.0.1-SNAPSHOT.jar
COPY target/CarRentProject-0.0.1-SNAPSHOT.jar $APP_HOME/application.jar
COPY target/modules $APP_HOME
ENTRYPOINT ["java", "-p", "./application.jar:./3rdparty", "-m", "com.rent.carrent"]