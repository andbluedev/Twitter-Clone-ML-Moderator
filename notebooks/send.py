#!/usr/bin/env python
# coding: utf-8

# In[21]:


#!/usr/bin/env python
import pika
import joblib


connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

channel.queue_declare(queue='Touitter')


touit = "This is a test"

channel.basic_publish(exchange='', routing_key='Touitter', body=touit)
print(" [x] Sent : ", touit)



 



connection.close()


# In[ ]:




