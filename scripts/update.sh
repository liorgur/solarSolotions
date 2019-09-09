#!/bin/bash

docker rm -f $(docker ps -aq)
docker run -d -p 80:8080 liorxmed/xmed:latest

while true; do
    current_digest=$(docker images --digests | grep liorxmed/xmed | grep latest | awk '{print $3}')
    docker pull liorxmed/xmed:latest
    new_digest=$(docker images --digests | grep liorxmed/xmed | grep latest | awk '{print $3}')

    if [ "$current_digest" != "$new_digest"]; then
        docker rm -f $(docker ps -aq)
        docker run -d -p 80:8080 liorxmed/xmed:latest
        docker system prune -f
    fi
    sleep 60
done
