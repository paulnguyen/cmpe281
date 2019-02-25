
all: clean

clean: 
	find . -name 'app' -type f -exec rm -f {} \; 
	rm -rf bin/*
	go clean

build:
	go build app

run:
	./app 

format:
	go fmt app

doc:
	go doc app

install:
	go install app

docker-build: 
	docker build -t goapp .
	docker images

docker-run:
	docker run --name goapp -td --net=host goapp
	docker ps

docker-network:
	docker network inspect host

docker-shell:
	docker exec -it goapp bash 

docker-clean:
	docker stop goapp
	docker rm goapp
	docker rmi goapp




