#!/bin/bash

# Terminate script if anything fails
set -e
# Build the project
mvn clean install

# Get ID of running java procesees (our app)
pid=$(pgrep java || echo "")
# If any processes were found, kill them
if [ -n "$pid" ]; then /bin/kill -9 $pid; fi

# Run the web app
cd djv-web
mvn spring-boot:run &
cd ..
