FROM openjdk:17-jdk-alpine
ENV LINUX_USERNAME doat
ENV LINUX_PASSWORD Doat@123#
RUN adduser -u 1001 -g 1001 -D -h /home/$LINUX_USERNAME $LINUX_USERNAME \
    && echo "$LINUX_USERNAME:$LINUX_PASSWORD" | chpasswd
RUN apk update && apk add --no-cache maven
WORKDIR /home/$LINUX_USERNAME
USER $LINUX_USERNAME
COPY src src
COPY pom.xml pom.xml
RUN mvn clean install -DskipTests
EXPOSE 8081
RUN touch app.log
ENTRYPOINT ["sh", "-c", "java -jar target/ifmis_api.jar >> ./app.log 2>&1"]