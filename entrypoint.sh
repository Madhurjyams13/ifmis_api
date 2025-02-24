#!/bin/sh

# Ensure the .secureapi directory exists and has correct permissions
mkdir -p "$USER_HOME"/.secureapi
chmod 700 "$USER_HOME"/.secureapi

# Start the application
exec java -jar app.jar \
    --logging.file.path=/app/logs \
    --logging.file.name=/app/logs/ifmis_api.log \
    -Duser.home="$USER_HOME"