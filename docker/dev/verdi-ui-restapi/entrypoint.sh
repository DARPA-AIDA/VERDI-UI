#!/bin/bash
set -x

if ${POPULATE_ES}; then java -jar build/libs/verdi-api-$VERSION.jar populate; fi

java -jar build/libs/verdi-api-$VERSION.jar --spring.profiles.active=dev
