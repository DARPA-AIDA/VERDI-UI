# Dockerization and Deployment

This folder contains the scripts to containerize and deploy the VERDI-UI system.

## docker-compose files

1. docker-compose.yaml - this file launches the GUI and restAPI locally but uses the ElasticSearch and Blazegraph from the development environment
2. docker-compose-dev.yaml - this file is used for deployment to the development environment
3. docker-compose-standalone.yaml - this files launches the GUI, restAPI, ElasticSearch and Blazegraph locally

## Local Development with docker-compose

NOTE: You don't need to use docker-compose to deploy/test locally. You can still bring up the GUI, restAPI and Elasticsearch as you have before.

Docker files can be found at:
1. VERDI-UI/docker/local/verdi-ui-gui
2. VERDI-UI/docker/local/verdi-ui-restapi

Generally, the Dockerfile's won't need to change unless there are changes to the run commands.

For local development, use `./VERDI-UI/docker-compose.yaml`.

Docker compose is used to build and coordinate the VERDI-UI containers:
1. GUI
2. restAPI

This setup uses the ElasticSearch and Blazegraph from the development environment.

Inside `docker-compose.yaml`, the containers are named (the containers can resolve each other by these names):
1. verdi-ui-gui (this is the GUI)
2. verdi-ui-restapi (this is the restAPI)

To initially start the local environment with docker-compose, this command will build the containers and start them on the local host.

```
docker-compose -f docker-compose.yaml build
```

If you only made changes to one of the containers, you can just build that container with `docker-compose build {container_name}`:

```
docker-compose build verdi-ui-gui
```

After the container is rebuilt, you will need to restart it.

```
docker-compose restart verdi-ui-gui
```

Other common commands:

`docker-compose up`: This command will start your stopped containers without rebuilding them.  If the containers don't exist, the command will build them and start them. For example, if you reboot your computer, this command will restart your stopped containers so they don't need to be rebuilt again. This is useful for the ElasticSearch container so you don't need to repopulate ElasticSearch. 

`docker-compose down`: This will stop and destroy all the containers.

`CTRL+C`: In the terminal running the docker-compose, this will stop (but not destroy) the containers.

## DEV Deployment

Pushing to the development branch will start a Jenkins Job that automatically deploys the application to AWS Beanstalk.

AWS Elastic Beanstalk was used to facilitate the deployment. The scripts also use Elastic [Beanstalk CLI](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install.html) to initialize and control the Beanstalk environment.

The Jenkins job will
1. Run SonarQube code quality check on:
    1. ./GUI
    2. ./restAPI
2.  Build and push containers using the `docker-compose-dev.yaml`, images are pushed to the AWS Elastic Container Registry
3.  Deploy to Elastic Beanstalk
    1. The URL's (unless the Beanstalk Environment is deleted and recreated)
        1. restAPI: https://aida-ui-dev.verdi.nextcentury.com/api/#
        2. GUI: https://aida-ui-dev.verdi.nextcentury.com/
        3. ElasticSearch: https://aida-ui-dev.verdi.nextcentury.com:9200/
        4. Blazegraph: https://aida-ui-dev.verdi.nextcentury.com/blazegraph/#

Configuration of Docker files for Dev can be found at:
1. VERDI-UI/docker/dev/verdi-ui-gui
2. VERDI-UI/docker/dev/verdi-ui-restapi

Below is the Jenkins process to build the images and deploy to Elastic Beanstalk (this automated in Jenkins)
```
#Install Elastic Beanstalk CLI
pip3 install awsebcli --upgrade --user

#Create an Access Token for ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 606941321404.dkr.ecr.us-east-1.amazonaws.com

#Build Containers
docker-compose -f ./docker-compose-dev.yaml build

#Tag and Push Images to ECR
docker push 606941321404.dkr.ecr.us-east-1.amazonaws.com/verdi-ui-gui:latest
docker push 606941321404.dkr.ecr.us-east-1.amazonaws.com/verdi-ui-restapi:latest

#Deploy to elastic beanstalk (Permissions in IAM Role aida-jenkins-role)
cd ./docker/dev
~/.local/bin/eb deploy
cd ../../
```
### Authentication
The Dev environment uses AWS ALB with Cognito to authenticate users. Please contact Phi Le for the username and password.

