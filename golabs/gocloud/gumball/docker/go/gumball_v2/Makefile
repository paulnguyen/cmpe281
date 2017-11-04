
all: clean

clean: 
	find . -name 'gumball' -type f -exec rm -f {} \; 
	go clean

run:
	go run src/app/$(app).go

main:
	go run src/app/main.go

format:
	go fmt gumball

install:
	go install gumball

build:
	go build gumball

start:
	./gumball 

test-ping:
	curl localhost:3000/ping

test-gumball:
	curl localhost:3000/gumball

docker-build: 
	docker build -t gumball .
	docker images

rabbitmq-run:
	docker run --name rabbitmq \
			   -p 8080:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management
mongodb-run:
	docker run --name mongodb -p 27017:27017 -d mongo

docker-run:
	docker run \
	  		--link mongodb:mongodb \
            --link rabbitmq:rabbitmq \
			--name gumball -p 3000:3000 -td gumball
	docker ps

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-shell:
	docker exec -it gumball bash 

docker-clean:
	docker stop mongodb
	docker stop rabbitmq
	docker rm mongodb
	docker rm rabbitmq
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-ip:
	docker-machine ip

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

