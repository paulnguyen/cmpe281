
all: clean

clean: 
	find . -name 'hello' -type f -exec rm -f {} \; 
	go clean

build:
	go build github.com/cloudnativego/hello

install:
	go install github.com/cloudnativego/hello

run:
	bin/hello

test:
	curl http://localhost:8080

docker-build: 
	docker build -t hellogo .
	docker images

docker-run:
	docker run --name hellogo -td --net=host hellogo
	docker ps

docker-network:
	docker network inspect host

docker-shell:
	docker exec -it hellogo bash 

docker-clean:
	docker stop hellogo
	docker rm hellogo
	docker rmi hellogo

docker-ip:
	docker-machine ip