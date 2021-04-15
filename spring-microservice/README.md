# Touitter REST API

Spring Boot Application backed by a PostgreSQL database and a RabbitMQ queue (consumed linked to a python).

The REST API uses java, maven and docker:

* [installing java](https://www.oracle.com/fr/java/technologies/javase/jdk12-archive-downloads.html) (version used: openjdk 12.0.1)
* [installing maven](https://maven.apache.org/install.html) (version used: 3.6.2)
* [installing docker](https://docs.docker.com/get-docker/) (version used: 20.10.5)

## Setting Up the Database

On a postgreSQL instance (local or remote) create a dev user and database.

```sql
CREATE USER twitter_clone_user_dev WITH password '<CHOOSE_PASS_WORD>';
CREATE DATABASE twitter_clone_dev WITH OWNER twitter_clone_user_dev;
```

We chose to create and manage the database schema manually.

To generate the PostgreSQL database schema, run the sql satements in `../sql/generate_schema.sql`.

## Running the application

⚠️ Define and export the environment variables of the `RABBIT SPECIFIC` section as described [here]( https://github.com/andbluedev/Twitter-Clone-ML-Moderator#environment-variables) **before running any python file***! ⚠️

After defining the environment variables, create the following bash script and run it.

```bash
#  start_dev.sh
./mvnw clean

source ../.env

./mvnw spring-boot:run -Dspring.profiles.active=dev
```

Make the bash script executable:
```bash
chmod +x ./start_dev.sh
```

To run it:
```bash
# Make the bash script executable
./start_dev.sh
```

## Running the app using Docker

The application in production is run using a docker container, but we still run it on any computer if it supports docker for developement or just simply running the application.

### Building the Docker image

Build the doker image and tag it as `touitter-restapi:v1`.

```bash
docker build -t touitter-restapi:v1 .
```

### Running the docker container

Running the docker container and exposing the spring boot application to port 8000 of the host (to access it on [localhost:8000](localhost:8000)).

```bash
docker run -p 8000:8000 touitter-restapi:v1
```
