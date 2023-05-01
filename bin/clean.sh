docker ps -a|grep episync| awk '{ print "docker stop "$1}'|sh
docker ps -a|grep episync| awk '{ print "docker rm "$1}'|sh
