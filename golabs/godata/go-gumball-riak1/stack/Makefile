
net-ls:
	docker network ls

net-inspect:
	docker network inspect $(net)

net-create:
	docker network create --driver bridge gumball_front
	docker network create --driver bridge gumball_app
	docker network create --driver bridge gumball_db

net-drop:
	docker network rm gumball_front gumball_app gumball_db gumball_extra

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

compose-up:
	docker-compose up 

compose-down:
	docker-compose down 	

mysql-up:
	docker-compose scale mysql=1

riak-up:
	docker-compose scale coordinator=1 member=3

riak-up-node1:
	docker-compose scale coordinator=1 

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
	docker logs gumball_gumball_1

kong-up:
	docker-compose scale cassandra=1 kong=1

gumball-shell:
	docker exec -it gumball_gumball_1 bash
	#docker exec -it gumball_gumball_run_1 bash

mysql-shell:
	docker exec -it gumball_mysql_1 bash

cassandra-shell:
	docker exec -it gumball_cassandra_1 bash

kong-shell:
	docker exec -it gumball_kong_1 bash 

riak-status:
	docker-compose exec coordinator riak-admin cluster status 

riak-console:
	open http://localhost:8098/admin/ 

riak-logs:
	docker-compose logs

riak-inspect-coordinator:
	docker inspect gumball_coordinator_1

riak-shell:
	docker exec -it $(node) bash 

riak-shell-node1:
	docker exec -it gumball_coordinator_1 bash
	#riak ping
	#riak-admin test
	#riak-admin bucket-type create animals '{"props":{"search_index":"animals"}}'
	#riak-admin bucket-type activate animals

riak-shell-node2:
	docker exec -it gumball_member_1 bash

riak-shell-node3:
	docker exec -it gumball_member_2 bash

riak-shell-node4:
	docker exec -it gumball_member_3 bash

riak-shell-node5:
	docker exec -it gumball_member_4 bash

riak-shell-extra1:
	docker exec -it gumball_extra_1 bash

riak-shell-extra2:
	docker exec -it gumball_extra_2 bash

riak-nodes:
	docker ps -q -f label=com.basho.riak.cluster.name=riakkv

riak-hosts:
	riakhosts.sh

riak-inspect:
	docker inspect $(node)