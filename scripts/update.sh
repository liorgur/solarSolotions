#!/bin/bash

docker rm -f $(docker ps -aq)
docker run -d -p 80:8080 liorgur/solar_solutions:latest

while true; do
    current_digest=$(docker images --digests | grep liorgur/solar_solutions | grep latest | awk '{print $3}')
    docker pull liorgur/solar_solutions:latest
    new_digest=$(docker images --digests | grep liorgur/solar_solutions | grep latest | awk '{print $3}')

    if [[ "$current_digest" != "$new_digest"]]; then
        docker rm -f $(docker ps -aq)
        docker run -d -p 80:8082 liorgur/solar_solutions:latest
        docker system prune -f
    fi
    sleep 60
done
