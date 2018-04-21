
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

mysql-run:
	docker run -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe281 mysql:5.5

mysql-shell:
	docker exec -it mysql bash 

mysql-connect:
	mysql --host=localhost --user=root --password=cmpe281

riak-up:
	docker-compose up -d coordinator
	docker-compose scale member=4	
	docker-compose up -d mysql
	docker-compose up -d cassandra
	#docker-compose up --scale member=4

riak-status:
	docker-compose exec coordinator riak-admin cluster status 

riak-console:
	open http://localhost:8098/admin/ 

riak-scale:
	docker-compose scale coordinator=1 member=4

riak-logs:
	docker-compose logs

riak-down:
	docker-compose down

riak-shell:
	docker exec -it $(node) bash 

riak-shell-mysql:
	docker exec -it gumballriak_mysql_1 bash

riak-shell-cassandra:
	docker exec -it gumballriak_cassandra_1 bash

riak-shell-node1:
	docker exec -it gumballriak_coordinator_1 bash
	#riak ping
	#riak-admin test
	#riak-admin bucket-type create animals '{"props":{"search_index":"animals"}}'
	#riak-admin bucket-type activate animals


riak-shell-node2:
	docker exec -it gumballriak_member_1 bash

riak-shell-node3:
	docker exec -it gumballriak_member_2 bash

riak-shell-node4:
	docker exec -it gumballriak_member_3 bash

riak-shell-node5:
	docker exec -it gumballriak_member_4 bash

riak-nodes:
	docker ps -q -f label=com.basho.riak.cluster.name=riakkv

riak-hosts:
	riakhosts.sh

riak-inspect:
	docker inspect $(node)

docker-run:
	docker run -d --name gumball --link mysql:mysql -td -p 3000:3000 gumball

kong-run:
	docker run -d --name kong-database -p 9042:9042 cassandra:2.2
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

docker-shell:
	docker exec -it gumball bash 

kong-shell:
	docker exec -it kong bash 

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-clean:
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


