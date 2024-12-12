FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/ifmis_api-0.0.1.jar
COPY ${JAR_FILE} ifmis_api.jar
EXPOSE 8081
RUN touch app.log
ENTRYPOINT ["sh", "-c", "java -jar /ifmis_api.jar >> ./app.log 2>&1"]