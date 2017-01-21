
 	_____             _             
	|  __ \           | |            
 	| |  | | ___   ___| | _____ _ __ 
 	| |  | |/ _ \ / __| |/ / _ \ '__|
 	| |__| | (_) | (__|   <  __/ |   
 	|_____/ \___/ \___|_|\_\___|_|   

                              

# Getting Started with Docker
    
    https://docs.docker.com/mac/

## Step 1 - Run Hello World Image
	
	docker run hello-world

## Step 2 - Run Whalesay Image
	
	docker run docker/whalesay cowsay boo
	docker images

## Step 3 - Build Your Own Image
	
	Dockerfile:
		FROM docker/whalesay:latest
		RUN apt-get -y update && apt-get install -y fortunes
		CMD /usr/games/fortune -a | cowsay

	cd helloworld
	docker build -t helloworld .
	docker images
	docker run helloworld

## Step 4 - Tag and Push Image

	docker images  (find image id)
	docker tag <image id> <account>/helloworld:latest

	macpro:helloworld pnguyen$ docker images
	REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
	helloworld          latest              b17c76453dda        3 hours ago         274.5 MB
	docker/whalesay     latest              6b362a9f73eb        10 months ago       247 MB

	docker tag b17c76453dda paulnguyen/helloworld:latest
	docker login --username=paulnguyen --email=paul.nguyen@sjsu.edu

	WARNING: login credentials saved in /Users/pnguyen/.docker/config.json
	Login Succeeded

	docker push paulnguyen/helloworld:latest

## Step 5 - Run new Docker Image

	docker run paulnguyen/helloworld


# References
    https://www.docker.com/
    https://www.tutum.co/



                                                             

    