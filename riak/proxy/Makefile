
net-ls:
	docker network ls

net-inspect:
	docker network inspect $(net)

net-create:
	docker network create --driver bridge proxy_db

net-drop:
	docker network rm proxy_db

network-prune:
	docker network prune	

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

compose-up:
	docker-compose up 

compose-down:
	docker-compose down 	

riak-up:
	docker-compose scale proxy=1 coordinator=1 member=4

riak-up-1:
	docker-compose scale proxy=1 coordinator=1 member=1

riak-up-3:
	docker-compose scale proxy=1 coordinator=1 member=3

riak-up-4:
	docker-compose scale proxy=1 coordinator=1 member=4

riak-down:
	docker-compose stop coordinator
	docker-compose stop member
	docker-compose stop proxy

riak-status:
	docker-compose exec coordinator riak-admin cluster status 

riak-console:
	open http://localhost:8098/admin/ 

riak-logs:
	docker-compose logs

riak-inspect-coordinator:
	docker inspect proxy_coordinator_1

riak-shell:
	docker exec -it $(node) bash 

riak-shell-node1:
	docker exec -it proxy_coordinator_1 bash
	# riak ping
	# riak-admin test
	# riak-admin cluster status
	# riak-admin bucket-type create animals '{"props":{"search_index":"animals"}}'
	# riak-admin bucket-type activate animals
	# curl -i http://localhost:8098/ping

riak-shell-node2:
	docker exec -it proxy_member_1 bash

riak-shell-node3:
	docker exec -it proxy_member_2 bash

riak-shell-node4:
	docker exec -it proxy_member_3 bash

riak-shell-node5:
	docker exec -it proxy_member_4 bash

riak-nodes:
	docker ps -q -f label=com.basho.riak.cluster.name=riakkv

riak-hosts:
	./riakhosts.sh

riak-inspect:
	docker inspect $(node)

proxy-shell:
	docker exec -it proxy_proxy_1 bash

