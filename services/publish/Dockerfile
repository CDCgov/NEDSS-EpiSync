FROM maven:3.8.3-openjdk-11 AS maven-build

COPY services/publish/pom.xml .
RUN mvn dependency:go-offline
COPY services/publish/src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:11-jre

ARG USERNAME=publish
ARG USER_UID=2001

RUN groupadd --gid $USER_UID $USERNAME && useradd -r --uid $USER_UID --gid $USER_UID $USERNAME

RUN mkdir -p /opt/episync
WORKDIR /opt/episync

COPY --from=maven-build /target/publish-0.0.1-SNAPSHOT.jar /opt/episync/publish.jar

USER publish

EXPOSE 8088
CMD ["java", "-jar", "-Duser.timezone=UTC", "/opt/episync/publish.jar"]
