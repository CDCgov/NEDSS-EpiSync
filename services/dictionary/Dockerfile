FROM python:3.10
RUN apt-get update

RUN mkdir -p /opt/episync
WORKDIR /opt/episync

RUN apt install -y libpq-dev
RUN apt install -y libevent-dev libev-dev libevdev2
RUN apt install -y git

ADD cli/episync.db /opt/episync/episync.db
ADD cli/episync /opt/episync/episync
ADD cli/requirements.txt /opt/episync/requirements.txt
ADD cli/setup.py /opt/episync/setup.py
ADD cli/Makefile /opt/episync/Makefile
ADD cli/README.md /opt/episync/README.md
ADD services/dictionary/episync.ini /opt/episync/episync.ini

RUN python3 -m venv venv
#RUN make init
RUN venv/bin/pip3 install -r requirements.txt
RUN venv/bin/python3 setup.py install

EXPOSE 8014
CMD ["/opt/episync/venv/bin/episync","api","start"]
