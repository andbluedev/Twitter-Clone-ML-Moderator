import sys
import pika
import time
import json
import os

import numpy
import pandas
import sklearn

from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
import joblib


from profanity_check import predict_prob as prof_predict_prob
from profanity_check import predict as prof_predict


from utils import text_process
from database_manager import DatabaseManager


start_time = time.time()

print("Booting Modbot...\n")
print("Imports...\n")


try:
    MQ_HOST = os.environ['MQ_HOST']
    MQ_USER = os.environ['MQ_USER']
    MQ_PASSWORD = os.environ['MQ_PASSWORD']
    MQ_QUEUE = os.environ['MQ_QUEUE']

except KeyError as e:
    raise Exception(
        f"Missing (at least ) {str(e)} environment variable...\nVisit Github for more information.")


print("Creating Database Connection...\n")
db = DatabaseManager()

print("Loading model...\n")
model = joblib.load('model.pkl')
X_train = joblib.load('X_train.pkl')
y_train = joblib.load('y_train.pkl')


print("Creating pipeline...\n")
pipeline = Pipeline([
    ('bow', CountVectorizer()),
    ('tfidf', TfidfTransformer()),
    ('classifier', model),
])
print("Fitting pipeline...\n")
pipeline.fit(X_train, y_train)


def main():
    # connection and channel initiation
    credentials = pika.PlainCredentials(MQ_USER, MQ_PASSWORD)
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host=MQ_HOST, credentials=credentials))
    channel = connection.channel()

    channel.queue_declare(queue=MQ_QUEUE)

    end_time = time.time()
    run_time = end_time-start_time
    print("Boot completed in : ", run_time, "seconds \n")
    print("Modbot Ready ! \n\n\n")

    def callback(ch, method, properties, body):
        payload = json.loads(body.decode('utf-8'))
        tweet_text = payload["text"]
        tweet_id = payload["id"]

        new_body = text_process(str(tweet_text))

        sample_spam_predict = pipeline.predict([str(new_body)])[0]

        sample_prof_predict_prob = round(
            prof_predict_prob([str(new_body)])[0], 2)

        my_mod = {'label': str(sample_spam_predict), 'profanity_score': float(
            sample_prof_predict_prob), 'body': str(new_body)}

        print(f"{tweet_id} text processed: {tweet_text}\n\n\n")

        print("New message ! Label : ", sample_spam_predict,
              " / Profanity Index : ", sample_prof_predict_prob, "\n")

        print(f"Updating record {tweet_id}...")
        statement = """
            UPDATE tweets 
                SET category = '{label}', profanity_index = {prof_index}
            WHERE id = '{id}'
        """.format(id=tweet_id, prof_index=sample_prof_predict_prob, label=sample_spam_predict)

        print(statement)
        db.query(statement)

        print(f"Successfully updated record {tweet_id}!")

        return my_mod

    channel.basic_consume(
        queue=MQ_QUEUE, on_message_callback=callback, auto_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C \n ------------------ \n')
    channel.start_consuming()


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print('Interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)
