
all: clean

clean: 
	find . -name 'gumball' -type f -exec rm -f {} \; 
	go clean

go-get:
	rm -rf src/github.com
	go get -v github.com/codegangsta/negroni
	go get -v github.com/gorilla/mux
	go get -v github.com/unrolled/render
	go get -v github.com/satori/go.uuid
	go get -v database/sql
	go get -v github.com/go-sql-driver/mysql
	go get -v github.com/go-redis/redis

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

mysql-run:
	docker run -d --name mysql --network gumball -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.5

redis-run:
	docker run --name redis --network gumball -td -p 6379:6379 redis:4.0

docker-run:
	docker run -d --name gumball --network gumball -td -p 3000:3000 gumball

kong-database:
	docker run -d --name kong-database --network gumball -p 9042:9042 cassandra:2.2

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

mysql-shell:
	docker exec -it mysql bash 

mysql-connect:
	mysql --host=localhost --user=root --password=cmpe281

redis-shell:
	docker exec -it redis bash 

docker-shell:
	docker exec -it gumball bash 

kong-shell:
	docker exec -it kong bash 

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-clean:
	docker stop redis
	docker rm redis
	docker stop mysql
	docker rm mysql
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

