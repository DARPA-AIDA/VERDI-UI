FROM httpd:2.4

# docker build . -t verdi-ui-blazegraph
# docker run -d -p 9999:9999 verdi-ui-blazegraph


WORKDIR /usr/local/apache2/htdocs/

#copy all the files
COPY . .

COPY httpd.conf  /usr/local/apache2/conf/