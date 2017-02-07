all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;

compile:
	gradle build

run:
	echo Starting Spring at:  http://localhost:8080
	java -jar build/libs/hello-rest-service-1.0.0.jar

test1:
	curl http://localhost:8080/greeting

test2:
	curl http://localhost:8080/greeting?name=User

docker:
	docker build -t hellospring .
	docker run --name hellospring -td --net=host hellospring
	docker exec -it hellospring bash 	

docker-build: 
	docker build -t hellospring .
	docker images

docker-clean:
	docker stop hellospring
	docker rm hellospring
	docker rmi hellospring

docker-run:
	docker run --name hellospring -td hellospring
	docker ps

docker-run-host:
	docker run --name hellospring -td --net=host hellospring
	docker ps

docker-run-bridge:
	docker run --name hellospring -td -p 80:8080 hellospring
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop hellospring
	docker rm hellospring

docker-shell:
	docker exec -it hellospring bash 