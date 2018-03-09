

# Docker - Java Restlet Gumball API


* https://github.com/paulnguyen/cmpe281/tree/master/restlet/gumball


## Makefile

```
app: compile
	cd build ; jar -cvfe ../dist/app.jar GumballServer .

test: compile app
	java -cp dist/restlet.jar:dist/restlet-json.jar:dist/json.jar:dist/app.jar api.GumballServer

compile: 
	javac -cp dist/restlet.jar:dist/restlet-json.jar:dist/json.jar -d build \
	src/gumball/*.java \
	clients/loadtestclient/*.java \
	src/api/*.java

run:
	echo Starting Service at:  http://localhost:8080
	java -cp build:dist/restlet.jar:dist/restlet-json.jar:dist/json.jar api.GumballServer
```

## Dockerfile

```
FROM openjdk
EXPOSE 8080
ADD ./dist/app.jar /srv/app.jar
ADD ./dist/restlet.jar /srv/restlet.jar
ADD ./dist/restlet-json.jar /srv/restlet-json.jar
ADD ./dist/json.jar /srv/json.jar
CMD java -cp /srv/restlet.jar:/srv/restlet-json.jar:/srv/json.jar:/srv/app.jar api.GumballServer
```

## Docker Build

```
docker-build: app
	docker build -t gumball .
	docker images

docker-clean:
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-run-bridge:
	docker run --name gumball -td -p 80:8080 gumball
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop gumball
	docker rm gumball

docker-shell:
	docker exec -it gumball bash 
```


## Docker Build Steps

```
1.  make compile
2.  make app
3.  make docker-build
4.  make docker-run-bridge
```

## Test Gumball Docker API (Get Banner)

```
curl -X GET http://localhost:80/gumball
```

## Test Gumball Docker API (Insert Quarter)

```
curl -X POST \
  http://localhost:80/gumball \
  -H 'Content-Type: application/json' \
  -d '{
  "action": "insert-quarter"
}'
```


## Test Gumball Docker API (Turn Crank)

```
curl -X POST \
  http://localhost:80/gumball \
  -H 'Content-Type: application/json' \
  -d '{
  "action": "turn-crank"
}'
```


## Docker Build and Publish to Docker Hub (using Menu)

### Run Script

```
docker.sh
```

### Docker Menu Options

```
============================================
          D O C K E R   M E N U             
============================================
> gumball - /gumball:latest 
 
[1] login      - Login to Docker            
[2] images     - Show Docker Images         
[3] build      - Build Container Image      
[4] run        - Run Container              
[5] pull       - Pull Container Image       
[6] push       - Push Build to Docker Hub   
[7] ps         - Show Running Containers    
[8] rmi        - Remove Container Image     
[9] release    - Release to Docker Hub      
 
[+] More Options                        
[X] Exit Menu                              
 
Selection: 
```

### Docker Menu Steps

```
1.  login (to your docker hub account)
2.  build (to build docker hub image)
3.  push  (to push image to docker hub)
```











