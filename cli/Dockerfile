FROM python:3.10

WORKDIR /opt/episync

RUN mkdir -p /opt/episync
ADD requirements.txt /opt/episync/requirements.txt

RUN python3 -m venv venv
RUN venv/bin/pip3 install -r requirements.txt

ADD episync-docker.ini /opt/episync/episync.ini
ADD hl7.txt /opt/episync/hl7.txt
ADD hl7.xml /opt/episync/hl7.xml
ADD elr.xml /opt/episync/elr.xml
ADD hl7-2.xml /opt/episync/hl7-2.xml
ADD setup.py /opt/episync/setup.py
ADD episync /opt/episync/episync
ADD README.md /opt/episync/README.md

RUN venv/bin/python3 setup.py install

ENV PATH /opt/episync/venv/bin:$PATH