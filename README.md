# IFMIS_API

This repository contains an API application built with Docker support.

## Prerequisites

Before you begin, ensure you have the following installed:
- Docker
- Docker Compose
- VPN access to "DoAT_User" network

## Getting Started

Follow these steps to set up and run the application locally:

1. Connect to VPN:
    - Connect to the "DoAT_User" VPN using credentials
    - Ensure you have an active VPN connection before proceeding

2. Build and start the application:
```bash
docker compose -f docker-compose.dev.yml up -d
```

3. Retrieve the authentication secret key:
```bash
docker exec ifmis_api cat /home/doat/.secureapi/secret.key
```
Save this key as it will be required for API authentication.

4. To get the logs for the application use:
```bash
docker exec ifmis_api tail -500 /app/logs/ifmis_api.log
```

## Application Access in Docker

The API will be available at `http://localhost:8081`. 
Please free the port if it is in use or change the Dockerfile and 
docker-compose.yml expose port

## Application Access in local

The API will be available at `http://localhost:8090`.
Please free the port if it is in use and to build locally go to project's
edit configuration > set active profile has "test" or add environment
variable where "Name: SPRING_PROFILES_ACTIVE" and "Value: test" or in
the "VM Options" field, add "-Dspring.profiles.active=test"

## Stopping the Application

To stop the application and remove containers:
```bash
docker compose -f docker-compose.dev.yml down
```

## Troubleshooting

1. If you cannot connect to the API:
    - Verify that your VPN connection is active
    - Ensure port 8081(docker) or 8090(local) is not being used 
   by another application
    - Check if the Docker containers are running with `docker ps`

2. If you cannot retrieve the secret key:
    - Ensure the containers are running
    - Verify that you're using the correct container name (ifmis_api)

## Contributing

Please read our contributing guidelines before submitting pull requests