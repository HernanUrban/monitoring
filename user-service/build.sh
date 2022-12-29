#!/bin/bash
./mvnw clean install -DskipTests
docker build -t user-service:latest .