import pika
import os
import joblib


try:
    MQ_HOST = os.environ['MQ_HOST']
    MQ_USER = os.environ['MQ_USER']
    MQ_PASSWORD = os.environ['MQ_PASSWORD']
    MQ_QUEUE = os.environ['MQ_QUEUE']
except KeyError as e:
    raise Exception(f"Missing (at least ) {str(e)} environment variable...\nVisit Github for more information.")


credentials = pika.PlainCredentials(MQ_USER, MQ_PASSWORD)
connection = pika.BlockingConnection(
    pika.ConnectionParameters(host=MQ_HOST, credentials=credentials))
channel = connection.channel()

channel.queue_declare(queue=MQ_QUEUE)


touit = "This is a test"

channel.basic_publish(exchange='', routing_key=MQ_QUEUE, body=touit)
print(" [x] Sent : ", touit)


channel.close()
connection.close()
