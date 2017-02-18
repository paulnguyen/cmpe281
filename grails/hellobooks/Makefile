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

test-get-all:
	curl http://localhost:8080/books/

test-get-json:
	curl http://localhost:8080/books/1.json

test-get-xml:
	curl http://localhost:8080/books/1.xml

test-create:
	curl -i -X POST -H "Content-Type: application/json" -d '{"title":"Along Came A Spider"}' localhost:8080/books

test-update:
	curl -i -X PUT -H "Content-Type: application/json" -d '{"title":"Along Came A Spider 2nd Edition"}' localhost:8080/books/1

test-delete:
	curl -i -X DELETE localhost:8080/books/1

test-publisher:
	curl -i -X GET localhost:8080/books/publisher

run-boot:
	echo Starting Grails at:  http://localhost:8080
	java -jar build/libs/hellobooks-1.0.war

docker:
	docker build -t hellobooks .
	docker run --name hellobooks -td hellobooks
	docker exec -it hellobooks bash 	

docker-build: 
	docker build -t hellobooks .
	docker images

docker-clean:
	docker stop hellobooks
	docker rm hellobooks
	docker rmi hellobooks

docker-run:
	docker run --name hellobooks -td hellobooks
	docker ps

docker-run-host:
	docker run --name hellobooks -td --net=host hellobooks
	docker ps

docker-run-bridge:
	docker run --name hellobooks -td -p 80:8080 hellobooks
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop hellobooks
	docker rm hellobooks

docker-shell:
	docker exec -it hellobooks bash 

