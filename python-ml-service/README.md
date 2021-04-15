# RabbitMQ implementation - Spam and Profanity Identification in Text Messages

## Context

Here lies the implementation of the ModBot algorithm found in the notebooks folder of our project's repository : https://github.com/andbluedev/Twitter-Clone-ML-Moderator/tree/master/notebooks

RabbitMQ is message queue service that can work with python files : https://www.rabbitmq.com/

## Implementation

RabbitMQ requires two files : send and receive, both are plugged to a defined connection method using the pika library from python. Requirements can be met by calling the requirements file.

```bash
pip install -r requirements.txt
```

Receive.py is an endless loop awaiting messages to be sent to its connection, in our case from the send.py file.
Receive.py works with joblib pickles originating from the ModBot notebook.

To launch RabbitMQ on your machine, download appropriate installer from https://www.rabbitmq.com/download.html.

Open a first command line console and begin by calling the receive file :

```bash
python receive.py
```

Open a second command line console And then call the send file :

```bash
python send.py
```

Step by step local implementation guidelines can be found here : https://www.rabbitmq.com/tutorials/tutorial-one-python.html