### Scaling the Dev Environment to Zero
Using AWS Elastic Beanstalk CLI, the environment can easily be scaled to zero (terminates servers related to this Dev Beanstalk environment)

```
eb scale 0
```

It can also be scaled back up:

```
eb scale 1
```


## PROD Deployment
TBD

## Populating ElasticSearch
In the docker.env for each environment folder (local, dev, and prod), there is a flag to populate ElasticSearch 

```
POPULATE_ES=true
```

In the respective `application.properties` add:

```
ncc.ta2-graphs={add comma separated values of graph URIs from Blazegraph}
ncc.ta3-runs={add comma separated values of graph URIs from Blazegraph}
```

Note: This process will be updated with [AIDA-1893](https://nextcentury.atlassian.net/browse/AIDA-1893)

## Stand-Alone Deployment

This version of the deployment launches containers for:
1. GUI (http://localhost:8081/#/)
2. restAPI (http://localhost:8008/api/#/)
4. Blazegraph (http://localhost:9999/blazegraph/#/)

## Starting the containers

```
docker-compose -f ./docker-compose-standalone.yaml build
docker-compose -f ./docker-compose-standalone.yaml up
```

## Destroying the containers
Note: Container volumes (data) will be destroyed
```
docker-compose -f ./docker-compose-standalone.yaml down
```

## Loading Data into Blazegraph

TBD: See [AIDA-1893](https://nextcentury.atlassian.net/browse/AIDA-1893)

## Loading Data into ElasticSearch

TBD: See [AIDA-1893](https://nextcentury.atlassian.net/browse/AIDA-1893)


# Elastic Beanstalk

Folders:
1. .ebextensions - this folder houses custom configuration to the Beanstalk environment which can't be done via the AWS Console
2. .elasticbeanstalk - this folder houses configuration for the VERDI-UI-DEV Elastic Beanstalk Environment. The config in the `saved_configs` folder can be used to rebuild the environment.
3. Common EB CLI commands:
    1. Save the current environment: eb config save {name of Beanstalk Environment} --cfg {name of saved config}
        ```
        eb config save VERDI-UI-DEV --cfg {name of existing config in Beanstalk}
        ```
    2. Get a saved configuration from Elastic Beanstalk, this downloads the config into your `.elasticbeanstalk/saved_configs` folder:
        ```
        eb config get {name of existing saved config in Beanstalk}
        ```
    3. Create an environment from a saved config:
        ```
        eb create {name of Beanstalk Environment} --cfg {name of config from saved_configs folder without the .cfg.yaml}
        ```
    4. Update an environment from local modified config file:
        ```
        cat .\.elasticbeanstalk\saved_configs\{saved config}.cfg.yml | eb config {name of Beanstalk Environment}
        ```
    5. When encountering a platform bug, do this: eb init --platform "{name of Elastic Beanstalk platform}" 
        ```
        eb init --platform "arn:aws:elasticbeanstalk:us-east-1::platform/Multi-container Docker running on 64bit Amazon Linux/2.25.1" 
        ```
    6. After the EB Environment is up and runnning you can re-deploy the app if there are changes, this command should be issued in the same folder as the `Dockerrun.aws.json` file:
        ```
        eb deploy
        ```
# Dependencies
1. Route 53 - Route53 is used to forward `aida-ui-dev.verdi.nextcentury.com` to the VERDI UI and restAPI resources provisioned via Elastic Beanstalk
2. EFS for VERDI-UI persistance - An EFS is used to persist data for the VERDI-UI system between builds. Currently, only the Elasticsearch data folder is being persisted on this EFS

# Outstanding Issues:

1. If the environment is deleted and rebuilt, the Application Load Balancer will have a new DNS. Need to automate the process of registering the new DNS with Route53.
