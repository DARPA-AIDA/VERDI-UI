#local testing
#docker build . -t verdi-ui-restapi
#docker run --rm -it -p 8008:8008 verdi-ui-restapi

FROM openjdk:11

RUN apt-get update -y; apt-get install curl openssh-client -y

RUN mkdir -p ~/.ssh

RUN echo "ServerAliveInterval 180" >> ~/.ssh/config

#Set the working direct
#NOTE: build.gradle references version.properties, outside project folder
WORKDIR /usr/src/verdi-api

#copy all the files
COPY . .

RUN ls -al

RUN chmod +x ./docker/dev/verdi-ui-restapi/entrypoint.sh

WORKDIR /usr/src/verdi-api/restAPI

#Build
RUN ./gradlew clean build

WORKDIR /usr/src/verdi-api

RUN ls -al

#ENV VERSION=0.1.0

#Expose the required port, should coincide with python settings API_HOST_PORT
EXPOSE 8008

ENTRYPOINT ["./docker/dev/verdi-ui-restapi/entrypoint.sh"]

