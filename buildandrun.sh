#!/bin/bash
cd user-service
./build.sh
cd ..
cd address-service
./build.sh
cd ..
cd docker
docker-compose -p "monitoring" up -d

