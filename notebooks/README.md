# Spam and Profanity Identification in Text Messages

## Context

Our algorithms is called ModBot : A NLP algorithm whose purpose is to identify spam and quantify profanity in text messages.

ModBot's code can be found in the "Touitter_ModBot" notebook. Module version requirements can be found in the "requirements.txt" file. Python 3.7.1 was used.

## Dataset

ModBot was created using the 'SMS Spam Collection Dataset' from Kaggle : https://www.kaggle.com/uciml/sms-spam-collection-dataset
The data consists of 5,572 text messages flagged by either 'spam' or 'ham' (safe/normal message).

## Notebook

Once requirements are met and module versions are correctly installed the notebook can be executed. Modules have been installed using pip 21.0.1 : https://pypi.org/project/pip/

```bash
pip install -r requirements.txt
```

The notebook provides some data evaluation before defining the prediction algorithm.

The ML model selected for ModBot is a Multinomial Naive Bayes model. 

The SKLearn Pipeline method is used to accelerate sample predictions.

Joblib pickles are dumped in this notebook for the RabbitMQ message queue service used in the complete project implementation.


