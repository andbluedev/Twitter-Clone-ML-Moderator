# RabbitMQ implementation - Spam and Profanity Identification in Text Messages

## Context

Here lies the implementation of the ModBot algorithm found in the notebooks folder of our project's repository : https://github.com/andbluedev/Twitter-Clone-ML-Moderator/tree/master/notebooks

RabbitMQ is message queue service that can work with python files : https://www.rabbitmq.com/

## Implementation

RabbitMQ requires two files : send and receive, both are plugged to a defined connection method using the pika library from python.

Receive.py is an endless loop awaiting messages to be sent to its connection, in our case from the send.py file.
Receive.py works with joblib pickles originating from the ModBot notebook.

