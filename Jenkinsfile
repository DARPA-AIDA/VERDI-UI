pipeline {
    agent none
    stages {
        stage('Clean Up') {
            agent any
            steps {
                sh 'kill -9 $(lsof -t -i:8008) || true'
                sh 'docker stop verdi-ui-test-es || true'
                sh 'docker rm verdi-ui-test-es || true'
            }
        }
        stage('Start ES Docker in Background') {
            agent any
            steps {
                sh 'docker run -m 512m -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name verdi-ui-test-es elasticsearch:7.11.1'
                sh 'sleep 15'
            }
        }
        stage('Build/start the restAPI Server and run tests') {
            agent any
            steps {
                sh 'cd restAPI && ./gradlew -PspringProfile=jenkins clean build test'
                sh 'java -Xmx1024m -jar restAPI/build/libs/verdi-api-0.1.0.jar --spring.profiles.active=jenkins &'
                sh 'sleep 15'
            }
        }
        stage('SonarQube scan on restAPI') {
            agent any
            steps {
                sh 'cd restAPI && /opt/sonar-scanner-cli/bin/sonar-scanner'
            }
        }
        stage('Start GUI Server and run tests') {
            agent any
            tools {nodejs "JenkinsNodeJS"}
            steps {
                sh 'cd GUI && npm install -g node-gyp'
                sh 'cd GUI && npm install'
                sh 'cd GUI && npm run test:unit'
            }
        }
        stage('SonarQube scan on GUI') {
            agent any
            tools {nodejs "JenkinsNodeJS"}
            steps {
                sh 'cd GUI && /opt/sonar-scanner-cli/bin/sonar-scanner'
            }
        }
        stage('Clean up') {
            agent any
            steps {
                sh 'kill -9 $(sudo lsof -t -i:8008) || true'
                sh 'docker stop verdi-ui-test-es || true'
                sh 'docker rm verdi-ui-test-es || true'
            }
        }        
    }
}

