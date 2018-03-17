

# Docker - Java Restlet Starbucks API 

* https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v1


## PART I - Setup

### Install Docker Toolbox: 

* https://www.docker.com/products/docker-toolbox 
* https://www.docker.com/docker-mac 
* https://www.docker.com/docker-windows 
* https://www.docker.com/docker-ubuntu 

```
        NOTE:  This lab works best on Mac or Linux. 
        If you are using a Windows Machine, it is best to use
        the Docker Toolbox Option to run Docker in a Linux VM.
```

### Register for Docker Hub and Docker Cloud Accounts:

* https://hub.docker.com/ 
* https://cloud.docker.com 

### Link Docker Cloud to your AWS Account:

* https://docs.docker.com/docker-cloud/getting-started/ 
```
  Notes: Micro Free-Tier Instance in any AZ
```

### Download and Install Postman Desktop App:

* https://www.getpostman.com/ 



## PART II - LOCAL HOST

### Build and Run Localhost Version

* https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v1 

```
Compile, Build JAR and Run Local Instance on port 9090 using Makefile Targets:

compile:
    gradle build

jar: compile
    gradle shadowJar

run: 
    echo Starting Service at:  http://localhost:9090
    java -cp build/libs/starbucks-all.jar api.StarbucksServer
```


### Test using Postman Collection for Starbucks API

* https://github.com/paulnguyen/cmpe281/tree/master/postman

```
Using Postman's Starbucks Test Collection, run 
the "Post Starbucks API | Localhost" Test with 
Postman Console View Enable.

curl -X POST \
  http://localhost:9090/v1/starbucks/order \
  -d '{
  "location": "take-out",
  "items": [
    {
      "qty": 1,
      "name": "latte",
      "milk": "whole",
      "size": "large"
    }
  ]
}'

```



## PART III - LOCAL DOCKER

*  https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v1 

### Compile, Build JAR and Run Local Docker Instance on port 90 using Makefile Targets:

```
docker-build: 
    docker build -t starbucks .
    docker images

docker-run-bridge:
    docker run --name starbucks -td -p 90:9090 starbucks
    docker ps
```
 

### Test using Postman Collection for Starbucks API (Local Docker Test)

* https://github.com/paulnguyen/cmpe281/tree/master/postman

```
Using Postman's Starbucks Test Collection, 
run the "Post Starbucks API | Docker Local" 
Test with Postman Console View Enable.

curl -X POST \
  http://localhost:90/v1/starbucks/order \
  -d '{
  "location": "take-out",
  "items": [
    {
      "qty": 1,
      "name": "latte",
      "milk": "whole",
      "size": "large"
    }
  ]
}'

```




## PART IV -  DOCKER CLOUD

* https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v1 

### Using the docker.sh script, build and push a release to Docker Hub and then Deploy to Docker Cloud.

```
Note: When deploying to Docker Cloud, map the Container's Exported Port 9090 to Docker Cloud's Port 90.
```

### Test using Postman Collection for Starbucks API (on Docker Cloud)

```
Using Postman's Starbucks Test Collection, run the 
"Post Starbucks API |Docker Cloud (Host IP)" 
Test with Postman Console View Enable.  

You will need to update the Docker Host IP 
to use your setup.
```





