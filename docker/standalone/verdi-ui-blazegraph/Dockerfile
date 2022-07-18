FROM openjdk:11

# docker build . -t verdi-ui-blazegraph
# docker run -d -p 9999:9999 verdi-ui-blazegraph

RUN mkdir -p /opt/blazegraph

WORKDIR /opt/blazegraph

#RUN sysctl -w vm.swappiness=0
RUN wget -O blazegraph.jar https://github.com/blazegraph/database/releases/download/BLAZEGRAPH_2_1_6_RC/blazegraph.jar -P /opt/blazegraph
COPY blazegraph.jnl /opt/blazegraph/

EXPOSE 9999

CMD java -server -Xmx4g -jar /opt/blazegraph/blazegraph.jar
