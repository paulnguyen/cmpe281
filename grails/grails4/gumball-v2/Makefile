all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -rf build
	rm -f *.db

compile:
	grails compile

build: compile
	grails war

run: compile
	echo Starting Grails at:  http://localhost:8080
	grails run-app

run-test: compile
	echo Starting Grails at:  http://localhost:8080
	grails test run-app	

run-boot: build
	echo Starting Grails at:  http://localhost:8080
	java -jar build/libs/gumball-v2-2.0.war

docker:
	docker build -t gumball .
	docker run --name gumball -td gumball
	docker exec -it gumball bash 	

docker-build: 
	docker build -t gumball .
	docker images

docker-clean:
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-run: docker-build
	docker run --name gumball -td -p 8080:8080 gumball --restart always
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop gumball
	docker rm gumball

docker-shell:
	docker exec -it gumball bash 

