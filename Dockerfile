FROM openjdk:11-jdk-slim as builder
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
ARG JAR_FILE=target/CarRentProject-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
RUN jar -xf ./application.jar

FROM openjdk:11-jdk-slim
ENV APP_HOME=/usr/app
EXPOSE 8080
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/BOOT-INF/lib lib
COPY --from=builder $APP_HOME/META-INF app/META-INF
COPY --from=builder $APP_HOME/BOOT-INF/classes app
ENTRYPOINT ["java", "-cp", "app:lib/*", "com.rent.carrent.CarRentProjectApplication"]