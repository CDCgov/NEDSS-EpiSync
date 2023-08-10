##

## Quick Start
Using $PROJECT_ROOT reference as that in publish folder as starting point,
build the docker image and run the behave script on that docker image.

## Generate test data CSV from DDL
```shell
$ cd $PROJECT_ROOT
$ docker build -t mansi-python-app -f services/test/Dockerfile .
$ docker run -it --rm -v $PROJECT_ROOT/services/test/episync_gen/docker_vol_shared:/docker_vol_shared --name my-running-app mansi-python-app
```
Run behave test from services/test/episync 
## XML to CSV convertor validation
```shell
$ behave features/publish.feature -f plain --no-capture
```
## html report for BDD test run
````shell
$ behave features/publish.feature -f html -o report.html
````

## Run karate tests in docker

## Docker Build test image 
````shell
$ docker build -t karate-test .
````
## Run docker test image 
````shell
$ docker run -it --rm -v "$(pwd)/question_bank/src":/src -w /src karate-test java -jar /karate.jar .
````

