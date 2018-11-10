
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

docker-build: 
	docker build -t gumball .
	docker images

rabbitmq-run:
	docker run --name rabbitmq \
			   -d rabbitmq:3-management
mongodb-run:
	docker run --name mongodb -d mongo:3.7

docker-run:
	docker run \
	  		--link mongodb:mongodb \
            --link rabbitmq:rabbitmq \
			--name gumball -p 9000:3000 -td gumball
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

test-ping:
	curl localhost:9000/ping

test-get-inventory:
	curl localhost:9000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:9000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:9000/orders \
  	-H 'Content-Type: application/json'

up:
	docker-compose up -d

down:
	docker-compose down

network-ls:
	docker network ls

network-create:
	docker network create webgateway

network-prune:
	docker network prune
	

