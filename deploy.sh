#!/bin/bash

# Terminate script if anything fails
set -e

# Set constants
APP="/etc/init.d/djv-app"

# Build the project
mvn clean package

# Now the executable jar file can be found under djv-web/target/djv-web-VERSION.jar

# Stop the app if it is running
$APP stop

# Start the app
$APP start
