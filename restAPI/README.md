# Verdi API Swagger Server
Verdi API is a rest server to facilitate UI data requests

## To build
Run `./gradlew` which is the same as running `./gradlew clean build`

This will produce "build/libs/verdi-api-#.#.jar"

## Running the project
### From code
Run `./gradlew bootrun`. This will use gradle to start the swagger server and UI.

### From the .jar
Run `java -jar build/libs/verdi-api-#.#.jar` in the root project directory.

## Accessing the Swagger UI
The Swagger UI is helpful for debugging the queries into the triple store without having to run the 
TA4 User Interface. Once the server is running, access the swagger UI at http://localhost:8008/api/#/

## To clean 
Running "gradle clean" will delete the build directory and all generated source

## Index

### Directory Details:

    > restAPI
        > .gradle/
            ==> The gradle build directory
            
        > build/
            ==> The project build directory
            
        > gradle/
            ==> Gradle configuration and jar file

        > src/
            ==> The source directory
            
            > generated/
                ==> Compiled java files     

            > main/
                ==> Utilities and classes for the rest application

                > java/com/ncc/verdi/
                    ==> Java main, Elasticsearch caching, and data utilities files
                    
                        > api
                            ==> Swagger generated API utilities and API controller classes
                    
                > resources/
                    ==> Sparql properties and configuration files
                    
                        > sparql
                            ==> Individual sparql queries                  

                > swagger/
                    ==> Contains open API setup for Swagger server
            > test
                ==> Junit5 Test Framework 
                
                > java/com/ncc/verdi/test
                    ==> Unit testing
                 

## Update to Yaml File
The OpenAPI document can be found [here](src/main/swagger/openapi.yaml)

Rebuild the project if there are changes to the OpeanAPI document

#### Future Development Notes
When adding new APIs to the OpenAPI document, new controller classes must be added under com.ncc.verdi.api

