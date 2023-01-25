#!/bin/bash

cd /home/ubuntu/service/$1
git pull origin $1
./gradlew --stacktrace clean build
cp build/libs/project-0.0.1-SNAPSHOT.jar docker/$1/project.jar
cd docker/$1
sudo docker-compose up -d --build --force-recreate
