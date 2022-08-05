#!/bin/bash
set -x

#cd restAPI
#if ${POPULATE_ES}; then java -jar restAPI/build/libs/verdi-api-$VERSION.jar populate; fi
#cd ..

java -jar restAPI/build/libs/verdi-api-$VERSION.jar --spring.profiles.active=standalone
