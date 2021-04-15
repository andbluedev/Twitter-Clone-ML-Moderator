# RabbitMQ implementation - Spam and Profanity Identification in Text Messages

## Context

Here lies the implementation of the ModBot algorithm found in the notebooks folder of our project's repository : [notebooks](../notebooks)

RabbitMQ is message queue service that can work with python files : https://www.rabbitmq.com/


## Implementation

RabbitMQ requires two files : send and receive, both are plugged to a defined connection method using the pika library from python. Requirements can be met by calling the requirements file.

## Installing Development Environement

[receive.py](./receive.py): is an endless loop awaiting messages to be sent to its connection, in our case from the send.py file.
[receive.py](./receive.py) works with joblib pickles originating from the ModBot notebook.

Installing python dependencies.

```bash
pip install -r requirements.txt
```

To launch RabbitMQ on your machine, download appropriate installer from https://www.rabbitmq.com/download.html.

Open a first command line console and begin by calling the [receive file](./receive.py) :

```bash
python receive.py
```

Open a second command line console and then call the [send file](./send.py) :

```bash
python send.py
```

Step by step local implementation guidelines can be found here : https://www.rabbitmq.com/tutorials/tutorial-one-python.html

## Running the app using Docker

The application in production is run using a docker container, but we still run it on any computer if it supports docker for developement or just simply running the application.


Building the docker image.

```bash
docker build -t touitter-ml-service:v1 .
```

Running the docker container from the previous image.

```bash
docker run -p 3000:80 touitter-ml-service:v1 . .
```
