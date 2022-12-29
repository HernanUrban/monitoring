#!/bin/bash
./mvnw clean install -DskipTests
docker build -t address-service:latest .