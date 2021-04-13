import nltk
from nltk.corpus import stopwords
import string

print("Downloading English Stop words.")
nltk.download('stopwords')

def text_process(mess):
    nopunc = [char for char in mess if char not in string.punctuation]
    nopunc = ''.join(nopunc)
    return [word for word in nopunc.split() if word.lower() not in stopwords.words('english')]
