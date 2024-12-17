FROM openjdk:17-jdk-alpine
ENV LINUX_USERNAME doat
ENV LINUX_PASSWORD Doat@123#
#RUN adduser -u 1001 -g 1001 -D -h /home/$LINUX_USERNAME $LINUX_USERNAME
# Set the user's password
#RUN echo "$USERNAME:$USER_PASSWORD" | chpasswd

# Set the new user as the default user
#USER $USERNAME
#WORKDIR /home/$LINUX_USERNAME
ARG JAR_FILE=target/ifmis_api-0.0.1.jar
COPY ${JAR_FILE} ifmis_api.jar
EXPOSE 8081
#RUN mkdir -p /home/$LINUX_USERNAME/.secureapi
RUN touch app.log
ENTRYPOINT ["sh", "-c", "java -jar /ifmis_api.jar >> ./app.log 2>&1"]