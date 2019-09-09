
## PART I - Setup

    Install Docker Desktop: 

        https://www.docker.com/products/docker-toolbox
        https://www.docker.com/docker-mac
        https://www.docker.com/docker-windows
        https://www.docker.com/docker-ubuntu

        NOTE:  This lab works best on Mac or Linux. 
        If you are using a Windows Machine, it is best to use
        the Docker Toolbox Option to run Docker in a Linux VM. 

    Register for Docker Hub Account:

        https://hub.docker.com/

    Set up a Docker Host to your AWS Account:

		https://github.com/paulnguyen/cmpe281/blob/master/aws/dockerhost/dockerhost.md

    Download and Install Postman Desktop App:

        https://www.getpostman.com/


## PART II - LOCAL HOST

	SOURCE:  https://github.com/paulnguyen/cmpe281/tree/master/restlet/starbucks_v1

	Compile, Build JAR and Run Local Instance on port 9090 using Makefile Targets:

	compile:
	    gradle build

	jar: compile
	    gradle shadowJar

	run: 
	    echo Starting Service at:  http://localhost:9090
	    java -cp build/libs/starbucks-all.jar api.StarbucksServer


## PART III - LOCAL DOCKER

	SOURCE:  https://github.com/paulnguyen/cmpe281/blob/master/labs/lab4/starbucks.zip

	Compile, Build JAR and Run Local Docker Instance on port 90 using Makefile Targets:

	 docker-build: 
	    docker build -t starbucks .
	    docker images

	docker-run-bridge:
	    docker run --name starbucks -td -p 90:9090 starbucks
	    docker ps


## PART IV -  DOCKER ON AWS

	SOURCE:  https://github.com/paulnguyen/cmpe281/blob/master/labs/lab4/starbucks.zip

	Using the docker.sh script, build and push a release to Docker Hub and then Deploy to Docker on AWS.

	Note: When deploying to Docker, map the Container's Exported Port 9090 to Docker Port 90.



