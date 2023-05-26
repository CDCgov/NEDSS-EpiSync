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

RUN curl https://dl.min.io/client/mc/release/linux-amd64/mc --create-dirs -o /opt/episync/minio-binaries/mc
RUN chmod +x /opt/episync/minio-binaries/mc
RUN echo "/opt/episync/minio-binaries/mc alias set episync http://minio1:9000 minioadmin minioadmin" > /root/.bashrc
ENV PATH /opt/episync/venv/bin:/opt/episync/minio-binaries/:$PATH

CMD bash