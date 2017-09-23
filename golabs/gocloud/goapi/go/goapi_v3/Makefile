
all: clean

clean: 
	find . -name 'goapi' -type f -exec rm -f {} \; 
	go clean

run:
	go run src/app/$(app).go

main:
	go run src/app/main.go

format:
	go fmt goapi

install:
	go install goapi

build:
	go build goapi

start:
	./goapi 

test-ping:
	curl localhost:3000/ping

test-gumball:
	curl localhost:3000/gumball

docker-build: 
	docker build -t goapi .
	docker images

docker-run:
	docker run --name goapi -td -p 3000:3000 goapi
	docker ps

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-shell:
	docker exec -it goapi bash 

docker-clean:
	docker stop goapi
	docker rm goapi
	docker rmi goapi

docker-ip:
	docker-machine ip

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

docker-start:
	docker start mongodb
	docker start rabbitmq
	docker start kong-database
	docker start kong
	docker start goapi

docker-stop:
	docker stop mongodb
	docker stop rabbitmq
	docker stop kong
	docker stop kong-database	
	docker stop goapi

docker-rm:
	docker rm mongodb
	docker rm rabbitmq	
	docker rm kong
	docker rm kong-database
	docker rm goapi

docker-rabbit:
	docker run -d --name rabbitmq -p 8080:15672 rabbitmq:3-management

docker-mongo:
	docker run -d --name mongodb -p 27017:27017 mongo

docker-goapi:
	docker run -d -p 3000:3000 --name goapi --link mongodb:mongodb --link rabbitmq:rabbitmq goapi

docker-kongdb:
	docker run -d --name kong-database -p 9042:9042 cassandra:2.2

docker-kong:
	docker run -d --name kong \
		--link goapi:goapi \
		--link kong-database:kong-database \
    	-e "KONG_DATABASE=cassandra" \
        -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
        -e "KONG_PG_HOST=kong-database" \
        -p 8000:8000 \
        -p 8443:8443 \
        -p 8001:8001 \
        -p 7946:7946 \
        -p 7946:7946/udp \
        kong


