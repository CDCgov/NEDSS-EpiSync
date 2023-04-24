FROM maven:3.8.3-openjdk-11 AS maven-build

COPY components/services/publish/pom.xml .
RUN mvn dependency:go-offline
COPY components/services/publish/src ./src
RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

ARG USERNAME=publish
ARG USER_UID=2001

RUN apk add --no-cache shadow
RUN groupadd --gid $USER_UID $USERNAME && useradd -r --uid $USER_UID --gid $USER_UID $USERNAME

RUN mkdir -p /opt/episync
WORKDIR /opt/episync

COPY --from=maven-build /target/publish-0.0.1-SNAPSHOT.jar /opt/episync/publish.jar
ADD cli/episync.db /opt/episync/episync.db

USER publish

EXPOSE 8080
CMD ["java", "-jar", "-Duser.timezone=UTC", "/opt/episync/publish.jar"]