ApiUtil is auto-generated from [swagger code gen](https://swagger.io/tools/swagger-codegen/) and used for setting example response. If there are no example responses then it'll be no longer needed.

## Running with Elasticsearch cached information
### Prerequisites
1. Java 11
1. Access to a SPARQL endpoint

    ` Tunnel into the Neptune triplestore e.g. ssh -L 8182:<triplestore endpoint>:8182 user@host `
    
1. Elasticsearch 7.6.2 database running on localhost:9200. Here is a link to the [Docker tutorial](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

### Populating Elasticsearch database
To populate graphs from Neptune, please modify [application.properties](src/main/resources/application.properties) and run the following command

```
java -jar build/libs/verdi-api-$VERSION.jar populate
```

**Note:** Depending on the number of graphs specified, this can take a long time.

## Using Your Own Triple-Store and a Local ElasticSearch container

### Using Blazegraph:
1. Create a folder for the Blazegraph executable
2. Download the blazegraph.jar at [Blazegraph](https://github.com/blazegraph/database/releases/tag/BLAZEGRAPH_2_1_6_RC) into the above folder
3. `cd` into the folder
4. Launch Blazegraph
    ```
    java -server -Xmx4g -jar blazegraph.jar 
    ```
    Note: Adjust maximum java heap size as necessary

5. The Blazegraph UI can be reached at `http://127.0.0.1:9999/blazegraph`

### Importing TTL's

1. Import TTL's as a named graph
    ```
    curl -D- -H 'Content-Type: text/turtle' --upload-file {ttl file} -X POST 'http://127.0.0.1:9999/blazegraph/sparql?context-uri={URI of named graph}}'
    ```

2. URI format require by the VERDI-UI system

    1. TA2 URI - {base uri}/TA2/{TA2}/{TA1}

        Example:
        ```
        curl -D- -H 'Content-Type: text/turtle' --upload-file OPERA_TA1.OPERA_TA2.ttl -X POST 'http://127.0.0.1:9999/blazegraph/sparql?context-uri=https://www.nextcentury.com/TA2/OPERA-20210215/OPERA-20210117' 
        ```

    2. TA3 URI - {base uri}/TA3/{TA3}/{TA2}/{TA1}/{Hypothesis ID}

        Example:
        ```
        curl -D- -H 'Content-Type: text/turtle' --upload-file OPERA_TA1.OPERA_TA2.OPERA_TA3.AIDA_M36_TA3_E201.F1.H000.ttl -X POST 'http://127.0.0.1:9999/blazegraph/sparql?context-uri=https://www.nextcentury.com/TA3/E201/OPERA-20210217/OPERA-20210215/OPERA-20210117/OPERA_TA3.AIDA_M36_TA3_E201.F1.H000' 
        ```

### Update VERDI API settings for Blasegraph

1. Update the `tripestore` settings in `./VERDI-UI/restAPI/src/main/resources/application.properties`, an exmaple of Blazegraph settings is included at: `./VERDI-UI/restAPI/src/main/resources/application-blazeexample.properties`

    ```
    triplestore.scheme=http
    triplestore.host=127.0.0.1
    triplestore.port=9999
    triplestore.query.namespace=blazegraph/sparql
    ```

### Populating ElasticSearch with TA2

1. Setup ElasticSearch using the ElasticSearch DockerHub Image:

    ```
    docker network create es-network 
    docker run -d --name elasticsearch --net es-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.11.1 
    ```

2. Update ElasticSearch settings in `./VERDI-UI/restAPI/src/main/resources/application.properties`:

    1. Update the ElasticSearch settings beginning with `elastic`:

        ```
        elastic.scheme=http
        elastic.host=localhost
        elastic.port=9200
        ```

3. Building and running the populate ElasticSearch command:

    1. Update the TA2 graphs list in `./VERDI-UI/restAPI/src/main/resources/application.properties`

        1. For ncc.graphs, please list all TA2 URI's that were imported into your triplestore

        ```
        ncc.graphs={Comma separated list of TA2 URI's to index into ElasticSearch}
        ```

    2. Build the API jar, in the `/VERDI-UI/restAPI folder`:

        1. Build the VERDI API jar
        ```
        ./gradlew -x test
        ```

        2. Run population of ElasticSearch with TA2 data:
        ```
        java -jar ./VERDI-UI/restAPI/build/libs/verdi-api-0.1.0.jar populate
        ```

4. Start the VERDI API:
    In ./VERDI-UI/restAPI
    ```
    ./gradlew bootRun
    ```
    or 
    ```
    java -jar ./VERDI-UI/restAPI/build/libs/verdi-api-0.1.0.jar
    ```
5. Start the VERDI UI:
    In ./VERDI-UI/GUI
    ```
    npm run serve
    ```
6. Navigating to VERDI UI:
    Open an internet browser and navigate to:
    ```
    http://localhost:8080/
    ```

## Testing and Code Coverage

1. build.gradle is setup with JUnit5 and Jacoco Reports

2. Testing currently runs with the gradle build

3. The Jenkins job for VERDI-UI has been setup with the SonarQube Jacoco report which can be found in [SonarQube](http://3.218.245.112:9000/dashboard?id=VERDI-UI-restAPI).

4. Running gradlew with a differnt Java Spring Boot profile.  By Default the restAPI will run with the `application.properties` profile from the project resources folder. To run ./gradlew with a different profile, i.e. the `application-jenkins.properties` profile:

    ```
    /gradlew -PspringProfile=jenkins clean build test
    ```

### Outstanding:
1. Jenkins instance doesn't have enough memory to start its own ElasticSearch container for testing, in `DataUtils.java` using the VPC local IP of VERDI-UI-DEV for `ES_ADDRESS`. This currently works for the AIDA-UI-DEV environment as well.

2. As testing becomes more dynamic:
    1. Jenkins isn't large enough to run current systems locally for testing. 2. We should setup a Boss-Worker configuration for Jenkins to spawn workers as needed. This will also allow us to descrease the size of the current Jenkins instance. The workers will be created dynamically, launch a test system, perform dynamic testing and then terminate.
    3. This will keep us from having to increase the size of the Boss Jenkins instance or having larger testing instances running. 

