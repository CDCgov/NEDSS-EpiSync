# FROM adoptopenjdk:11-jre-hotspot

# RUN curl -L -o karate.jar https://github.com/karatelabs/karate/releases/download/v1.4.0/karate-1.4.0.jar

FROM maven:3-jdk-11 
COPY services/test/episync/episync_qb/pom.xml .
# COPY episync/episync_qb/pom.xml .
FROM maven:3-jdk-11
COPY services/test/episync/episync_qb/pom.xml .
COPY episync/episync_qb/pom.xml .
RUN mvn clean install

FROM python:3.10
RUN apt-get update

# RUN mkdir -p /opt/episync
# WORKDIR /opt/episync

# RUN apt install -y libpq-dev
# RUN apt install -y libevent-dev libev-dev libevdev2
# RUN apt install -y git

# ADD cli/episync /opt/episync/episync
# ADD cli/requirements.txt /opt/episync/requirements.txt
# ADD cli/setup.py /opt/episync/setup.py
# ADD cli/Makefile /opt/episync/Makefile
# ADD services/test/README.md /opt/episync/README.md
# RUN python3 -m venv venv
#RUN make init
# RUN venv/bin/pip3 install -r requirements.txt
# RUN venv/bin/python3 setup.py install

RUN pip install behave
RUN pip install requests
RUN pip install behave-html-formatter
RUN pip install faker
COPY services/test/episync_gen/* /app/
WORKDIR /app/

CMD ["behave", "datagen.feature"]
