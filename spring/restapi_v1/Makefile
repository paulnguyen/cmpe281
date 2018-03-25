all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;

compile:
	gradle build

run:
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/spring-restapi-1.0.0.jar

test1:
	curl http://localhost:8080/greeting

test2:
	curl http://localhost:8080/greeting?name=User

docker:
	docker build -t spring-restapi .
	docker run --name spring-restapi -td --net=host spring-restapi
	docker exec -it spring-restapi bash 	

docker-build: 
	docker build -t spring-restapi .
	docker images

docker-clean:
	docker stop spring-restapi
	docker rm spring-restapi
	docker rmi spring-restapi

docker-run:
	docker run --name spring-restapi -td spring-restapi
	docker ps

docker-run-host:
	docker run --name spring-restapi -td --net=host spring-restapi
	docker ps

docker-run-bridge:
	docker run --name spring-restapi -td -p 80:8080 spring-restapi
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop spring-restapi
	docker rm spring-restapi

docker-shell:
	docker exec -it spring-restapi bash 