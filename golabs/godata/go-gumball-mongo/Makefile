
all: clean

clean: 
	find . -name 'gumball' -type f -exec rm -f {} \; 
	go clean

go-get:
	rm -rf src/github.com
	go get -v github.com/codegangsta/negroni
	go get -v github.com/streadway/amqp
	go get -v github.com/gorilla/mux
	go get -v github.com/unrolled/render
	go get -v github.com/satori/go.uuid
	go get -v gopkg.in/mgo.v2
	go get -v gopkg.in/mgo.v2/bson

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

network-create:
	docker network create --driver bridge gumball

network-inspect:
	docker network inspect gumball

rabbitmq-run:
	docker run --name rabbitmq \
	           --network gumball \
			   -p 8080:15672 -p 4369:4369 -p 5672:5672 \
			   -d rabbitmq:3-management
mongodb-run:
	docker run --name mongodb --network gumball -p 27017:27017 -d mongo

docker-run:
	docker run \
            --network gumball \
			--name gumball -p 3000:3000 -td gumball
	docker ps

kong-run:
	docker run -d --name kong \
	          --network gumball \
              -e "KONG_DATABASE=cassandra" \
              -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
              -e "KONG_PG_HOST=kong-database" \
              -p 8000:8000 \
              -p 8443:8443 \
              -p 8001:8001 \
              -p 7946:7946 \
              -p 7946:7946/udp \
              kong:0.9.9

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
	docker stop kong-database
	docker rm kong-database
	docker stop kong
	docker rm kong
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
	curl localhost:3000/ping

test-get-inventory:
	curl localhost:3000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:3000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:3000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:3000/orders \
  	-H 'Content-Type: application/json'
