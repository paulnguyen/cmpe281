
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

docker-run:
	docker run --name gumball -td -p 3000:3000 gumball
	docker ps

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-clean:
	docker stop gumball
	docker rm gumball
	docker rmi gumball

docker-ip:
	docker-machine ip

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

docker-shell:
	docker exec -it gumball bash 

kong-shell:
	docker exec -it kong bash 

run-databases:
	docker run -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.5
	docker run -d --name kong-database -p 9042:9042 cassandra:2.2

run-gumball:
	docker run -d --name gumball --link mysql:mysql -td -p 3000:3000 gumball

run-kong:
	docker run -d --name kong \
			  --link gumball:gumball \
              --link kong-database:kong-database \
              -e "KONG_DATABASE=cassandra" \
              -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
              -e "KONG_PG_HOST=kong-database" \
              -p 8000:8000 \
              -p 8443:8443 \
              -p 8001:8001 \
              -p 7946:7946 \
              -p 7946:7946/udp \
              kong:0.9.9

