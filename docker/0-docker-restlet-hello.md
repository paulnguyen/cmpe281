
# Docker - Hello World with Restlet


* https://docs.docker.com/edge/engine/reference/commandline/docker/
* https://docs.docker.com/edge/engine/reference/commandline/build/
* https://docs.docker.com/edge/engine/reference/commandline/images/
* https://docs.docker.com/edge/engine/reference/commandline/run/
* https://docs.docker.com/edge/engine/reference/commandline/ps/
* https://docs.docker.com/edge/engine/reference/commandline/push/

* https://github.com/paulnguyen/cmpe281/tree/master/restlet


## Docker Build

### Docker File

```
FROM openjdk
EXPOSE 8080

ADD ./dist/app.jar /srv/app.jar
ADD ./dist/restlet.jar /srv/restlet.jar
CMD java -cp /srv/restlet.jar:/srv/restlet-json.jar:/srv/app.jar helloworld.HelloWorldServer
```

### Docker Build Command

```
	docker build -t restapi .
	docker images
```


## Docker Run

### Run with Default Network Settings

```
	docker run --name restapi -td restapi
	docker ps
```

### Run in Host Mode

```
	docker run --name restapi -td --net=host restapi
	docker ps
```

### Run in Bridge Mode

```
	docker run --name restapi -td -p 80:8080 restapi
	docker ps
```


## Local Build of Java API

### Makefile Build Rules for Restlet App

```
clean:
	rm -rf build/*
	rm -f dist/app.jar
	find . -name "*.class" -exec rm -rf {} \;

app: compile
	cd build ; jar -cvfe ../dist/app.jar HelloWorldServer .

test: compile app
	java -cp dist/restlet.jar:dist/restlet-json.jar:dist/json.jar:dist/app.jar helloworld.HelloWorldServer

compile: 
	javac -cp \
	dist/json.jar:\
	dist/restlet.jar:\
	dist/restlet-json.jar:\
	dist/restlet-jackson.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar \
	-d build \
	src/mailserver/*.java \
	src/helloworld/*.java
```

### Build Restlet Java API

``` 
	make clean
	make compile
	make app
```

### Run & Test Localhost version of Restlet Java API

```
	make test
	curl http://localhost:8080/restlet/hello
```


## Build and Deploy Docker Image to Docker Hub and Docker Cloud

### Login to Docker Hub

```
docker login -u <user> -p  
```

### Build Docker Hub Image

```
docker build -t <account>/<container>:<version> .

Exammple:

docker build -t paulnguyen/restapi:latest .
```

### Push Docker Image to Docker Hub

```
docker push <account>/<container>:<version> .

Example:

docker push paulnguyen/restapi:latest ; 
```

### Docker Cloud Deployment

```
1.  Create new Docker Cloud Service
2.  Select Docker Images from your Docker Hub 
3.  Select Docker Image (i.e. paulnguyen/restapi)
4.  Select Docker Image Tag (i.e. latest)
5.  Assign Service Name (i.e. restapi)
6.  Map Bridge Network Port 8080 to 80
7.  Create & Deploy 
```

### Test Deployment of Docker Cloud Version

```
Service Endpoints

    restapi		tcp/9090	http://restapi.8035ca22.svc.dockerapp.io:80

Test using Curl

	curl http://restapi.8035ca22.svc.dockerapp.io:80/restlet/hello	

```








