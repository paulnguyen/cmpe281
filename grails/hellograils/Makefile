all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -f build/libs/*
	rm -f *.db

compile:
	grails compile

war: compile
	grails war

dev-war: compile
	grails -Dgrails.env=development war

run: 
	echo Starting Grails at:  http://localhost:8080
	echo Make Controller at:  http://localhost:8080/make
	echo Model Controller at:  http://localhost:8080/model
	echo Vechicle Controller at:  http://localhost:8080/vechicle
	grails run-app

run-test: 
	echo Starting Grails at:  http://localhost:8080
	grails test run-app	

run-boot:
	echo Starting Grails at:  http://localhost:8080
	java -jar build/libs/hellograils-1.0.war

build-image:
	docker build -t hellograils .

docker:
	docker build -t hellograils .
	docker run --name grails-mysql -e MYSQL_ROOT_PASSWORD=welcome -dt mysql
	docker run --name hellograils --link grails-mysql:mysql -p 8080:8080 -dt hellograils 

grails-shell:
	docker exec -it hellograils bash 	

mysql-shell:
	docker exec -it grails-mysql bash 	

docker-build: 
	docker build -t hellograils .
	docker images

docker-clean:
	docker stop hellograils
	docker rm hellograils
	docker rmi hellograils
	docker stop grails-mysql
	docker rm grails-mysql

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