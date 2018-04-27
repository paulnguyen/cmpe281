
##
## Gumball Riak
##

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

compose-up:
	docker-compose up 

compose-down:
	docker-compose down 	

mysql-up:
	docker-compose up -d mysql

riak-up:
	docker-compose scale coordinator=1 member=4

nodejs-up:
	docker-compose scale nodejs=1 

nodejs-down:
	docker-compose stop nodejs
	docker-compose rm nodejs

gumball-up:
	docker-compose scale gumball=1 

gumball-down:
	docker-compose stop gumball
	docker-compose rm gumball

gumball-logs:
	docker logs gumballriak_gumball_1

kong-up:
	docker-compose scale cassandra=1 kong=1

gumball-shell:
	docker exec -it gumballriak_gumball_1 bash
	#docker exec -it gumballriak_gumball_run_1 bash

mysql-shell:
	docker exec -it gumballriak_mysql_1 bash

cassandra-shell:
	docker exec -it gumballriak_cassandra_1 bash

kong-shell:
	docker exec -it gumballriak_kong_1 bash 

riak-status:
	docker-compose exec coordinator riak-admin cluster status 

riak-console:
	open http://localhost:8098/admin/ 

riak-logs:
	docker-compose logs

riak-inspect-coordinator:
	docker inspect gumballriak_coordinator_1

riak-shell:
	docker exec -it $(node) bash 

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

docker-network:
	docker network ls

docker-network-inspect:
	docker network inspect host

docker-ip:
	docker-machine ip

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"


