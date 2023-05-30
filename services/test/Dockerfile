FROM python:3.10
RUN apt-get update

RUN mkdir -p /opt/episync
WORKDIR /opt/episync

RUN apt install -y libpq-dev
RUN apt install -y libevent-dev libev-dev libevdev2
RUN apt install -y git

ADD cli/episync /opt/episync/episync
ADD cli/requirements.txt /opt/episync/requirements.txt
ADD cli/setup.py /opt/episync/setup.py
ADD cli/Makefile /opt/episync/Makefile
ADD cli/README.md /opt/episync/README.md
RUN make init
RUN venv/bin/pip3 install -r requirements.txt
RUN venv/bin/python3 setup.py install

RUN pip install behave
RUN pip install faker
COPY services/test/episync_gen/* /app/
WORKDIR /app/

CMD ["behave"]