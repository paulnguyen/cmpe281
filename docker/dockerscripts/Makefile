
container = <container name>
network = <network name>

up:
	docker-compose up -d

down:
	docker-compose down

network-ls:
	docker network ls

network-create:
	docker network create $(network)

network-prune:
	docker network prune

docker-build: 
	docker build -t $(container) .
	docker images

docker-clean:
	docker stop $(container)
	docker rm $(container)
	docker rmi $(container)

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

docker-clean-all:
	docker stop $(docker ps -aq)
	docker rm $(docker ps -aq)
	docker rmi $(docker images -q)

docker-run:
	docker run --name $(container) -td $(container)
	docker ps

docker-run-host:
	docker run --name $(container) -td --net=host $(container)
	docker ps

docker-run-bridge:
	docker run --name $(container) -td -p 80:8080 $(container)
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop $(container)
	docker rm $(container)

docker-shell:
	docker exec -it $(container) bash 

