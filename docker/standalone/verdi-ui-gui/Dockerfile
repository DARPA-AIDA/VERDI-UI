#local testing
#docker build . -t verdi-ui-gui
#docker run --rm -it -p 8081:8081 verdi-ui-gui

FROM node:18

#Set the working directory
WORKDIR /usr/src/verdi-gui

#copy all the files
COPY . .

#Install the dependencies
RUN npm install

#Expose the required port, should coincide with python settings API_HOST_PORT
EXPOSE $VERDI_GUI_PORT

#Run the command
ENTRYPOINT npm run serve -- --port $VERDI_GUI_PORT --disable-host-check
