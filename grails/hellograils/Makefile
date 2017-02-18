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
	java -jar build/libs/hellograils-1.0.war

docker:
	docker build -t hellograils .
	docker run --name hellograils -td hellograils
	docker exec -it hellograils bash 	

docker-build: 
	docker build -t hellograils .
	docker images

docker-clean:
	docker stop hellograils
	docker rm hellograils
	docker rmi hellograils

docker-run:
	docker run --name hellograils -td hellograils
	docker ps

docker-run-host:
	docker run --name hellograils -td --net=host hellograils
	docker ps

docker-run-bridge:
	docker run --name hellograils -td -p 80:8080 hellograils
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop hellograils
	docker rm hellograils

docker-shell:
	docker exec -it hellograils bash 