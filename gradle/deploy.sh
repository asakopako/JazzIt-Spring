#!/usr/bin/env bash

docker stop jazzit
docker rm jazzit
docker rmi jazzit
cd /root/jazzit/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test
docker logs -f jazzit