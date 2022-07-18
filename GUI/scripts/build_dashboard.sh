#!/bin/bash
# A script to run build commands and create a war file to be deployed to a development or production environment

BUILD=${1:-dash}
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
source "../interface.properties"

#Checks for the build version and prints an error message if it is not found
if [ -z "$BUILD_VERSION" ]; then
    printf "Error: Could not read interface.properties file"
else
  app_path="${app_path}-${BUILD_VERSION}"
fi

# Remove old war files and create a new one based on this release version
rm -rf dist/* && rm -f target/*.war && mkdir -p target
vue-cli-service build --name=$app_path --dest=dist/$app_path/
cd dist/$app_path/ && zip -r $app_path.war *
cd ../../ && mv dist/$app_path/$app_path.war target/$app_path.war


