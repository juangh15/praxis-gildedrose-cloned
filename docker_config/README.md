## Before you start:
If you want to perform only the docker run, better use the steps indicated in the README of the DockerHub repository in which the image was saved:
https://hub.docker.com/r/juangh15/gildedrose-api

This way you will use the official DockerHub image instead of doing the build.

## General information
This Dockerfile is used to build a Docker image to run the Gildedrose API from this repository.
The process of building the image with the Dockerfile has the following features:
* The RUN process in the Dockerfile uses the same vagrant provisioning commands present in the "vagrant_config" folder of this repository, in the file "provisioning-spring_api.sh". The same base system version (Ubuntu 18.04) was also used.
* To the RUN commands, have been added lines for deleting unnecessary files at the end of the build process, to make the image a little lighter.
* The maven build for the API is made during the build of the Docker image, just as it was done in the Vagrant provisioning. All maven and java files required for the maven build are downloaded and subsequently removed during the Docker build.
* Environment variables were added to customize the IP, port, username and password to connect the containerized API to the existing postgresql database.

## Steps for BUILD the Dockerfile:
#### 1 - Start up the required postgresql database container with the command:

	docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres

It is necessary to have the database in the background because during the Docker build, the maven build is also performed.
#### 2 - Get and save the IP Address that Docker assigned to the database container:

	docker inspect my-postgres | grep -i "IPAddress"

#### 3 - Change your directory to the current folder, to locate the Dockerfile:

	cd /YOUR_USER_PATH_TO_DIRECTORY/.../praxis-gildedrose-cloned/docker_config

#### 4 - Modify and execute the build command with the required parameters:
IMPORTANT: Instead of "172.17.0.2", the IP obtained from the DB in step 2 must be entered.

	docker build --no-cache --build-arg POSTGRES_IP_ARG=172.17.0.2 --build-arg POSTGRES_PORT_ARG=5432 --build-arg POSTGRES_USER_ARG=postgres --build-arg POSTGRES_PASSWORD_ARG=secret -t gildedrose-api .

##### EXPLANATION OF THE BUILD ARGS
* "POSTGRES_IP_ARG=172.17.0.2" : IP of the database to which the API will connect DURING THE MAVEN BUILD PROCESS, in this case "172.17.0.2" is used because it is the IP of the DB container obtained in step 2.
* "POSTGRES_PORT_ARG=5432" : Database port "5432" defined.
* "POSTGRES_USER_ARG=postgres" : Database user "postgres" defined (default).
* "POSTGRES_PASSWORD_ARG=secret": Database "secret" password defined.
##### IN CASE OF USING DIFFERENT PARAMETERS IN THE POSTGRES DATABASE, CHANGE THEIR ARGS IN THE BUILD COMMAND.

#### 5 - Check if the image was created:
Check that the image with the name "gildedrose-api" is present when running:

	docker images

## Steps for RUN the gildedrose-api image builded:
#### 1 - Start up the database:
(Skip this step if it has already been done during the build process).

You need to have a postgresql database running in the background, it is recommended to use a docker container with the following:

```docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres```

No additional steps are needed for the database, everything needed regarding relations will be done automatically by the SpringBoot API.
#### 2 - Create an internal network for the database and the API:
This step ensures that the communication between the DB containers and the API is isolated from other containers. Additionally, the IP of the DB can be referenced only with the name of the container ("my-postgres"), this for the connection that SpringBoot makes internally. To do this enter the commands:

```
docker network create backend_net
docker network connect backend_net my-postgres
```
In this case, the "backend_net" is the name of the internal network. The name can be replaced by any other.
#### 3 - Run the container:
To run the container it is necessary to define the parameters with which the connection to the "my-postgres" database will be made. This only requires the command:

```
docker run --name api-container --network backend_net --env POSTGRES_IP=my-postgres --env POSTGRES_PORT=5432 --env POSTGRES_USER=postgres --env POSTGRES_PASSWORD=secret -p 8080:8080 -d gildedrose-api
```
##### EXPLANATION OF THE PARAMETERS:
* "--name api-container": The name by which the container will be identified in this case will be "api-container".
* "--network backend_net": With this parameter, the container will connect to the "backend_net" created in step 2.
* "-p 8080:8080": The same port 8080 of the host will be mapped to the container.
* "gildedrose-api": The name of the image builded.

##### EXPLANATION OF THE "--env" (environment variables):
* "POSTGRES_IP=my-postgres" : IP of the database to which the API will connect, in this case "my-postgres" is used because it is the name of the DB container within the "backend_net" network.
* "POSTGRES_PORT=5432" : Database port "5432" defined.
* "POSTGRES_USER=postgres" : Database user "postgres" defined (default).
* "POSTGRES_PASSWORD=secret": Database "secret" password defined.

#### 4 - Verify that the container is running:
Wait a few minutes and the API will be exposed to the web address: 

http://localhost:8080/api/items

If it is working correctly, when entering the previous address from a browser (or a program like Postman), it should deliver a JSON file with an array composed of the items that are currently in the database.

If it does not appear after a few minutes, check with "docker ps" that the container is running on port 8080. If not, review the previous steps and delete any images or containers that may cause conflicts with the running.
