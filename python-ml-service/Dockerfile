FROM python:3.7.2-slim-stretch

WORKDIR /src
# copy local python scripts into container
COPY . /src/

ENV PYTHONUNBUFFERED=1

RUN apt-get update && apt-get install -y gcc python-dev libldap2-dev libsasl2-dev libssl-dev
# create virtualenv
RUN pip3 install -r requirements.txt

# puts virtualenv utils at top of path
# commands from inside container will run venv versions
# https://stackoverflow.com/questions/44077407/is-there-a-way-to-automatically-activate-a-virtualenv-as-a-docker-entrypoint
# https://pythonspeed.com/articles/activate-virtualenv-dockerfile/
#
ENV VIRTUAL_ENV=/src
ENV PATH="$VIRTUAL_ENV/bin:$PATH"

CMD ["python"]