all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -f build/libs/*
	rm -f *.db

compile:
	grails compile

war: compile
	grails war

run: 
	echo Starting Grails at:  http://localhost:8080
	grails run-app

run-test: 
	echo Starting Grails at:  http://localhost:8080
	grails test run-app	

run-boot:
	echo Starting Grails at:  http://localhost:8080
	java -jar build/libs/hellorest-1.0.war

docker:
	docker build -t hellorest .
	docker run --name hellorest -td hellorest
	docker exec -it hellorest bash 	

docker-build: 
	docker build -t hellorest .
	docker images

docker-clean:
	docker stop hellorest
	docker rm hellorest
	docker rmi hellorest

docker-run:
	docker run --name hellorest -td hellorest
	docker ps

docker-run-host:
	docker run --name hellorest -td --net=host hellorest
	docker ps

docker-run-bridge:
	docker run --name hellorest -td -p 80:8080 hellorest
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop hellorest
	docker rm hellorest

docker-shell:
	docker exec -it hellorest bash 

