FROM maven:3.8.3-openjdk-11 AS maven-build

WORKDIR /build
COPY libs/questionbank/pom.xml /build/libs/pom.xml
WORKDIR /build/libs
RUN mvn dependency:go-offline
COPY libs/questionbank/src /build/libs/src
RUN mvn package -DskipTests
RUN mvn install

WORKDIR /build
COPY episync/pom.xml /build/episync/pom.xml
WORKDIR /build/episync
RUN mvn dependency:go-offline
COPY episync/src /build/episync/src
RUN mvn package -DskipTests
RUN mvn install

WORKDIR /build
COPY services/publish/pom.xml /build
RUN mvn dependency:go-offline
COPY services/publish/src /build/src
RUN mvn package -DskipTests

FROM eclipse-temurin:11-jre

ARG USERNAME=publish
ARG USER_UID=2001

RUN groupadd --gid $USER_UID $USERNAME && useradd -r --uid $USER_UID --gid $USER_UID $USERNAME

RUN mkdir -p /opt/episync
WORKDIR /opt/episync

COPY --from=maven-build /build/target/publish-0.0.1-SNAPSHOT.jar /opt/episync/publish.jar

USER publish

EXPOSE 8088
CMD ["java", "-jar", "-Duser.timezone=UTC", "/opt/episync/publish.jar"]
