import os
import sys
import pika
from profanity_check import predict_prob as prof_predict_prob
from profanity_check import predict as prof_predict
from sklearn.pipeline import Pipeline
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from nltk.corpus import stopwords
import joblib
import numpy
import pandas
import string
import sklearn
import time

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


print("Setting up text processing...\n")


def text_process(mess):
    nopunc = [char for char in mess if char not in string.punctuation]
    nopunc = ''.join(nopunc)
    return [word for word in nopunc.split() if word.lower() not in stopwords.words('english')]


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

        new_body = text_process(str(body))

        sample_spam_predict = pipeline.predict([str(new_body)])

        sample_prof_predict_prob = prof_predict_prob([str(new_body)])

        my_mod = {'label': str(sample_spam_predict), 'profanity_score': float(
            sample_prof_predict_prob), 'body': str(new_body)}

        print("New message ! Label : ", sample_spam_predict,
              " / Profanity Index : ", sample_prof_predict_prob, "\n")
        print("Body: %r" % body, "\n\n\n")

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
