##

## Quick Start
Using $PROJECT_ROOT reference as that in publish folder as starting point,
build the docker image and run the behave script on that docker image.


```shell
$ cd $PROJECT_ROOT
$ docker build -t mansi-python-app -f services/test/Dockerfile .
$ docker run -it --rm -v $PROJECT_ROOT/services/test/episync_gen/docker_vol_shared:/docker_vol_shared --name my-running-app mansi-python-app
```
