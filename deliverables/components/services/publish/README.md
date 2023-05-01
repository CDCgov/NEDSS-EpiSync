## Overview

Episync service to publish STLT tabular data to be consumed by CDC. Data is validated and conforms to Episync Data Dictionary.

## Quickstart

```shell
$ cd $PROJECT_ROOT/deliverables
$ docker build -t episync/docker-publish:1.0-SNAPSHOT -f Dockerfile.publish .
$ docker run -d -p 8088:8088 \
$       -v $PROJECT_ROOT/deliverables/cli/episync.db:/opt/episync/episync.db \
$       -v ~/.aws:/home/publish/.aws \
$       episync/docker-publish:1.0-SNAPSHOT
```
Define and set the path to project root directory in `$PROJECT_ROOT` variable or substitute when run.
Use your path to `episync.db` file created by Episync `dictionary` service.

Application is deployed as a microservice and can be accessed locally via the URL http://localhost:8088/api-docs

## Passing AWS Credentials to Docker Container
The argument `-v ~/.aws:/home/publish/.aws` mounts the contents of local `.aws` folder including `config` and `credentials` files to the container working folder so that when it runs the application, it will find and use these.
The `credentials` file should contain AWS access keys associated with a user that has the necessary S3 permissions defined under the '[episync]' profile:

```agsl
[episync]
aws_access_key_id=...
aws_secret_access_keyid=...
```

## Authorization

All requests to the service need to be authorized and include a bearer token in the request headers. To acquire a bearer token, you can send a request to the `oauth/token` URL, e.g.:
```shell
$ curl -X 'POST' 'http://localhost:8088/oauth/token' \
$ -H 'Content-Type: application/json' -d '{"username": "epi","password": "sync!"}'
```
After obtaining the token, include it in the authorization header for all subsequent requests (replace with a real obtained token value):
```shell
$ curl -X 'GET' 'http://localhost:8088/feed/all' \
$  -H 'accept: application/json' \
$  -H 'Authorization: Bearer $API_TOKEN'
```

