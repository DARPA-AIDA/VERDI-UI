# VERDI-UI
The NextCentury/CACI UI for the AIDA program

## Project Structure

### ` /GUI `  
The TA4 front-end providing visual representation of the Knowledge Base

Prerequisites:
1. Node.js ^16.13.2 and npm.js ^8.1.2 - [installation instructions](https://nodejs.org/en/download/package-manager/)

### ` /restAPI `  
The TA4 back-end consisting of a Rest API that uses SPARQL to query a RDF triplestore


### ` /docker `
Deployment scripts for various environments

Development:
Note Development Environment uses AWS Elastic Beanstalk
```
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 606941321404.dkr.ecr.us-east-1.amazonaws.com 
docker-compose -f ./docker-compose-dev.yaml build 
docker push 606941321404.dkr.ecr.us-east-1.amazonaws.com/verdi-ui-gui:latest 
docker push 606941321404.dkr.ecr.us-east-1.amazonaws.com/verdi-ui-restapi:latest 
cd ./docker/dev 
eb deploy 
cd ../.. 
```

Prerequisites:
1. Java 11 - [installation instructions](https://java.com/en/download/help/download_options.xml)
1. Access to a SPARQL endpoint; e.g. tunneling into Neptune triplestore:

   ``` 
   ssh -L 8182:<triplestore hostname>:8182 user@host 
   ``` 
    * If your triplestore requires SSL (like Neptune), update `hosts` file with the following line:

        ```
        127.0.0.1 <triplestore hostname>
        ```

        Location of `hosts` file:
        * Unix-Like: /etc/hosts
        * MacOS: /private/etc/hosts
        * Windows: C:\Windows\System32\drivers\etc\hosts

1. Docker - [installation instructions](https://docs.docker.com/get-docker/)
1. Access to an Elasticsearch 7.6.2 database endpoint preferably using Docker. For instructions on starting a single node cluster, go to the [Elasticsearch docker tutorial](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

   ```
   docker pull elasticsearch:7.6.2
   docker run -it -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2 
   ```
<br/>

## Triplestore API Setup

The restAPI needs access to the triplestore and Elasticsearch. It is assumed that Neptune is the triplestore available at localhost:8182. It is also assumed that Elasticsearch is available at localhost:9200.

If the triplestore or Elasticsearch instance is running on a different ip address and port, the DataUtils and RDFCacheCreator files will need to be modified.

### Updating triplestore endpoint (Modifying DataUtils.java)
[DataUtils](restAPI/src/main/java/com/ncc/verdi/DataUtils.java) controls the triplestore endpoint for both the restAPI and RDFCacheCreator

Modify the triplestore address and port by modifying the variable NEPTUNE_ADDRESS

    ```
    public static final String NEPTUNE_ADDRESS = "http://prototype-ui.cbbg7lviorzp.us-east-1.neptune.amazonaws.com:8182";
    ```

### Updating the Elasticsearch endpoint
[application.properties](src/main/resources/application.properties) controls the Elasticsearch address, port, and scheme (http/https)

    ```
    elastic.host=aida-ui-dev.verdi.nextcentury.com
    elastic.scheme=https
    elastic.port=9200
    ```

### Configurable Properties for GUI and restAPI
[interface.properties](interface.properties) Configurable Properties for GUI

```
URI_BASE: The base URI for all named graphs
```

[application.properties](src/main/resources/application.properties) Configurable Properties for restAPI

```
#Server/Spring settings
server.port: restAPI server port (8008)
spring.h2.console.enabled: Spring web console (true)
generateSupportingFiles: Spring property to generate supporting files (true)
spring.jpa.hibernate.ddl-auto:how the schema tool management will manipulate the database schema at startup (update)
spring.mvc.servlet.path: conext-path for API's (/api)
spring.servlet.multipart.max-file-size = Max file upload size (20GB)
spring.servlet.multipart.max-request-size = Max file request size (20GB)

#Graphs to cache on statup
ncc.ta2-graphs: comma separated list of named TA2 graphs to cache on start-up
ncc.ta3-runs:  comma separated list of named TA3 graphs to cache on start-up

#ElasticSearch URL
elastic.scheme=https
elastic.host=aida-ui-dev.verdi.nextcentury.com
elastic.port=9200

#Triplestore URL (Blazegraph)
triplestore.scheme=https
triplestore.host=aida-ui-dev.verdi.nextcentury.com
triplestore.port=443
triplestore.query.namespace=blazegraph/sparql

#Location to resolve QNodes
kgtk.scheme=https
kgtk.host=kgtk.isi.edu
kgtk.namespace=/api

URI_BASE: The base URI for all named graphs

```

### Modifying SPARQL Queries

The API SPARQL queries are based on NIST restricted AIF. In order for this rest API and UI to work with a different structure, 
the SPARQL queries will need to be modified to include the appropriate URIs.
The queries can be found in the [sparql directory](restAPI/src/main/resources/sparql)

| SPARQL File | Description |
| --------------- | --------------- |
| cache-clusters.sparql | retrieves all Event or Relation clusters with their corresponding type and prototype |
| element-arguments.sparql  | retrieves arguments for a specified Event or Relation |
| entity-connections.sparql | retrieves the Event and Relation clusters that an Entity cluster is associated with |
| entity-element-connections.sparql | retrieves the Events and Relations that a "real-world" Entity is associated with |
| get-clusters.sparql  | retrieves cluster data for a specified Event or Relation cluster |
| get-entity-clusters.sparql | retrieves cluster data for a specified Entity cluster |
| get-handle.sparql | retrieves the handle for a specified Entity cluster|
| get-members.sparql | retrieves co-references ("real-world" elements) for a specified ERE cluster |
| get-prototype.sparql  | retrieves the prototype for a specified ERE cluster |
| get-type-docs.sparql | retrieves documents for a specified Event or Relation |
| get-type-docs-names.sparql  | retrieves documents and names for a specified Entity |
| member-arguments.sparql | retrieves co-reference details for a specified ERE cluster |
| prototype-arguments.sparql  | retrieves prototype arguments for a specified Event or Relation cluster |   

<br/>

## Running The Project
Prior to running the project, ensure that:
1. The connection to the triplestore is active 

    ``` 
   Tunnel into the Neptune triplestore e.g. ssh -L 8182:<triplestore endpoint>:8182 user@host 
    ```
    
1. The Elasticsearch instance is running
    - From the command line, run `docker ps -a` to get the instance name if it is unknown
    
        ```
            Output e.g. a03cc61eac09 docker.elastic.co/elasticsearch/elasticsearch:7.8.0   "/tini -- /usr/local…"   4 weeks ago  
                 Exited (255) 3 weeks ago   0.0.0.0:9200->9200/tcp, 0.0.0.0:9300->9300/tcp  <instance name>
        ```
   
    - Run  `docker start <instance name>`
    - To verify Elasticsearch is running, go to `http://localhost:9200`


### Rest API and Swagger
#### Run locally
1. From the command line, navigate to the restAPI build directory VERDI-UI/restAPI
1. To build, run `./gradlew `
1. (Optional) Populate elasticsearch: `java -jar build/libs/verdi-api-0.1.0.jar populate`
1. Run `java -jar build/libs/verdi-api-0.1.0.jar`
1. To access the Swagger UI, go to `http://localhost:8008/api/`

* For more information on the Rest API and Swagger [restAPI/README.md](restAPI/README.md)


### TA4 User Interface
#### Run locally
1. From the command line, navigate to the restAPI build directory VERDI-UI/GUI
1. To load the node-modules, run `npm install`
1. Run `npm run serve`
1. To access the TA4 UI, go to `http://localhost:8080/`

* For more information on the TA4 User Interface [GUI/README.md](GUI/README.md)
